package com.wild.entity.user;

public class FriendList {
	
	private String wgcNum;//用户GC号
	private String wnickName;//用户昵称
	private String wheadImage;//用户头像地址
	private String lTime;//最近一次出现的时间
	private String laddress;//最近一次出现的地址
	private int wage;//年龄段
	
	public FriendList(String wgcNum, String wnickName, String wheadImage, String lTime, String laddress, int wage) {
		super();
		this.wgcNum = wgcNum;
		this.wnickName = wnickName;
		this.wheadImage = wheadImage;
		this.lTime = lTime;
		this.laddress = laddress;
		this.wage = wage;
	}

	public FriendList() {
		super();
	}

	public String getWgcNum() {
		return wgcNum;
	}

	public void setWgcNum(String wgcNum) {
		this.wgcNum = wgcNum;
	}

	public String getWnickName() {
		return wnickName;
	}

	public void setWnickName(String wnickName) {
		this.wnickName = wnickName;
	}

	public String getWheadImage() {
		return wheadImage;
	}

	public void setWheadImage(String wheadImage) {
		this.wheadImage = wheadImage;
	}

	public String getlTime() {
		return lTime;
	}

	public void setlTime(String lTime) {
		this.lTime = lTime;
	}

	public String getLaddress() {
		return laddress;
	}

	public void setLaddress(String laddress) {
		this.laddress = laddress;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	@Override
	public String toString() {
		return "FriendList [wgcNum=" + wgcNum + ", wnickName=" + wnickName + ", wheadImage=" + wheadImage + ", lTime="
				+ lTime + ", laddress=" + laddress + ", wage=" + wage + "]";
	}
}
