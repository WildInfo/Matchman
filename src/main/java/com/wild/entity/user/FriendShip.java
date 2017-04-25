package com.wild.entity.user;

import java.util.Date;

public class FriendShip {
	
	private String FID;
	private String FKUserID;
	private String FKFriendID;
	private Date FCreatedAt;
	private String FFriend1;
	private String FFriend2;
	
	public FriendShip(String fID, String fKUserID, String fKFriendID, Date fCreatedAt, String fFriend1,
			String fFriend2) {
		super();
		FID = fID;
		FKUserID = fKUserID;
		FKFriendID = fKFriendID;
		FCreatedAt = fCreatedAt;
		FFriend1 = fFriend1;
		FFriend2 = fFriend2;
	}

	public FriendShip() {
		super();
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFKUserID() {
		return FKUserID;
	}

	public void setFKUserID(String fKUserID) {
		FKUserID = fKUserID;
	}

	public String getFKFriendID() {
		return FKFriendID;
	}

	public void setFKFriendID(String fKFriendID) {
		FKFriendID = fKFriendID;
	}

	public Date getFCreatedAt() {
		return FCreatedAt;
	}

	public void setFCreatedAt(Date fCreatedAt) {
		FCreatedAt = fCreatedAt;
	}

	public String getFFriend1() {
		return FFriend1;
	}

	public void setFFriend1(String fFriend1) {
		FFriend1 = fFriend1;
	}

	public String getFFriend2() {
		return FFriend2;
	}

	public void setFFriend2(String fFriend2) {
		FFriend2 = fFriend2;
	}

	@Override
	public String toString() {
		return "FriendShip [FID=" + FID + ", FKUserID=" + FKUserID + ", FKFriendID=" + FKFriendID + ", FCreatedAt="
				+ FCreatedAt + ", FFriend1=" + FFriend1 + ", FFriend2=" + FFriend2 + "]";
	}
	
}
