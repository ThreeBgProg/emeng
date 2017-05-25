package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.RolePermission;

public interface RolePermissionMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(RolePermission record);

	int insertSelective(RolePermission record);

	RolePermission selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RolePermission record);

	int updateByPrimaryKey(RolePermission record);
	
	List<RolePermission> selectAllByRoleId(Integer roleId);
}