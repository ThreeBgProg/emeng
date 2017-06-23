
package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;

public interface UserService {

	
	/**
	 * 用户信息更新
	 * @return
	 */
	public int updateUser(User user);
	
	/**
	 * 查询是否存在有效用户
	 * @param user
	 * @return 
	 */
	public User selectSelective(User user);
	
	/**
	 * 获取所有有效用户
	 * @return
	 */
	public Pager<User> selectAllUser(Integer fromIndex, Integer pageSize);
	
	/**
	 * 获取用户角色
	 * @return
	 */
	public Role getUserRole(Integer id);
	
	/**
	 * 添加用户
	 * @return
	 */
	public int insertUser(User user);
	
	
	/**
	 * 获取某种角色用户
	 * @param id
	 * @return
	 */
	public Pager<User> getUserByRole(Integer id,Integer currentPage, Integer pageSize);

	/**
	 * 使用某个或某些字段查询
	 * @param user
	 * @return
	 */
	public Pager<User> selectAllSelective(User user,Integer currentPage, Integer pageSize);
	
	/**
	 * 查詢
	 * @param record
	 * @return
	 */
	public Pager<User> findSelective(User record,Integer currentPage, Integer pageSize);
	
	/**
	 * 修改用户角色
	 * @return
	 */
	public int updateUserRole(Integer roleId,Integer userId);

	int insertUserRole(Integer roleId, Integer userId);

}
