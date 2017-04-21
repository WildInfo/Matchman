package com.wild.entity.message;

import java.io.Serializable;
import java.util.Date;

import com.wild.enums.StatusEnum;

/**
 * 消息
 * 
 * @author Wild
 *
 */
public class IInformation implements Serializable {

	private static final long serialVersionUID = 4228844647828167273L;

	private String IID;
	private String IContent;// 消息内容
	private String IImage;// 消息图片
	private String IAdress;// 消息地址
	private Date IDate;// 消息创建时间
	private StatusEnum IStatus;// 消息状态
	private String IInformation1;// 备用字段1
	private String IInformation2;// 备用字段2
	private String IInformation3;// 备用字段3

	public String getIID() {
		return IID;
	}

	public void setIID(String iID) {
		IID = iID;
	}

	public String getIContent() {
		return IContent;
	}

	public void setIContent(String iContent) {
		IContent = iContent;
	}

	public String getIAdress() {
		return IAdress;
	}

	public void setIAdress(String iAdress) {
		IAdress = iAdress;
	}

	public Date getIDate() {
		return IDate;
	}

	public void setIDate(Date iDate) {
		IDate = iDate;
	}

	public StatusEnum getIStatus() {
		return IStatus;
	}

	public void setIStatus(StatusEnum iStatus) {
		IStatus = iStatus;
	}

	public String getIInformation1() {
		return IInformation1;
	}

	public void setIInformation1(String iInformation1) {
		IInformation1 = iInformation1;
	}

	public String getIInformation2() {
		return IInformation2;
	}

	public void setIInformation2(String iInformation2) {
		IInformation2 = iInformation2;
	}

	public String getIInformation3() {
		return IInformation3;
	}

	public void setIInformation3(String iInformation3) {
		IInformation3 = iInformation3;
	}

	public String getIImage() {
		return IImage;
	}

	public void setIImage(String iImage) {
		IImage = iImage;
	}

}
