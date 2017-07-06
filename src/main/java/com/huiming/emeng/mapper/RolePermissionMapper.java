package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.RolePermission;

public interface RolePermissionMapper {
	int deleteByPrimaryKey(Integer id);
	
	int deleteByRolePermission(RolePermission record);
	
	int deleteByPermissionId(Integer id);
	
	int deleteByRoleId(Integer id);

	int insert(RolePermission record);

	int insertSelective(RolePermission record);

	RolePermission selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RolePermission record);

	int updateByPrimaryKey(RolePermission record);
	
	List<Integer> selectAllByRoleId(Integer roleId);
	
	RolePermission selectSelective(RolePermission record);
}