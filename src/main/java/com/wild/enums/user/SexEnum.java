package com.wild.enums.user;

public enum SexEnum {
	//年龄就四个选项    70 80 90 00 
	man(0),woman(1);
	
	private int desc;
	private SexEnum(int desc){
		this.desc=desc;
	}

	public int getDesc() {
		return desc;
	}

	public void setDesc(int desc) {
		this.desc = desc;
	}
	public static boolean contain(String value) {
		SexEnum[] status = SexEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}
}

