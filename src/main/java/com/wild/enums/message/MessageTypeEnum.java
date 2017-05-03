package com.wild.enums.message;

public enum MessageTypeEnum {
	// 消息 热点
	infomation("infomation"), message("message");

	private String desc;

	private MessageTypeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static boolean contain(String value) {
		MessageTypeEnum[] status = MessageTypeEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
