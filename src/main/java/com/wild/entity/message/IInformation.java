package com.wild.entity.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wild.enums.message.StatusEnum;


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
	private String IUserId;// 备用字段1
	private String IInformation2;// 备用字段2
	private String IInformation3;// 备用字段3
	
	private List<MComment> comments; //评论
	private List<MPraise> praises; //评论

	public IInformation(String iID, String iContent, String iImage, String iAdress, Date iDate, StatusEnum iStatus,
			String IUserId, String iInformation2, String iInformation3) {
		super();
		IID = iID;
		IContent = iContent;
		IImage = iImage;
		IAdress = iAdress;
		IDate = iDate;
		IStatus = iStatus;
		this.IUserId = IUserId;
		IInformation2 = iInformation2;
		IInformation3 = iInformation3;
	}

	public IInformation(String iID, String iContent, String iImage, String iAdress, Date iDate, StatusEnum iStatus,
			String iUserId, String iInformation2, String iInformation3, List<MComment> comments,
			List<MPraise> praises) {
		super();
		IID = iID;
		IContent = iContent;
		IImage = iImage;
		IAdress = iAdress;
		IDate = iDate;
		IStatus = iStatus;
		IUserId = iUserId;
		IInformation2 = iInformation2;
		IInformation3 = iInformation3;
		this.comments = comments;
		this.praises = praises;
	}

	public IInformation() {
		super();
	}

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

	public String getIUserId() {
		return IUserId;
	}

	public void setIUserId(String IUserId) {
		this.IUserId = IUserId;
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

	public List<MComment> getComments() {
		return comments;
	}

	public void setComments(List<MComment> comments) {
		this.comments = comments;
	}

	public List<MPraise> getPraises() {
		return praises;
	}

	public void setPraises(List<MPraise> praises) {
		this.praises = praises;
	}

	@Override
	public String toString() {
		return "\nIInformation [IID=" + IID + ", IContent=" + IContent + ", IImage=" + IImage + ", IAdress=" + IAdress
				+ ", IDate=" + IDate + ", IStatus=" + IStatus + ", IUserId=" + IUserId + ", IInformation2="
				+ IInformation2 + ", IInformation3=" + IInformation3 + ", \ncomments=" + comments + ", \npraises=" + praises
				+ "]";
	}
}
