package com.wild.entity.user;

import java.io.Serializable;
import java.util.Date;

import com.wild.enums.message.StatusEnum;
import com.wild.enums.user.UserVersioniEnum;

/**
 * 用户
 * 
 * @author Wild
 *
 */
public class WUser implements Serializable {

	private static final long serialVersionUID = 1632537799039372010L;

	private String WID;// 用户id
	private String WGCNum;// GC号
	private String WNickName;// 用户昵称
	private int WSex;// 用户性别
	private String WUserNum;// 用户帐号
	private String WPassWord;// 用户密码
	private int WAge;// 用户年龄
	private Date WDate;// 注册时间
	private StatusEnum WStatus;// 用户状态
	private UserVersioniEnum WSuperManager;// 用户角色

	
	public WUser(String wID, String wGCNum, String wNickName, int wSex, String wUserNum, String wPassWord, int wAge,
			Date wDate, StatusEnum wStatus, UserVersioniEnum wSuperManager) {
		super();
		WID = wID;
		WGCNum = wGCNum;
		WNickName = wNickName;
		WSex = wSex;
		WUserNum = wUserNum;
		WPassWord = wPassWord;
		WAge = wAge;
		WDate = wDate;
		WStatus = wStatus;
		WSuperManager = wSuperManager;
	}

	public WUser() {
		super();
	}

	public String getWID() {
		return WID;
	}

	public void setWID(String wID) {
		WID = wID;
	}

	public String getWGCNum() {
		return WGCNum;
	}

	public void setWGCNum(String wGCNum) {
		WGCNum = wGCNum;
	}

	public String getWNickName() {
		return WNickName;
	}

	public void setWNickName(String wNickName) {
		WNickName = wNickName;
	}

	public int getWSex() {
		return WSex;
	}

	public void setWSex(int wSex) {
		WSex = wSex;
	}

	public String getWUserNum() {
		return WUserNum;
	}

	public void setWUserNum(String wUserNum) {
		WUserNum = wUserNum;
	}

	public String getWPassWord() {
		return WPassWord;
	}

	public void setWPassWord(String wPassWord) {
		WPassWord = wPassWord;
	}

	public int getWAge() {
		return WAge;
	}

	public void setWAge(int wAge) {
		WAge = wAge;
	}

	public Date getWDate() {
		return WDate;
	}

	public void setWDate(Date wDate) {
		WDate = wDate;
	}

	public StatusEnum getWStatus() {
		return WStatus;
	}

	public void setWStatus(StatusEnum wStatus) {
		WStatus = wStatus;
	}

	public UserVersioniEnum getWSuperManager() {
		return WSuperManager;
	}

	public void setWSuperManager(UserVersioniEnum wSuperManager) {
		WSuperManager = wSuperManager;
	}

	@Override
	public String toString() {
		return "WUser [WID=" + WID + ", WGCNum=" + WGCNum + ", WNickName=" + WNickName + ", WSex=" + WSex
				+ ", WUserNum=" + WUserNum + ", WPassWord=" + WPassWord + ", WAge=" + WAge + ", WDate=" + WDate
				+ ", WStatus=" + WStatus + ", WSuperManager=" + WSuperManager + "]";
	}
}
