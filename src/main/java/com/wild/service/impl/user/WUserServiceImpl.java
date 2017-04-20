package com.wild.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.user.WDetails;
import com.wild.entity.user.WUser;
import com.wild.entity.user.WUserDetailsRelation;
import com.wild.mapper.user.WUserMapper;
import com.wild.service.user.WUserService;

@Service("wUserService")
public class WUserServiceImpl implements WUserService {

	@Autowired
	private WUserMapper wuserMapper;

	@Override
	public int register(WUser user) {
		return wuserMapper.register(user);
	}

	@Override
	public List<WUser> login(WUser user) {
		return wuserMapper.login(user);
	}

	@Override
	public int lostPassWord(WUser user) {
		return wuserMapper.lostPassWord(user);
	}

	@Override
	public String telForOnly(String num) {
		return wuserMapper.telForOnly(num);
	}

	@Override
	public int updateUserDetails(WDetails details) {
		return wuserMapper.updateUserDetails(details);
	}

	@Override
	public int userDetailsDelation(WUserDetailsRelation detailsRelation) {
		return wuserMapper.userDetailsDelation(detailsRelation);
	}

	@Override
	public List<WUser> userDetils(String wid) {
		return wuserMapper.userDetils(wid);
	}

	@Override
	public List<WUserDetailsRelation> userDetilsById(WUserDetailsRelation detailsRelation) {
		return wuserMapper.userDetilsById(detailsRelation);
	}

	@Override
	public int insertDtails(WDetails details) {
		return wuserMapper.insertDtails(details);
	}

}
