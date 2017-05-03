package com.wild.entity.user;

import java.util.Date;

public class HotFriend {
	private String fkuserid;//当前用户GC号
	private String fkfriendid;// 用户好友GC号
	private Date fcreatedat;//成为好友的时间
	private String wnickname;//好友昵称
	
	public HotFriend(String fkuserid, String fkfriendid, Date fcreatedat, String wnickname) {
		super();
		this.fkuserid = fkuserid;
		this.fkfriendid = fkfriendid;
		this.fcreatedat = fcreatedat;
		this.wnickname = wnickname;
	}

	public HotFriend() {
		super();
	}

	public String getFkuserid() {
		return fkuserid;
	}

	public void setFkuserid(String fkuserid) {
		this.fkuserid = fkuserid;
	}

	public String getFkfriendid() {
		return fkfriendid;
	}

	public void setFkfriendid(String fkfriendid) {
		this.fkfriendid = fkfriendid;
	}

	public Date getFcreatedat() {
		return fcreatedat;
	}

	public void setFcreatedat(Date fcreatedat) {
		this.fcreatedat = fcreatedat;
	}

	public String getWnickname() {
		return wnickname;
	}

	public void setWnickname(String wnickname) {
		this.wnickname = wnickname;
	}

	@Override
	public String toString() {
		return "HotFriend [fkuserid=" + fkuserid + ", fkfriendid=" + fkfriendid + ", fcreatedat=" + fcreatedat
				+ ", wnickname=" + wnickname + "]";
	}
	
	
}
