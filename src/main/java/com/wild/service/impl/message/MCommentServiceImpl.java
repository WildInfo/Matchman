package com.wild.service.impl.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.message.IInformation;
import com.wild.entity.message.MComment;
import com.wild.entity.message.MMessage;
import com.wild.entity.message.MMessageCommentRelation;
import com.wild.mapper.message.MCommentMapper;
import com.wild.service.message.MCommentService;

@Service("mCommentService")
public class MCommentServiceImpl implements MCommentService {

	@Autowired
	private MCommentMapper commentMapper;

	@Override
	public List<MComment> getComments(IInformation information) {
		return commentMapper.getComments(information);
	}

	@Override
	public int insertComment(MComment comment) {
		return commentMapper.insertComment(comment);
	}

	@Override
	public int insertIMC(MMessageCommentRelation mcr) {
		return commentMapper.insertIMC(mcr);
	}

	@Override
	public int insertMessage(MMessage message) {
		return commentMapper.insertMessage(message);
	}

	@Override
	public List<MMessage> selectMessage(MMessage message) {
		return commentMapper.selectMessage(message);
	}

}
