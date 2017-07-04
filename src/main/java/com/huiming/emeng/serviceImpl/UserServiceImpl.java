package com.huiming.emeng.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
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
	public User selectByPrimaryKey(User user) {
		return userMapper.selectByPrimaryKey(user.getId());
	}
	
	@Override
	public User selectSelective(User user) {
		return userMapper.selectSelective(user);
	}

	@Override
	public Pager<User> selectAllSelective(User user, Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = userMapper.countSelective(user);
		Pager<User> userPage = new Pager<>(pageSize, currentPage, totalRecord,
				userMapper.selectPagerUserSelective(user, fromIndex, pageSize));
		return userPage;
	}

	@Override
	public Pager<User> selectAllUser(Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = userMapper.selectCount();
		Pager<User> userPage = new Pager<>(pageSize, currentPage, totalRecord,
				userMapper.selectPagerUser(fromIndex, pageSize));
		return userPage;
	}

	@Override
	public Role getUserRole(Integer id) {
		UserRole userRole = userRoleMapper.selectByUserId(id);
		if (userRole != null) {
			return roleMapper.selectByPrimaryKey(userRole.getRoleId());
		} else
			return null;
	}

	@Override
	public Pager<User> getUserByRole(Integer id, Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = userRoleMapper.selectCountByRoleId(id);
		List<Integer> userIds = userRoleMapper.selectByRoleId(id, fromIndex, pageSize);
		List<User> list = new ArrayList<>();
		for (Integer userId : userIds) {
			list.add(userMapper.selectByPrimaryKey(userId));
		}
		Pager<User> userPage = new Pager<>(pageSize, currentPage, totalRecord, list);
		return userPage;
	}

	@Override
	public int insertUser(User user) {
		user.setState((byte) 1);
		return userMapper.insert(user);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	// 有问题，这个方法不知道有没有用到
	@Override
	public Pager<User> findSelective(User record, Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = userMapper.selectCount();
		Pager<User> userPage = new Pager<>(pageSize, currentPage, totalRecord,
				userMapper.selectPagerUserSelective(record, fromIndex, pageSize));
		return userPage;
	}

	public Boolean selectByJobId(String jobId){
		return userMapper.selectByJobId(jobId)==null;
	}
	/**
	 * 修改用户角色
	 * 
	 * @return
	 */
	@Override
	public int updateUserRole(Integer roleId, Integer userId) {
		UserRole userRole = new UserRole();
		userRole.setRoleId(roleId);
		userRole.setUserId(userId);
		return userRoleMapper.updateUserRole(userRole);
	}

	@Override
	public int insertUserRole(Integer roleId, Integer userId) {
		UserRole userRole = new UserRole();
		userRole.setRoleId(roleId);
		userRole.setUserId(userId);
		return userRoleMapper.insert(userRole);
	}
}
