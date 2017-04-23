package com.wild.service.message;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wild.entity.message.IInformation;
import com.wild.entity.message.MMessage;
import com.wild.entity.message.MMessageCommentRelation;

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
