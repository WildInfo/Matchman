package com.wild.service;

import java.util.List;

import com.wild.entity.WUser;

public interface WUserService {

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
	
	/**
	 * 完善信息(年龄段，性别)
	 * @param age
	 * @param sex
	 * @return
	 */
	public int perfectDatum(WUser user);

}
