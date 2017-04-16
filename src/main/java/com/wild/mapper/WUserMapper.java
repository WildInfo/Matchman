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
	 * 查看手机号码是否唯一
	 * 
	 * @param tel
	 * @return
	 */
	public String telForOnly(String num);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public List<WUser> login(WUser user);

	/**
	 * 完善信息(年龄段，性别)
	 * 
	 * @param age
	 * @param sex
	 * @return
	 */
	public int perfectDatum(WUser user);

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	public int lostPassWord(WUser user);
}
