package com.wild.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wild.entity.WUser;
import com.wild.service.WUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class ConTest {
	@Autowired
	private WUserService vservice;

	@Test
	public void test() {
		int user = vservice.register(new WUser("Luodawei", "a", 2, 0));
		System.out.println("用户名为：" + user);
	}

}
