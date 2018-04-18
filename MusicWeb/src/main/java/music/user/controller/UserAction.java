package music.user.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.user.entity.User;
import music.user.service.UserService;
import music.util.GeetestConfig;
import music.util.GeetestLib;
import music.util.JsonWrapper;
import music.util.SessionUtils;

@Controller
@RequestMapping({"userAction"})
public class UserAction {

	@Resource
	private UserService serivec;
	
	private Logger loger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request) {
		request.setAttribute("action","login");
		return "user/login";
	}
	
	@RequestMapping("/toRegist")
	public String toRegist(HttpServletRequest request) {
		request.setAttribute("action","regist");
		return "user/login";
	}
	
	@RequestMapping("/fidnById")
	@ResponseBody
	public HashMap<String,Object> fidnById(Integer id){
		User user = null;
		if(id==null) {
			return JsonWrapper.failureWrapper("id不存在");
		}
		try {
			user = serivec.findById(id);
			return JsonWrapper.successWrapper(user);
		} catch (Exception e) {
			e.printStackTrace();
			loger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("网络异常");
		}
	}
	@RequestMapping("/login")
	@ResponseBody
	public HashMap<String,Object> login(HttpServletRequest request,User user){
		if(user==null) {
			return JsonWrapper.successWrapper("用户为空");
		}
		String account = user.getAccount();
		User result =null;
		if(account!=null&&!"".equals(account.trim())) {
			result = serivec.findByAccount(account);
		}
		if(result==null) {
			return JsonWrapper.successWrapper("账号不存在");
		}
		String password = user.getPassword();
		if(password==null||password.length()<6) {
			return JsonWrapper.successWrapper("请输入6位以上的密码");
		}
		String rpassword = result.getPassword();
		if(!password.equals(rpassword)) {
			return JsonWrapper.successWrapper("密码不正确");
		}
		SessionUtils.setUser(request, result);
		return JsonWrapper.successWrapper("登录成功");
	}
	
	@RequestMapping("/regist")
	@ResponseBody
	public HashMap<String,Object> regist(HttpServletRequest request,User user){
		if(user==null) {
			return JsonWrapper.successWrapper("用户为空");
		}
		String account = user.getAccount();
		if(account==null||"".equals(account)) {
			return JsonWrapper.successWrapper("账号不能为空");
		}
		User result = serivec.findByAccount(account);
		if(result!=null) {
			return JsonWrapper.successWrapper("该账号已存在");
		}
		String password = user.getPassword();
		if(password==null||password.length()<6) {
			return JsonWrapper.successWrapper("请输入6位以上的密码");
		}
		String confirmPwd = request.getParameter("confirmPwd");
		if(!confirmPwd.equals(password)) {
			return JsonWrapper.successWrapper("密码输入不一致");
		}
		try {
			serivec.saveUser(user);
			SessionUtils.setUser(request, user);
			return JsonWrapper.successWrapper("注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			loger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("注册失败");
		}
	}
	/**
	 * 初始化验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initVerify")
	@ResponseBody
	public HashMap<String,Object> initVerify(HttpServletRequest request,HttpServletResponse response){
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
		String resStr = "{}";
		//自定义参数,可选择添加
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
		param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(param);
		//将服务器状态设置到session中
		request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		resStr = gtSdk.getResponseStr();
		return JsonWrapper.successWrapper(resStr);
	}
}
