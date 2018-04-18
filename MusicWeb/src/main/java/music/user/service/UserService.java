package music.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import music.user.dao.UserMapper;
import music.user.entity.User;

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
}
