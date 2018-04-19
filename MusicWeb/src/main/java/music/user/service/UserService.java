package music.user.service;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import music.user.dao.UserMapper;
import music.user.entity.User;
import music.util.GeetestConfig;
import music.util.GeetestLib;
import music.util.SessionUtils;

@Service("userService")
public class UserService {
	@Resource
	private UserMapper dao;
	
	public User findById(Integer id){
		User user = dao.selectByPrimaryKey(id);
		return user;
	}
	
	public User findByAccount(String account) {
		User user = dao.findByAccount(account);
		return user;
	}
	
	public int saveUser(User user) throws Exception{
		String account = user.getAccount();
		User result = dao.findByAccount(account);
		if(result!=null) {
			return dao.updateByPrimaryKeySelective(user);
		}
		return dao.insertSelective(user);
	}
	
	public User findByEmail(String email) {
		User user = dao.findByEmail(email);
		return user;
	}
	public Integer checkValidate(HttpServletRequest request) {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
		GeetestConfig.isnewfailback());
		try {
			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
			//从session中获取gt-server状态
			int gt_server_status_code = (Integer)SessionUtils.getAttr(request,gtSdk.gtServerStatusSessionKey);
			//自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
			int gtResult = 0;
			if (gt_server_status_code == 1) {
				//gt-server正常，向gt-server进行二次验证
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
			} else {
				// gt-server非正常情况下，进行failback模式验证
				System.out.println("failback:use your own server captcha validate");
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
			}
			return gtResult;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public String initVerify(HttpServletRequest request) {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
		String resStr = "{}";
		//自定义参数,可选择添加
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
		param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(param);
		//将服务器状态设置到session中
		SessionUtils.setAttr(request,gtSdk.gtServerStatusSessionKey, gtServerStatus);
		resStr = gtSdk.getResponseStr();
		return resStr;
	}
}
