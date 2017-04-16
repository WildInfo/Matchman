package com.wild.enums.message;

public enum MActivateStatusEnum {
	// 激活  未激活
	activated("1"), unactivated("0");

	private String desc;

	private MActivateStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static boolean contain(String value) {
		MActivateStatusEnum[] status = MActivateStatusEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
