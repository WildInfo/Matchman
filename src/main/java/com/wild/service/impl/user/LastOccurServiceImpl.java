package com.wild.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.user.LastOccur;
import com.wild.mapper.user.LastOccurMapper;
import com.wild.service.user.LastOccurService;

@Service("lastOccurService")
public class LastOccurServiceImpl implements LastOccurService {

	@Autowired
	private LastOccurMapper lastOccurMapper;
	
	@Override
	public LastOccur insertLastOccur(LastOccur lastOccur) {
		return lastOccurMapper.insertLastOccur(lastOccur);
	}

	@Override
	public LastOccur selectLastOccur(String userGC) {
		return lastOccurMapper.selectLastOccur(userGC);
	}

	@Override
	public int updateLastOccur(LastOccur lastOccur) {
		return lastOccurMapper.updateLastOccur(lastOccur);
	}

}
