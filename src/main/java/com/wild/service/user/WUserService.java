package com.wild.service.user;

import java.util.List;

import com.wild.entity.user.WDetails;
import com.wild.entity.user.WUser;
import com.wild.entity.user.WUserDetailsRelation;

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
	public String telForOnly(String num);

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

	/**
	 * 更新个人详情
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserDetails(WDetails details);

	/**
	 * 用户详情与用户
	 * 
	 * @param detailsRelation
	 * @return
	 */
	public int userDetailsDelation(WUserDetailsRelation detailsRelation);

	/**
	 * 根据id查询用户信息
	 * 
	 * @param wid
	 * @return
	 */
	public List<WUser> userDetils(String wid);

	/**
	 * 根据用户id查询用户关系id
	 * 
	 * @param wid
	 * @return
	 */
	public List<WUserDetailsRelation> userDetilsById(WUserDetailsRelation detailsRelation);
	

	/**
	 * 插入用户详情数据
	 * 
	 * @param details
	 * @return
	 */
	public int insertDtails(WDetails details);
}
