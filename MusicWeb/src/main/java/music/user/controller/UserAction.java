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
			return JsonWrapper.successWrapper("账号不存在");
		}
		String password = user.getPassword();
		if(password==null||password.length()<6) {
			return JsonWrapper.successWrapper("请输入6位以上的密码");
		}
		String rpassword = result.getPassword();
		password = Md5Util.MD5(password);
		if(!password.equals(rpassword)) {
			return JsonWrapper.successWrapper("密码不正确");
		}
		int gtResult = serivec.checkValidate(request);
		if (gtResult != 1) {
			// 验证失败
			return JsonWrapper.successWrapper("验证码不正确");
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
		int gtResult = serivec.checkValidate(request);
		if (gtResult != 1) {
			// 验证失败
			return JsonWrapper.successWrapper("验证码不正确");
		}
		password = Md5Util.MD5(password);
		user.setPassword(password);
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
		String resStr = serivec.initVerify(request);
		return JsonWrapper.successWrapper(resStr);
	}
}
