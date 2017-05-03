package com.wild.entity.user;

import java.util.Date;

public class CheckUserInfo {
	private String checkusergc;//正在查看的用户GC号
	private String targetusergc;//被查看用户的GC号
	private Date checktime;//查看时间
	
	public CheckUserInfo(String checkusergc, String targetusergc, Date checktime) {
		super();
		this.checkusergc = checkusergc;
		this.targetusergc = targetusergc;
		this.checktime = checktime;
	}

	public CheckUserInfo() {
		super();
	}

	public String getCheckusergc() {
		return checkusergc;
	}

	public void setCheckusergc(String checkusergc) {
		this.checkusergc = checkusergc;
	}

	public String getTargetusergc() {
		return targetusergc;
	}

	public void setTargetusergc(String targetusergc) {
		this.targetusergc = targetusergc;
	}

	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Override
	public String toString() {
		return "CheckUserInfo [checkusergc=" + checkusergc + ", targetusergc=" + targetusergc + ", checktime="
				+ checktime + "]";
	}
}
