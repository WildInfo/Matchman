package com.wild.service.impl;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wild.entity.WUser;
import com.wild.service.WUserService;
import com.wild.utils.UUIDUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class WUserServiceImplTest {

	@Autowired
	private WUserService vservice;

	@Test
	public void testRegister() {
		WUser users = new WUser();
		users.setWID(UUIDUtil.createUUID());
		users.setWName("ludowe");
		users.setWDate(new Date());
		users.setWPassWord("erw");
		users.setWStatus(23);
		users.setWSuperManager(2);
		users.setWUserNum("111100");
		int user = vservice.register(users);
		System.out.println("用户名为：" + user);
	}

}
