package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.model.Permission;

public interface PermissionService {
	public int deleteByPrimaryKey(Integer id);

	public int insert(Permission record);

	public int insertSelective(Permission record);

	/**
	 * 获取角色对应的有效权限
	 * @return
	 */
	public List<Permission> selectByRole(Integer id);

	public int updateByPrimaryKeySelective(Permission record);

	public int updateByPrimaryKey(Permission record);

	public Permission selectSelective(Permission record);

	/**
	 * 获取全部有效权限
	 * @return
	 */
	public List<Permission> selectAllEffective();
	
	/**
     * 获取所有的权限，包括无效的     
     * @return
     */
    public List<Permission> selectAll();
}
