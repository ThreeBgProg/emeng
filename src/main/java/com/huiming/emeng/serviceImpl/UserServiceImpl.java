package com.huiming.emeng.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.RoleMapper;
import com.huiming.emeng.mapper.UserMapper;
import com.huiming.emeng.mapper.UserRoleMapper;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;
import com.huiming.emeng.model.UserRole;
import com.huiming.emeng.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public User selectSelective(User user) {
		user.setState((byte) 1);
		return userMapper.selectSelective(user);
	}

	@Override
	public List<User> selectAllSelective(User user) {
		return userMapper.selectAllSelective(user);
	}

	@Override
	public List<User> selectAllUser() {
		return userMapper.selectAll();
	}

	@Override
	public Role getUserRole(Integer id) {
		UserRole userRole = userRoleMapper.selectByUserId(id);
		return roleMapper.selectByPrimaryKey(userRole.getRoleId());
	}

	@Override
	public List<User> getUserByRole(Integer id) {
		List<Integer> userIds = userRoleMapper.selectByRoleId(id);
		List<User> list = new ArrayList<>();
		for (Integer userId : userIds) {
			list.add(userMapper.selectByPrimaryKey(userId));
		}
		return list;
	}

	@Override
	public int insertUser(User user) {
		return userMapper.insert(user);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	public List<User> findSelective(User record){
		return userMapper.findSelective(record);
	}
	
	/**
	 * 修改用户角色
	 * @return
	 */
	public int updateUserRole(Integer roleId,Integer userId){
		UserRole userRole = new UserRole();
		userRole.setRoleId(roleId);
		userRole.setUserId(userId);
		return userRoleMapper.updateUserRole(userRole);
	}
}
