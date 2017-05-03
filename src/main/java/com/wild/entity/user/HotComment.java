package com.wild.entity.user;

import java.util.Date;

public class HotComment {
	private String mowneruser;//评论的用户
	private Date mcreatedat;//评论的时间
	
	public HotComment(String mowneruser, Date mcreatedat) {
		super();
		this.mowneruser = mowneruser;
		this.mcreatedat = mcreatedat;
	}

	public HotComment() {
		super();
	}

	public String getMowneruser() {
		return mowneruser;
	}

	public void setMowneruser(String mowneruser) {
		this.mowneruser = mowneruser;
	}

	public Date getMcreatedat() {
		return mcreatedat;
	}

	public void setMcreatedat(Date mcreatedat) {
		this.mcreatedat = mcreatedat;
	}

	@Override
	public String toString() {
		return "HotComment [mowneruser=" + mowneruser + ", mcreatedat=" + mcreatedat + "]";
	}
}
