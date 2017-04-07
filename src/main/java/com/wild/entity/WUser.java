package com.wild.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 
 * @author Wild
 *
 */
public class WUser implements Serializable {

	private static final long serialVersionUID = 1632537799039372010L;

	private String WID;// 用户id
	private String WName;// 用户名字
	private String WSex;//用户性别
	private String WUserNum;// 用户帐号
	private String WPassWord;// 用户密码
	private String WAge;// 用户年龄
	private Date WDate;
	private int WStatus;// 用户状态
	private int WSuperManager;// 用户角色
	

	public WUser(String wID, String wName, String wSex, String wUserNum, String wPassWord, String wAge, Date wDate,
			int wStatus, int wSuperManager) {
		WID = wID;
		WName = wName;
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

	public String getWName() {
		return WName;
	}

	public void setWName(String wName) {
		WName = wName;
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

	public int getWStatus() {
		return WStatus;
	}

	public void setWStatus(int wStatus) {
		WStatus = wStatus;
	}

	public int getWSuperManager() {
		return WSuperManager;
	}

	public void setWSuperManager(int wSuperManager) {
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
