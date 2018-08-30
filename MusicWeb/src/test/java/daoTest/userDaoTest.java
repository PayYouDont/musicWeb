package daoTest;

import javax.annotation.Resource;

import org.junit.Test;

import music.user.dao.UserMapper;


public class userDaoTest {
	
	@Resource
	private UserMapper dao;
	
	@Test
	public void test() {
		System.out.println(dao);
	}
	
}
