package com.wild.mapper.user;

import java.util.List;

import com.wild.entity.user.FriendShip;
import com.wild.entity.user.WUser;

public interface FriendShipMapper {
	
	/**
	 * 获取用户的好友
	 * @param user
	 * @return
	 */
	public List<FriendShip> getFriends(String userid);
	
}
