package com.wild.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.user.FriendShip;
import com.wild.entity.user.WUser;
import com.wild.mapper.user.FriendShipMapper;
import com.wild.service.user.FriendShipService;

@Service("friendShipService")
public class FriendShipServiceImpl implements FriendShipService {

	@Autowired
	private FriendShipMapper friendShipMapper;
	
	@Override
	public List<FriendShip> getFriends(String userid) {
		return friendShipMapper.getFriends(userid);
	}

}
