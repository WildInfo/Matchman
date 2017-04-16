package com.wild.entity.message;

import java.io.Serializable;
import java.util.Date;

/**
 * 热点评论表
 * 
 * @author Wild
 *
 */
public class MComment implements Serializable {

	private static final long serialVersionUID = 2878458427916251826L;

	private String MID;// 主键ID
	private String MOwnerUser;// 发表评论的用户
	private String MTargetUser;// 评论的目标用户
	private String MContent;// 评论内容
	private int MLikeCount;// 该热点被点赞的数量
	private Date MCreatedAt;// 消息创建时间
	private String MParent;// 评论目标ID
	private String MParentType;// 评论目标类型
	private String MMessage1;// 备用字段1
	private String MMessage2;// 备用字段2
	private String MMessage3;// 备用字段3

	public MComment(String mID, String mOwnerUser, String mTargetUser, String mContent, int mLikeCount, Date mCreatedAt,
			String mParent, String mParentType, String mMessage1, String mMessage2, String mMessage3) {
		super();
		MID = mID;
		MOwnerUser = mOwnerUser;
		MTargetUser = mTargetUser;
		MContent = mContent;
		MLikeCount = mLikeCount;
		MCreatedAt = mCreatedAt;
		MParent = mParent;
		MParentType = mParentType;
		MMessage1 = mMessage1;
		MMessage2 = mMessage2;
		MMessage3 = mMessage3;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getMOwnerUser() {
		return MOwnerUser;
	}

	public void setMOwnerUser(String mOwnerUser) {
		MOwnerUser = mOwnerUser;
	}

	public String getMTargetUser() {
		return MTargetUser;
	}

	public void setMTargetUser(String mTargetUser) {
		MTargetUser = mTargetUser;
	}

	public String getMContent() {
		return MContent;
	}

	public void setMContent(String mContent) {
		MContent = mContent;
	}

	public int getMLikeCount() {
		return MLikeCount;
	}

	public void setMLikeCount(int mLikeCount) {
		MLikeCount = mLikeCount;
	}

	public Date getMCreatedAt() {
		return MCreatedAt;
	}

	public void setMCreatedAt(Date mCreatedAt) {
		MCreatedAt = mCreatedAt;
	}

	public String getMParent() {
		return MParent;
	}

	public void setMParent(String mParent) {
		MParent = mParent;
	}

	public String getMParentType() {
		return MParentType;
	}

	public void setMParentType(String mParentType) {
		MParentType = mParentType;
	}

	public String getMMessage1() {
		return MMessage1;
	}

	public void setMMessage1(String mMessage1) {
		MMessage1 = mMessage1;
	}

	public String getMMessage2() {
		return MMessage2;
	}

	public void setMMessage2(String mMessage2) {
		MMessage2 = mMessage2;
	}

	public String getMMessage3() {
		return MMessage3;
	}

	public void setMMessage3(String mMessage3) {
		MMessage3 = mMessage3;
	}

}
