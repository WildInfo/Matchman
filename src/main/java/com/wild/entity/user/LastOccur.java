package com.wild.entity.user;

import java.util.Date;

public class LastOccur {

	private String LID;   //编号
	private String LUserGC;  //用户GC号
	private Date LTime;  //最新以此出现的时间
	private String LAddress;  //最近一次出现的地点
	
	public LastOccur(String lID, String lUserGC, Date lTime, String lAddress) {
		super();
		LID = lID;
		LUserGC = lUserGC;
		LTime = lTime;
		LAddress = lAddress;
	}

	public LastOccur() {
		super();
	}

	public String getLID() {
		return LID;
	}

	public void setLID(String lID) {
		LID = lID;
	}

	public String getLUserGC() {
		return LUserGC;
	}

	public void setLUserGC(String lUserGC) {
		LUserGC = lUserGC;
	}

	public Date getLTime() {
		return LTime;
	}

	public void setLTime(Date lTime) {
		LTime = lTime;
	}

	public String getLAddress() {
		return LAddress;
	}

	public void setLAddress(String lAddress) {
		LAddress = lAddress;
	}

	@Override
	public String toString() {
		return "LastOccur [LID=" + LID + ", LUserGC=" + LUserGC + ", LTime=" + LTime + ", LAddress=" + LAddress + "]";
	}
	
}
