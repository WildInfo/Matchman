package com.wild.entity.user;

import java.io.Serializable;
import java.util.Date;

import com.wild.enums.user.UserStatusEnum;
import com.wild.enums.user.UserVersioniEnum;

/**
 * 用户
 * 
 * @author Wild
 *
 */
public class WUser implements Serializable {

	private static final long serialVersionUID = 1632537799039372010L;

	private String WID;// 用户id
	private String WNickName;// 用户昵称
	private String WSex;// 用户性别
	private String WUserNum;// 用户帐号
	private String WPassWord;// 用户密码
	private String WAge;// 用户年龄
	private Date WDate;// 注册时间
	private UserStatusEnum WStatus;// 用户状态
	private UserVersioniEnum WSuperManager;// 用户角色

	public WUser(String wID, String wNickName, String wSex, String wUserNum, String wPassWord, String wAge, Date wDate,
			UserStatusEnum wStatus, UserVersioniEnum wSuperManager) {
		WID = wID;
		WNickName = wNickName;
		WSex = wSex;
		WUserNum = wUserNum;
		WPassWord = wPassWord;
		WAge = wAge;
		WDate = wDate;
		WStatus = wStatus;
		WSuperManager = wSuperManager;
	}

	public WUser() {
	}

	public String getWID() {
		return WID;
	}

	public void setWID(String wID) {
		WID = wID;
	}

	public String getWNickName() {
		return WNickName;
	}

	public void setWNickName(String wNickName) {
		WNickName = wNickName;
	}

	public String getWUserNum() {
		return WUserNum;
	}

	public void setWUserNum(String wUserNum) {
		WUserNum = wUserNum;
	}

	public String getWPassWord() {
		return WPassWord;
	}

	public void setWPassWord(String wPassWord) {
		WPassWord = wPassWord;
	}

	public Date getWDate() {
		return WDate;
	}

	public void setWDate(Date wDate) {
		WDate = wDate;
	}

	public UserStatusEnum getWStatus() {
		return WStatus;
	}

	public void setWStatus(UserStatusEnum wStatus) {
		WStatus = wStatus;
	}

	public UserVersioniEnum getWSuperManager() {
		return WSuperManager;
	}

	public void setWSuperManager(UserVersioniEnum wSuperManager) {
		WSuperManager = wSuperManager;
	}

	public String getWSex() {
		return WSex;
	}

	public void setWSex(String wSex) {
		WSex = wSex;
	}

	public String getWAge() {
		return WAge;
	}

	public void setWAge(String wAge) {
		WAge = wAge;
	}

}
