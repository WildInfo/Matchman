package com.wild.mapper.user;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wild.entity.user.FriendList;
import com.wild.entity.user.FriendShip;

public interface FriendShipMapper {
	
	/**
	 * 获取用户的好友
	 * @param user
	 * @return
	 */
	public List<FriendShip> getFriends(String userid);
	
	/**
	 * 添加好友
	 * @param fid：编号
	 * @param userId：当前用户GC号
	 * @param friendId：要添加的好友GC号
	 * @param addTime：添加时间
	 * @return
	 */
	public int addFriend(@Param("fid")String fid,@Param("userId")String userId,@Param("friendId")String friendId,@Param("addTime")Date addTime);
	
	/**
	 * 更改好友热度
	 * @param userId：用户GC号
	 * @param friendId：好友GC号
	 * @return
	 */
	public int updateHotNum(@Param("userId")String userId,@Param("friendId")String friendId);
	
	/**
	 * 查询好友列表
	 * @param userid：用户GC号
	 * @return
	 */
	public List<FriendList> getFriendList(@Param("userid")String userid);
}
