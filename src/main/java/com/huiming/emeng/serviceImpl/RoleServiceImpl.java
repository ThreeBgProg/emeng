package com.huiming.emeng.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PermissionMapper;
import com.huiming.emeng.mapper.RoleMapper;
import com.huiming.emeng.mapper.RolePermissionMapper;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.RolePermission;
import com.huiming.emeng.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		rolePermissionMapper.deleteByRoleId(id);
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return roleMapper.insert(record);
	}

	@Override
	public int insertSelective(Role record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public Role selectByPrimaryKey(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public Pager<Role> selectAllByPage(Integer currentPage, Integer pageSize) {
		Role role = new Role();
		role.setState((byte) 1);
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = roleMapper.selectCount();
		Pager<Role> rolePage = new Pager<>(pageSize, currentPage, totalRecord,
				roleMapper.selectByRolename(role.getRolename(), fromIndex, pageSize));
		return rolePage;
	}
	
	@Override
	public List<Role> selectAll() {
		return roleMapper.selectAll();
	}

	@Override
	public int insertRolePermission(Integer roleId, Integer permissionId) {
		RolePermission rp = new RolePermission();
		rp.setPermissionId(permissionId);
		rp.setRoleId(roleId);
		return rolePermissionMapper.insertSelective(rp);
	}

	@Override
	public List<Permission> selectPermissionByRoleId(Integer id) {
		List<Integer> permissionIds = rolePermissionMapper.selectAllByRoleId(id);
		List<Permission> list = new ArrayList<Permission>();
		for (Integer permissionId : permissionIds) {
			list.add(permissionMapper.selectByPrimaryKey(permissionId));
		}
		return list;
	}

	@Override
	public int deleteRolePermission(RolePermission record) {
		return rolePermissionMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public boolean havePermission(Integer roleId, Integer permissionId) {
		return rolePermissionMapper.selectSelective(new RolePermission(roleId, permissionId)) == null;
	}

	@Override
	public Role selectRole(String rolename) {
		return roleMapper.selectRole(rolename);
	}

}
