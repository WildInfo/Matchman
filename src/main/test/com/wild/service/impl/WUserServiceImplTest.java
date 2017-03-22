package com.wild.service.impl;

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

	/**
	 * private int WID;// 用户id private String WName;// 用户名字 private String
	 * WUserNum;// 用户帐号 private String WPassWord;// 用户密码 private Date WDate;
	 * private int WStatus;// 用户状态 private int WSuperManager;// 用户角色
	 */
	@Test
	public void testRegister() {
		String is=UUIDUtil.createUUID();
		int user = vservice.register(new WUser(is, "Luodawei", "231", "eeee","2007-12-14 14:10", 2, 0));
		System.out.println("用户名为：" + user);
	}

}
