package com.wild.enums.message;

public enum GetStatusEnum {
	// 领取  未领取
	received("1"), unreceived("0");

	private String desc;

	private GetStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static boolean contain(String value) {
		GetStatusEnum[] status = GetStatusEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
