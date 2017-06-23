package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.RolePermission;

public interface RoleService {
	public int deleteByPrimaryKey(Integer id);

	public int insert(Role record);

	public int insertSelective(Role record);

	public Role selectByPrimaryKey(Integer id);

	public int updateByPrimaryKeySelective(Role record);

	public int updateByPrimaryKey(Role record);

	/**
	 * 分页获取所有角色
	 * 
	 * @return
	 */
	public Pager<Role> selectAllByPage(Integer currentPage, Integer pageSize);

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> selectAll();

	/**
	 * 添加角色的权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	public int insertRolePermission(Integer roleId, Integer permissionId);

	/**
	 * 获取角色对应的权限
	 * 
	 * @param id
	 * @return
	 */
	public List<Permission> selectPermissionByRoleId(Integer id);

	/**
	 * 删除角色的某个权限
	 * 
	 * @param record
	 * @return
	 */
	public int deleteRolePermission(RolePermission record);

	/**
	 * 判断某种角色是否存在某种权限
	 * 
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	public boolean havePermission(Integer roleId, Integer permissionId);

	/**
	 * 获取角色信息
	 * 
	 * @param record
	 * @return
	 */
	public Role selectRole(String rolename);

}
