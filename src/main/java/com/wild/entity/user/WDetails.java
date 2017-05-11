package com.wild.entity.user;

import java.io.Serializable;

/**
 * 用户详情
 * 
 * @author Wild
 *
 */
public class WDetails implements Serializable {

	private static final long serialVersionUID = -5753757172875401160L;

	private String WID;// 用户详情id
	private String signature;// 个性签名
	private String interest;// 兴趣爱好
	private String introduce;// 介绍自己
	private int WCurrency;// 游戏币
	private String headImage;// 头像地址
	private String WBarcode;// 二维码地址
	private String WDetails1;//备用字段
	private String WDetails2;//备用字段
	private String WDetails3;//备用字段

	public WDetails() {
		super();
	}

	
	public WDetails(String wID, String signature, String interest, String introduce, int wCurrency, String headImage,
			String wBarcode, String wDetails1, String wDetails2, String wDetails3) {
		super();
		WID = wID;
		this.signature = signature;
		this.interest = interest;
		this.introduce = introduce;
		WCurrency = wCurrency;
		this.headImage = headImage;
		WBarcode = wBarcode;
		WDetails1 = wDetails1;
		WDetails2 = wDetails2;
		WDetails3 = wDetails3;
	}


	public String getWID() {
		return WID;
	}

	public void setWID(String wID) {
		WID = wID;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getWCurrency() {
		return WCurrency;
	}

	public void setWCurrency(int wCurrency) {
		WCurrency = wCurrency;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getWBarcode() {
		return WBarcode;
	}

	public void setWBarcode(String wBarcode) {
		WBarcode = wBarcode;
	}

	public String getWDetails1() {
		return WDetails1;
	}

	public void setWDetails1(String wDetails1) {
		WDetails1 = wDetails1;
	}

	public String getWDetails2() {
		return WDetails2;
	}

	public void setWDetails2(String wDetails2) {
		WDetails2 = wDetails2;
	}

	public String getWDetails3() {
		return WDetails3;
	}

	public void setWDetails3(String wDetails3) {
		WDetails3 = wDetails3;
	}

	@Override
	public String toString() {
		return "WDetails [WID=" + WID + ", signature=" + signature + ", interest=" + interest + ", introduce="
				+ introduce + ", WCurrency=" + WCurrency + ", headImage=" + headImage + ", WBarcode=" + WBarcode
				+ ", WDetails1=" + WDetails1 + ", WDetails2=" + WDetails2 + ", WDetails3=" + WDetails3 + "]";
	}

}
