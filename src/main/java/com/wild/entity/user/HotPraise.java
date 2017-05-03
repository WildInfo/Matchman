package com.wild.entity.user;

import java.util.Date;

public class HotPraise {
	private String mkmessageid;//消息ID
	private String mkuserid;//用户ID
	private Date mpraise1;//点赞时间
	
	public HotPraise(String mkmessageid, String mkuserid, Date mpraise1) {
		super();
		this.mkmessageid = mkmessageid;
		this.mkuserid = mkuserid;
		this.mpraise1 = mpraise1;
	}

	public HotPraise() {
		super();
	}

	public String getMkmessageid() {
		return mkmessageid;
	}

	public void setMkmessageid(String mkmessageid) {
		this.mkmessageid = mkmessageid;
	}

	public String getMkuserid() {
		return mkuserid;
	}

	public void setMkuserid(String mkuserid) {
		this.mkuserid = mkuserid;
	}

	public Date getMpraise1() {
		return mpraise1;
	}

	public void setMpraise1(Date mpraise1) {
		this.mpraise1 = mpraise1;
	}

	@Override
	public String toString() {
		return "HotPraise [mkmessageid=" + mkmessageid + ", mkuserid=" + mkuserid + ", mpraise1=" + mpraise1 + "]";
	}
}
