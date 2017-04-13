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
	 * 查看手机号码是否唯一
	 * 
	 * @param tel
	 * @return
	 */
	public String telForOnly(String tel);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public List<WUser> login(WUser user);

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	public int lostPassWord(WUser user);

}
