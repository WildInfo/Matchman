package com.wild.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.WUser;
import com.wild.mapper.WUserMapper;
import com.wild.service.WUserService;

@Service("wUserService")
public class WUserServiceImpl implements WUserService {

	@Autowired
	private WUserMapper wuserMapper;

	@Override
	public int register(WUser user) {
		return wuserMapper.register(user);
	}

	@Override
	public List<WUser> login(WUser user) {
		return wuserMapper.login(user);
	}

	@Override
	public int lostPassWord(WUser user) {
		return wuserMapper.lostPassWord(user);
	}

	@Override
	public String telForOnly(String num) {
		return wuserMapper.telForOnly(num);
	}

}
