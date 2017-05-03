package com.wild.mapper.message;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.wild.entity.user.WUser;

public interface PraiseMapper {

	int addPraise(@Param("id")String id, @Param("userLogin")WUser userLogin, @Param("inId")String inId,@Param("MPraise1")Date date);

}
