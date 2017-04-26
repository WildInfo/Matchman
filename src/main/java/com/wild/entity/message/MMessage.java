package com.wild.entity.message;

import java.io.Serializable;
import java.util.Date;

import com.wild.enums.message.GetStatusEnum;
import com.wild.enums.message.MActivateStatusEnum;
import com.wild.enums.message.StatusEnum;

/**
 * 热点
 * 
 * @author Wild
 *
 */
public class MMessage implements Serializable {

	private static final long serialVersionUID = -7538107101352953698L;

	private String MID;// 主键ID
	private String MContent;// 消息内容
	private String MImage;// 消息图片
	private int MGrade;// 热点等级(游戏币数量)
	private String MAdress;// 消息地址
	private Date MDate;// 消息创建时间
	private MActivateStatusEnum MActivateStatus;// 消息激活状态
	private StatusEnum MStatus;// 消息状态
	private GetStatusEnum MGetStatus;// 消息状态
	private String MGetUser;// 备用字段1
	private String MGetNum;// 备用字段2
	private String MUserGC;// 备用字段3

	public MMessage(String mID, String mContent, String mImage, int mGrade, String mAdress, Date mDate,
			MActivateStatusEnum mActivateStatus, StatusEnum mStatus, GetStatusEnum mGetStatus, String mGetUser,
			String mGetNum, String MUserGC) {
		super();
		MID = mID;
		MContent = mContent;
		MImage = mImage;
		MGrade = mGrade;
		MAdress = mAdress;
		MDate = mDate;
		MActivateStatus = mActivateStatus;
		MStatus = mStatus;
		MGetStatus = mGetStatus;
		MGetUser = mGetUser;
		MGetNum = mGetNum;
		this.MUserGC = MUserGC;
	}

	public MMessage() {
		super();
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getMContent() {
		return MContent;
	}

	public void setMContent(String mContent) {
		MContent = mContent;
	}

	public String getMImage() {
		return MImage;
	}

	public void setMImage(String mImage) {
		MImage = mImage;
	}

	public int getMGrade() {
		return MGrade;
	}

	public void setMGrade(int mGrade) {
		MGrade = mGrade;
	}

	public String getMAdress() {
		return MAdress;
	}

	public void setMAdress(String mAdress) {
		MAdress = mAdress;
	}

	public Date getMDate() {
		return MDate;
	}

	public void setMDate(Date mDate) {
		MDate = mDate;
	}

	public MActivateStatusEnum getMActivateStatus() {
		return MActivateStatus;
	}

	public void setMActivateStatus(MActivateStatusEnum mActivateStatus) {
		MActivateStatus = mActivateStatus;
	}

	public StatusEnum getMStatus() {
		return MStatus;
	}

	public void setMStatus(StatusEnum mStatus) {
		MStatus = mStatus;
	}

	public GetStatusEnum getMGetStatus() {
		return MGetStatus;
	}

	public void setMGetStatus(GetStatusEnum mGetStatus) {
		MGetStatus = mGetStatus;
	}

	public String getMGetUser() {
		return MGetUser;
	}

	public void setMGetUser(String MGetUser) {
		this.MGetUser = MGetUser;
	}

	public String getMGetNum() {
		return MGetNum;
	}

	public void setMGetNum(String MGetNum) {
		this.MGetNum = MGetNum;
	}

	public String getMUserGC() {
		return MUserGC;
	}

	public void setMUserGC(String MUserGC) {
		this.MUserGC = MUserGC;
	}

	@Override
	public String toString() {
		return "MMessage [MID=" + MID + ", MContent=" + MContent + ", MImage=" + MImage + ", MGrade=" + MGrade
				+ ", MAdress=" + MAdress + ", MDate=" + MDate + ", MActivateStatus=" + MActivateStatus + ", MStatus="
				+ MStatus + ", MGetStatus=" + MGetStatus + ", MGetUser=" + MGetUser + ", MGetNum=" + MGetNum
				+ ", MUserGC=" + MUserGC + "]";
	}

}
