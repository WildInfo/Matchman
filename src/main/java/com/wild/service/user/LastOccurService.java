package com.wild.service.user;

import org.apache.ibatis.annotations.Param;

import com.wild.entity.user.LastOccur;

public interface LastOccurService {

	/**
	 * 插入lastOccur表
	 * @param lastOccur
	 * @return
	 */
	public LastOccur insertLastOccur(LastOccur lastOccur);
	
	/**
	 * 查询lastOccur
	 * @param userGC
	 * @return
	 */
	public LastOccur selectLastOccur(@Param("userGC")String userGC);
	
	/**
	 * 更新用户最新动态
	 * @return
	 */
	public int updateLastOccur(LastOccur lastOccur);
	
}
