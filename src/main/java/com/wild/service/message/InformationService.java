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
	
}
