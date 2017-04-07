package com.wild.enums;

public enum AgeAndSexEnum {
	//年龄就四个选项    70 80 90 00 
	seventy("1"),eighty("2"),ninety("3"),zero("4");
	
	private String desc;
	private AgeAndSexEnum(String desc){
		this.desc=desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	public static boolean contain(String value) {
		AgeAndSexEnum[] status = AgeAndSexEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}
}

