package com.wild.service.impl.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.message.IInformation;
import com.wild.mapper.message.InformationMapper;
import com.wild.service.message.InformationService;

@Service("informationService")
public class InformationServiceImpl implements InformationService {
	
	@Autowired
	private InformationMapper informationMapper;

	@Override
	public List<IInformation> getPublicNews(String address) {
		return informationMapper.getPublicNews(address);
	}

	@Override
	public int insertPublicNews(IInformation infomation) {
		return informationMapper.insertPublicNews(infomation);
	}
}
