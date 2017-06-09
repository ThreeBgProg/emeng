package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.UserRole;

public interface UserRoleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserRole record);

	int insertSelective(UserRole record);

	UserRole selectByPrimaryKey(Integer id);
	
	UserRole selectByUserId(Integer id);

	int updateByPrimaryKeySelective(UserRole record);

	int updateByPrimaryKey(UserRole record);
	
	List<Integer> selectByRoleId(Integer id);
}