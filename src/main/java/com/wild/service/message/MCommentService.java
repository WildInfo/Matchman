package com.wild.service.message;

import java.util.List;
import java.util.Map;

import com.wild.entity.message.IInformation;
import com.wild.entity.message.MComment;
import com.wild.entity.message.MMessage;
import com.wild.entity.message.MMessageCommentRelation;

public interface MCommentService {

	/**
	 * 根据消息ID查询该消息的评论
	 * 
	 * @return
	 */
	public List<MComment> getComments(IInformation information);

	/**
	 * 插入评论
	 * 
	 * @param comment
	 * @return
	 */
	public int insertComment(MComment comment);

	/**
	 * 插入消息评论关系表
	 * 
	 * @param mcr
	 * @return
	 */
	public int insertIMC(MMessageCommentRelation mcr);

	/**
	 * 插入热点
	 * 
	 * @param message
	 * @return
	 */
	public int insertMessage(MMessage message);

	/**
	 * 根据热点id查询热点详情
	 * 
	 * @param message
	 * @return
	 */
	public MMessage selectMessage(MMessage message);

	/**
	 * 根据条件显示热点
	 * 
	 * @param params
	 * @return
	 */
	public List<MMessage> paramterMessage(Map<String, Object> params);

	/**
	 * 查看热点
	 * 
	 * @param commentRelation
	 * @return
	 */
	public List<MMessageCommentRelation> messageRelation(MMessageCommentRelation commentRelation);

	/**
	 * 更新热点状态
	 * 
	 * @param message
	 * @return
	 */
	public int updateMessageRev(MMessage message);
}
