package music.user.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.user.entity.User;
import music.user.service.UserService;
import music.util.JsonWrapper;

@Controller
@RequestMapping({"userAction"})
public class UserAction {

	@Resource
	private UserService serivec;
	
	private Logger loger = LoggerFactory.getLogger(this.getClass());
	
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
}
