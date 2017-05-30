package com.huiming.emeng.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.ApplyMapper;
import com.huiming.emeng.mapper.RoleMapper;
import com.huiming.emeng.mapper.UserMapper;
import com.huiming.emeng.mapper.UserRoleMapper;
import com.huiming.emeng.model.Apply;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;
import com.huiming.emeng.model.UserRole;
import com.huiming.emeng.service.UserService;

/**
 * 用户
 * 
 * @author zhiwei
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private ApplyMapper applyMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapping;

	@Autowired
	private RoleMapper roleMapper;

	/*
	 * 后台生成报名表
	 * 
	 * @see
	 * com.huiming.emeng.service.UserService#insert(com.huiming.emeng.model.
	 * Apply)
	 */
	public void insert(Apply apply) {
		applyMapper.insert(apply);
	}

	/*
	 * 后台查询全部报名表的信息 （管理员）
	 * 
	 * @see com.huiming.emeng.service.UserService#selectApplys()
	 */
	public List<Apply> selectAllApply() {
		return applyMapper.selectAllApply();
	}

	/*
	 * 根据主键删除
	 * 
	 * @see com.huiming.emeng.service.UserService#deleteByPrimaryKey(java.lang.
	 * Integer)
	 */
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return applyMapper.deleteByPrimaryKey(id);
	}

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
		UserRole userRole = userRoleMapping.selectByPrimaryKey(id);
		return roleMapper.selectByPrimaryKey(userRole.getRoleId());

	}

	@Override
	public List<User> getUserByRole(Integer id) {
		List<Integer> userIds = userRoleMapping.selectByRoleId(id);
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
}
