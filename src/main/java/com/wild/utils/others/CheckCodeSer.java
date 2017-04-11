package com.wild.utils.others;

import java.io.Serializable;

public class CheckCodeSer implements Serializable {

	private static final long serialVersionUID = 7583099357393433155L;
	private String checkCode;   //生成的验证码
	private String beginDate;   //生成验证码的时间
	private String tel;    //验证码对应的电话号
	
	public CheckCodeSer(String checkCode, String beginDate, String tel) {
		this.checkCode = checkCode;
		this.beginDate = beginDate;
		this.tel = tel;
	}

	public CheckCodeSer() {
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CheckCodeSer [checkCode=" + checkCode + ", beginDate=" + beginDate + ", tel=" + tel + "]";
	}

}
