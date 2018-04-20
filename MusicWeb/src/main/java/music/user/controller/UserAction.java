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
import music.util.EmailUtil;
import music.util.JsonWrapper;
import music.util.Md5Util;
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
			return JsonWrapper.successWrapper("account","账号不存在");
		}
		String password = user.getPassword();
		if(password==null||password.length()<6) {
			return JsonWrapper.successWrapper("password","请输入6位以上的密码");
		}
		String rpassword = result.getPassword();
		password = Md5Util.MD5(password);
		if(!password.equals(rpassword)) {
			return JsonWrapper.successWrapper("password","密码不正确");
		}
		int gtResult = serivec.checkValidate(request);
		if (gtResult != 1) {
			// 验证失败
			return JsonWrapper.successWrapper("captcha-login","验证码不正确");
		}
		Byte status = result.getStatus();
		if(status==-1) {
			return JsonWrapper.successWrapper("fail","该账号已被禁用");
		}
		SessionUtils.setUser(request, result);
		return JsonWrapper.successWrapper(result,"登录成功");
	}
	
	@RequestMapping("/regist")
	@ResponseBody
	public HashMap<String,Object> regist(HttpServletRequest request,User user){
		if(user==null) {
			return JsonWrapper.successWrapper("用户为空");
		}
		String account = user.getAccount();
		if(account==null||"".equals(account)) {
			return JsonWrapper.successWrapper("account-regist","账号不能为空");
		}
		User result = serivec.findByAccount(account);
		if(result!=null) {
			return JsonWrapper.successWrapper("account-regist","该账号已存在");
		}
		String email = user.getEmail();
		if(email==null||"".equals(email)) {
			return JsonWrapper.successWrapper("email-regist","邮箱不能为空");
		}
		result = serivec.findByEmail(email);
		if(result!=null) {
			return JsonWrapper.successWrapper("email-regist","该邮箱已注册");
		}
		String password = user.getPassword();
		if(password==null||password.length()<6) {
			return JsonWrapper.successWrapper("password-regist","请输入6位以上的密码");
		}
		String confirmPwd = request.getParameter("confirmPwd");
		if(!confirmPwd.equals(password)) {
			return JsonWrapper.successWrapper("confirmPwd","密码输入不一致");
		}
		int gtResult = serivec.checkValidate(request);
		if (gtResult != 1) {
			// 验证失败
			return JsonWrapper.successWrapper("captcha-regist","验证码不正确");
		}
		try {
			EmailUtil.activateMail(request, user);
		} catch (Exception e) {
			e.printStackTrace();
			loger.error(e.getMessage(),e);
			return JsonWrapper.successWrapper("email-regist","邮箱验证失败");
		}
		password = Md5Util.MD5(password);
		user.setPassword(password);
		try {
			serivec.saveUser(user);
			SessionUtils.setUser(request, user);
			return JsonWrapper.successWrapper("success","注册成功");
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
		String resStr = serivec.initVerify(request);
		return JsonWrapper.successWrapper(resStr);
	}
	@RequestMapping("/activatemail")
	public String activatemail(HttpServletRequest request){
		String email = request.getParameter("email");
        String token = request.getParameter("token");
        Long time = System.currentTimeMillis();
        User user = serivec.findByEmail(email);
        if(user==null) {
        	request.setAttribute("activat","该邮箱未注册");
        	return "user/activatemail";
        }
        Byte status = user.getStatus();
        if(status==-1) {
        	request.setAttribute("activat","该账号已被禁用");
        	return "user/activatemail";
        }
        if(status==1) {
        	request.setAttribute("activat","该账号已激活");
        	return "user/activatemail";
        }
        Long activatetime = user.getActivatetime().getTime();
        if(time-activatetime>0) {//激活时间大于一天
        	try {
				user = EmailUtil.activateMail(request, user);
				//更新token和激活时间
				serivec.saveUser(user);
				request.setAttribute("activat","激活超时,请重新激活");
				return "user/activatemail";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("activat","重新激活邮箱发送失败!");
				return "user/activatemail";
			}
        }
        if(!token.equals(user.getToken())) {
        	request.setAttribute("activat","激活失败,激活码不正确");
        	return "user/activatemail";
        }
        //激活成功更改状态
        user.setStatus((byte)1);
        try {
			serivec.saveUser(user);
			request.setAttribute("activat","激活成功");
			return "user/activatemail";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("activat","网络异常,请稍后再试");
			return "user/activatemail";
		}
	}
	@RequestMapping("/toLogout")
	@ResponseBody
	public HashMap<String,Object> toLogout(HttpServletRequest request){
		SessionUtils.removeUser(request);
		return JsonWrapper.successWrapper("退出成功");
	}
}
