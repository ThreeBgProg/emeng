package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.UserRole;

public interface UserRoleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserRole record);

	int insertSelective(UserRole record);

	UserRole selectByPrimaryKey(Integer id);

	/**
	 * 查询用户角色
	 * 
	 * @param id
	 * @return
	 */
	UserRole selectByUserId(Integer id);

	int updateByPrimaryKeySelective(UserRole record);

	int updateByPrimaryKey(UserRole record);

	/**
	 * 根据角色分页查询对应的用户数据
	 * 
	 * @param id
	 * @param fromIndex
	 * @param pageSize
	 * @return
	 */
	List<Integer> selectByRoleId(@Param("roleId")Integer roleId, @Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);

	int updateUserRole(UserRole record);

	/**
	 * 根据角色查询用户的总数
	 * 
	 * @param id
	 * @return
	 */
	int selectCountByRoleId(Integer id);
}