package com.wild.entity;

/**
 * 用户
 * 
 * @author Wild
 *
 */
public class WUser {
	private int wuId;// 用户id
	private String wname;// 用户名字
	private String wpwd;// 用户密码
	private int wuStatus;// 用户状态
	private int wersion;// 用户角色

	public WUser(String string, String string2, int i, int j) {
	}

	public int getWuId() {
		return wuId;
	}

	public void setWuId(int wuId) {
		this.wuId = wuId;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	public String getWpwd() {
		return wpwd;
	}

	public void setWpwd(String wpwd) {
		this.wpwd = wpwd;
	}

	public int getWuStatus() {
		return wuStatus;
	}

	public void setWuStatus(int wuStatus) {
		this.wuStatus = wuStatus;
	}

	public int getWersion() {
		return wersion;
	}

	public void setWersion(int wersion) {
		this.wersion = wersion;
	}
}
