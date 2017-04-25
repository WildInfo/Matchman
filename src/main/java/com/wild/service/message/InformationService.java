package com.wild.service.message;

import java.util.List;

import com.wild.entity.message.IInformation;

public interface InformationService {
	/**
	 * 获取发布的公开信息
	 * @return
	 */
	public List<IInformation> getPublicNews(String address);
	
	/**
	 * 插入发布的公开信息
	 */
	public int insertPublicNews(IInformation infomation);
	
	/**
	 * 获取消息详情
	 * @param information：要查看的消息
	 * @return
	 */
	public List<IInformation> getInfoDetails(IInformation information);
	
	/**
	 * 陌生人获取消息详情
	 * @param information：要查看的消息
	 * @return
	 */
	public List<IInformation> getInfoDetailsByStrenger(IInformation information);
	
}
