package com.wild.mapper;

import java.util.List;

import com.wild.entity.WUser;

public interface WUserMapper {
	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	public int register(WUser user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public List<WUser> login(WUser user);
}
