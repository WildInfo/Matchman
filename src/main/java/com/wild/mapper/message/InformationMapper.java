package com.wild.mapper.message;

import java.util.List;

import com.wild.entity.message.IInformation;
import com.wild.entity.message.MMessageCommentRelation;

public interface InformationMapper {
	
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
	 * 插入消息评论关系表
	 * @param mcr
	 * @return
	 */
	public int insertIMC(MMessageCommentRelation mcr);
	
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
