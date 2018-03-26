package UserTest;

import org.junit.Test;

import music.user.dao.UserDao;
import music.user.entity.User;

public class UserDaoTest {
	private UserDao dao;
	@Test
	public void addUser() {
		User user = new User();
		user.setName("test");
		user.setAccount("test");
		user.setPassword("123456");
		user.setStatus(0);
		dao.addUser(user);
	}
	@Test
	public void test() {
		
	}
}
