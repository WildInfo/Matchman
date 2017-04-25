package com.wild.enums.user;

public enum AgeEnum {
	//年龄就四个选项    70 80 90 00 
	seventy(1),eighty(2),ninety(3),zero(4);
	
	private int desc;
	private AgeEnum(int desc){
		this.desc=desc;
	}

	public int getDesc() {
		return desc;
	}

	public void setDesc(int desc) {
		this.desc = desc;
	}
	public static boolean contain(String value) {
		AgeEnum[] status = AgeEnum.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

	
}

