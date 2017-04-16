package com.wild.entity.message;

import java.io.Serializable;

/**
 * 热点与评论关系
 * 
 * @author Wild
 *
 */
public class MMessageCommentRelation implements Serializable {

	private static final long serialVersionUID = -2001468437579196488L;

	private String MID;// 主键ID
	private String MKMessageID;// 热点ID
	private String MKUserID;// 用户ID
	private String MKCommentID;// 评论ID

	public MMessageCommentRelation(String mID, String mKMessageID, String mKUserID, String mKCommentID) {
		super();
		MID = mID;
		MKMessageID = mKMessageID;
		MKUserID = mKUserID;
		MKCommentID = mKCommentID;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getMKMessageID() {
		return MKMessageID;
	}

	public void setMKMessageID(String mKMessageID) {
		MKMessageID = mKMessageID;
	}

	public String getMKUserID() {
		return MKUserID;
	}

	public void setMKUserID(String mKUserID) {
		MKUserID = mKUserID;
	}

	public String getMKCommentID() {
		return MKCommentID;
	}

	public void setMKCommentID(String mKCommentID) {
		MKCommentID = mKCommentID;
	}

}
