package com.wild.enums.user;

public enum UserVersioniEnum {
	//超级管理员  一般管理员  普通用户
	supervision("超级管理员"), manversion("一般管理员"),common("普通用户");

	private String desc;

	private UserVersioniEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static boolean contain(String value) {
		UserStatusEnum[] status = UserStatusEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
