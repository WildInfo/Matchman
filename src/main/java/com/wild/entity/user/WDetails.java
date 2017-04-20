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
	private String WPersonalized;// 个性签名
	private String WHobbies;// 兴趣爱好
	private String WIntroduce;// 介绍自己
	private int WCurrency;// 游戏币
	private String WHeadImage;// 头像地址
	private String WBarcode;// 二维码地址
	private String WDetails1;//备用字段
	private String WDetails2;//备用字段
	private String WDetails3;//备用字段

	public WDetails() {
		super();
	}

	public WDetails(String wID, String wPersonalized, String wHobbies, String wIntroduce, int wCurrency,
			String wHeadImage, String wBarcode, String wDetails1, String wDetails2, String wDetails3) {
		WID = wID;
		WPersonalized = wPersonalized;
		WHobbies = wHobbies;
		WIntroduce = wIntroduce;
		WCurrency = wCurrency;
		WHeadImage = wHeadImage;
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

	public String getWPersonalized() {
		return WPersonalized;
	}

	public void setWPersonalized(String wPersonalized) {
		WPersonalized = wPersonalized;
	}

	public String getWHobbies() {
		return WHobbies;
	}

	public void setWHobbies(String wHobbies) {
		WHobbies = wHobbies;
	}

	public String getWIntroduce() {
		return WIntroduce;
	}

	public void setWIntroduce(String wIntroduce) {
		WIntroduce = wIntroduce;
	}

	public int getWCurrency() {
		return WCurrency;
	}

	public void setWCurrency(int wCurrency) {
		WCurrency = wCurrency;
	}

	public String getWHeadImage() {
		return WHeadImage;
	}

	public void setWHeadImage(String wHeadImage) {
		WHeadImage = wHeadImage;
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

}
