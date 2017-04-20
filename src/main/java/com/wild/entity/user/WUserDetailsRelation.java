package com.wild.entity.user;

import java.io.Serializable;

/**
 * 用户与用户详情关系
 * 
 * @author Wild
 *
 */
public class WUserDetailsRelation implements Serializable {

	private static final long serialVersionUID = -5121087614919443282L;

	private String WID;
	private String WKUserID;
	private String WKDetailsID;
	public String getWID() {
		return WID;
	}
	public void setWID(String wID) {
		WID = wID;
	}
	public String getWKUserID() {
		return WKUserID;
	}
	public void setWKUserID(String wKUserID) {
		WKUserID = wKUserID;
	}
	public String getWKDetailsID() {
		return WKDetailsID;
	}
	public void setWKDetailsID(String wKDetailsID) {
		WKDetailsID = wKDetailsID;
	}
}
