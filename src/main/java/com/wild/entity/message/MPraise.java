package com.wild.entity.message;

import java.io.Serializable;

/**
 * 点赞
 * 
 * @author Wild
 *
 */
public class MPraise implements Serializable {

	private static final long serialVersionUID = -7578295495995043924L;

	private String MID;// 主键ID
	private String MKUserID;// 用户ID
	private String MKMessageID;// 热点ID
	private String MPraise1;// 备用字段
	private String MPraise2;// 备用字段

	public MPraise(String mID, String mKUserID, String mKMessageID, String mPraise1, String mPraise2) {
		super();
		MID = mID;
		MKUserID = mKUserID;
		MKMessageID = mKMessageID;
		MPraise1 = mPraise1;
		MPraise2 = mPraise2;
	}

	public MPraise() {
		super();
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getMKUserID() {
		return MKUserID;
	}

	public void setMKUserID(String mKUserID) {
		MKUserID = mKUserID;
	}

	public String getMKMessageID() {
		return MKMessageID;
	}

	public void setMKMessageID(String mKMessageID) {
		MKMessageID = mKMessageID;
	}

	public String getMPraise1() {
		return MPraise1;
	}

	public void setMPraise1(String mPraise1) {
		MPraise1 = mPraise1;
	}

	public String getMPraise2() {
		return MPraise2;
	}

	public void setMPraise2(String mPraise2) {
		MPraise2 = mPraise2;
	}

	@Override
	public String toString() {
		return "MPraise [MID=" + MID + ", MKUserID=" + MKUserID + ", MKMessageID=" + MKMessageID + ", MPraise1="
				+ MPraise1 + ", MPraise2=" + MPraise2 + "]";
	}

}
