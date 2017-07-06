package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Permission;

public interface PermissionMapper {
	/**
	 * 刪除指定id的permission
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加权限
     * @param record
     * @return
     */
    int insert(Permission record);

    /**
     * 增加权限
     * @param record
     * @return
     */
    int insertSelective(Permission record);

    /**
     * 更新权限
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * 更新权限
     * @param record
     * @return
     */
    int updateByPrimaryKey(Permission record);
    
    int updateByMapping(Permission record);
    
    Permission selectSelective(Permission record);
    
    Permission selectByPrimaryKey(Integer id);
    
    int deleteSelective(Permission record);
    
    /**
     * 获取所有有效的权限
     * @return
     */
    List<Permission> selectAllEffective();
    
    /**
     * 分页获取所有有效的权限
     * @return
     */
    List<Permission> selectAllEffectiveByPage(@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
    
    /**
     * 获取所有的权限，包括无效的     
     * @return
     */
    List<Permission> selectAll();
    
    /**
     * 计数
     * @return
     */
    int selectCount();
    
}