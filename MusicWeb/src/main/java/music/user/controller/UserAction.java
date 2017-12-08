package music.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userAction")
public class UserAction {
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request){
		return "user/user";
	}
}
