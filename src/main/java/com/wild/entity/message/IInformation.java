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
	private String IUserId;// 当前用户GC
	private String IgdID;// 高德定位ID
	private String IhotID;// 热点编号
	
	private List<MComment> comments; //评论
	private List<MPraise> praises; //评论

	public IInformation(String iID, String iContent, String iImage, String iAdress, Date iDate, StatusEnum iStatus,
			String IUserId, String IgdID, String IhotID) {
		super();
		IID = iID;
		IContent = iContent;
		IImage = iImage;
		IAdress = iAdress;
		IDate = iDate;
		IStatus = iStatus;
		this.IUserId = IUserId;
		this.IgdID = IgdID;
		this.IhotID = IhotID;
	}

	public IInformation(String iID, String iContent, String iImage, String iAdress, Date iDate, StatusEnum iStatus,
			String iUserId, String IgdID, String IhotID, List<MComment> comments,
			List<MPraise> praises) {
		super();
		IID = iID;
		IContent = iContent;
		IImage = iImage;
		IAdress = iAdress;
		IDate = iDate;
		IStatus = iStatus;
		IUserId = iUserId;
		this.IgdID = IgdID;
		this.IhotID = IhotID;
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

	public String getIgdID() {
		return IgdID;
	}

	public void setIgdID(String IgdID) {
		this.IgdID = IgdID;
	}

	public String getIhotID() {
		return IhotID;
	}

	public void setIhotID(String IhotID) {
		this.IhotID = IhotID;
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
		return "IInformation ["+ "IID=" + IID + ", IContent=" + IContent + ", IImage=" + IImage
				+ ", IAdress=" + IAdress + ", IDate=" + IDate + ", IStatus=" + IStatus + ", IUserId=" + IUserId
				+ ", IgdID=" + IgdID + ", IhotID=" + IhotID + ", comments=" + comments + ", praises=" + praises + "]";
	}
	
}
