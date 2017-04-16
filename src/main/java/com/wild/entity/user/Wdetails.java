package com.wild.entity.user;

import java.io.Serializable;

/**
 * 用户详情
 * 
 * @author Wild
 *
 */
public class Wdetails implements Serializable {

	private static final long serialVersionUID = -5753757172875401160L;

	private String WID;// 用户详情id
	private String WPersonalized;// 个性签名
	private String WHobbies;// 兴趣爱好
	private String WIntroduce;// 介绍自己
	private String WCurrency;// 游戏币
	private String WHeadImage;// 头像地址
	private String WBarcode;// 二维码地址

	public Wdetails(String wID, String wPersonalized, String wHobbies, String wIntroduce, String wCurrency,
			String wHeadImage, String wBarcode) {
		WID = wID;
		WPersonalized = wPersonalized;
		WHobbies = wHobbies;
		WIntroduce = wIntroduce;
		WCurrency = wCurrency;
		WHeadImage = wHeadImage;
		WBarcode = wBarcode;
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

	public String getWCurrency() {
		return WCurrency;
	}

	public void setWCurrency(String wCurrency) {
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

	@Override
	public String toString() {
		return "Wdetails [WID=" + WID + ", WPersonalized=" + WPersonalized + ", WHobbies=" + WHobbies + ", WIntroduce="
				+ WIntroduce + ", WCurrency=" + WCurrency + ", WHeadImage=" + WHeadImage + ", WBarcode=" + WBarcode
				+ "]";
	}

}
