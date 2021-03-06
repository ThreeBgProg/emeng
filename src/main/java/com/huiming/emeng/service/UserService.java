
package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;

public interface UserService {

	/**
	 * 用户真删除
	 * @return
	 */
    int deleteReal(Integer id);
	
	/**
	 * 用户信息更新
	 * @return
	 */
    int updateUser(User user);
	
	/**
	 * 查询是否存在用户
	 * @param user
	 * @return 
	 */
    User selectSelective(User user);
	
	/**
	 * 分页获取所有有效用户
	 * @return
	 */
    Pager<User> selectAllUser(Integer fromIndex, Integer pageSize);
	
	/**
	 * 获取用户角色
	 * @return
	 */
    Role getUserRole(Integer id);
	
	/**
	 * 添加用户
	 * @return
	 */
    int insertUser(User user);
    
    /**
	 * 添加用户
	 * @return
	 */
    int insertSelective(User user);
	
	
	/**
	 * 获取某种角色用户
	 * @param id
	 * @return
	 */
    Pager<User> getUserByRole(Integer id, Integer currentPage, Integer pageSize);

	/**
	 * 使用某个或某些字段查询
	 * @param user
	 * @return
	 */
    Pager<User> selectAllSelective(User user, Integer currentPage, Integer pageSize);
	
	/**
	 * 查詢
	 * @param record
	 * @return
	 */
    Pager<User> findSelective(User record, Integer currentPage, Integer pageSize);
	
	/**
	 * 修改用户角色
	 * @return
	 */
    int updateUserRole(Integer roleId, Integer userId);

	/**
	 * 添加用户角色
	 * @param roleId
	 * @param userId
	 * @return
	 */
	int insertUserRole(Integer roleId, Integer userId);
	
	/**
	 * 根据id查找用户
	 * @param user
	 * @return
	 */
    User selectByPrimaryKey(User user);
	
	
	/**
	 * 判断是否存在工号用户
	 * 存在-false,不存在-true
	 * @param jobId
	 * @return
	 */
    Boolean selectByJobId(String jobId);
	

}
