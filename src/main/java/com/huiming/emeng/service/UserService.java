
package com.huiming.emeng.service;

import java.util.List;

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
	public List<User> selectAllUser();
	
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
	public List<User> getUserByRole(Integer id);

	/**
	 * 使用某个或某些字段查询
	 * @param user
	 * @return
	 */
	public List<User> selectAllSelective(User user);
	
	/**
	 * 查詢
	 * @param record
	 * @return
	 */
	public List<User> findSelective(User record);
}
