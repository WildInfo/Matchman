package com.wild.service.user;

import java.util.List;

import com.wild.entity.user.FriendShip;
import com.wild.entity.user.WUser;

public interface FriendShipService {
	
	/**
	 * 获取用户的好友
	 * @param user
	 * @return
	 */
	public List<FriendShip> getFriends(String userid);
	
}
