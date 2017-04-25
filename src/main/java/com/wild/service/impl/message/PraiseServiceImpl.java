package com.wild.service.impl.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.user.WUser;
import com.wild.mapper.message.PraiseMapper;
import com.wild.service.message.PraiseService;

@Service("praiseServiceImpl")
public class PraiseServiceImpl implements PraiseService {

	@Autowired
	private PraiseMapper praiseMapper;
	
	@Override
	public int addPraise(String id, WUser userLogin, String inId) {
		return praiseMapper.addPraise(id,userLogin,inId);
	}

}
