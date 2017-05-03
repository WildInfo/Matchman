package com.wild.service.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wild.entity.user.CheckUserInfo;
import com.wild.entity.user.FriendList;
import com.wild.entity.user.FriendShip;
import com.wild.entity.user.HotComment;
import com.wild.entity.user.HotFriend;
import com.wild.entity.user.HotPraise;
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

	@Override
	public int addFriend(String fid, String userId, String friendId, Date addTime) {
		return friendShipMapper.addFriend(fid, userId, friendId, addTime);
	}

	@Override
	public int updateHotNum(String userId, String friendId) {
		return friendShipMapper.updateHotNum(userId, friendId);
	}

	@Override
	public List<FriendList> getFriendList(String userid) {
		return friendShipMapper.getFriendList(userid);
	}

	@Override
	public HotFriend getHotFriend(String userid) {
		return friendShipMapper.getHotFriend(userid);
	}

	@Override
	public CheckUserInfo getHotCheck(String userid) {
		return friendShipMapper.getHotCheck(userid);
	}

	@Override
	public HotPraise hotPraiseNew(String userid) {
		return friendShipMapper.hotPraiseNew(userid);
	}

	@Override
	public HotPraise hotPraiseMessage(String userid) {
		return friendShipMapper.hotPraiseMessage(userid);
	}

	@Override
	public HotComment hotCommentNew(String userid) {
		return friendShipMapper.hotCommentNew(userid);
	}

	@Override
	public HotComment hotCommentMessage(String userid) {
		return friendShipMapper.hotCommentMessage(userid);
	}

	@Override
	public Date hotFriendMessage(String userid) {
		return friendShipMapper.hotFriendMessage(userid);
	}
}
