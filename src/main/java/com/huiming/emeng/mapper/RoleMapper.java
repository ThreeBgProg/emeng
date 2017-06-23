package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Role;

public interface RoleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);

	List<Role> selectByRolename(@Param("rolename")String rolename,@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);

	int selectCount();

	Role selectRole(String rolename);

	public List<Role> selectAll();
}