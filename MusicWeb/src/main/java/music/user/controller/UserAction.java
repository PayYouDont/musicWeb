package music.user.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.user.entity.User;
import music.user.service.UserService;
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
	
	public HashMap<String,Object> regist(HttpServletRequest request,User user){
		
		return null;
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public HashMap<String,Object> login(HttpServletRequest request,User user){
		if(user==null) {
			return JsonWrapper.failureWrapper("用户为空");
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
			return JsonWrapper.successWrapper("密码为空或者长度不符");
		}
		String rpassword = result.getPassword();
		if(!password.equals(rpassword)) {
			return JsonWrapper.successWrapper("密码不正确");
		}
		SessionUtils.setUser(request, result);
		return JsonWrapper.successWrapper("登录成功");
	}
}
