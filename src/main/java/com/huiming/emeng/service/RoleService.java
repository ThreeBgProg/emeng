package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;

public interface RoleService {
	int deleteByPrimaryKey(Integer id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);

	/**
	 * 分页获取所有角色
	 * 
	 * @return
	 */
    Pager<Role> selectAllByPage(Integer currentPage, Integer pageSize);

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
    List<Role> selectAll();

	/**
	 * 添加角色的权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
    int insertRolePermission(Integer roleId, Integer permissionId);

	/**
	 * 获取角色对应的权限
	 * 
	 * @param id
	 * @return
	 */
    List<Permission> selectPermissionByRoleId(Integer id);

	/**
	 * 删除角色的某个权限
	 * 
	 * @param record
	 * @return
	 */
    int deleteRolePermission(Integer roleId, Integer permissionId);

	/**
	 * 判断某种角色是否存在某种权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
    boolean havePermission(Integer roleId, Integer permissionId);

	/**
	 * 获取角色信息
	 * 
	 * @param record
	 * @return
	 */
    Role selectRole(String rolename);
	
	/**
	 * 获取角色权限id
	 * @param id
	 * @return
	 */
    List<Integer> selectPermissionIdByRoleId(Integer id);

}
