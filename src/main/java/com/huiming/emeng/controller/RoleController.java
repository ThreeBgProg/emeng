package com.huiming.emeng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.service.PermissionService;
import com.huiming.emeng.service.RoleService;
import com.huiming.emeng.service.UserService;

@Controller
public class RoleController {

	private String SUCCESS = "操作成功";
	private String FAIL = "操作失败";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/addRole")
	@MappingDescription("增加角色")
	@ResponseBody
	public String addRole(Role role, List<Integer> permissionList) {
		if (roleService.insert(role) != 0) {
			role = roleService.selectRole(role);
			Integer roleId = role.getId();
			for (Integer permissionId : permissionList) {
				roleService.insertRolePermission(roleId, permissionId);
			}
		} else {
			return FAIL;
		}
		return SUCCESS;
	}

	@ResponseBody
	@RequestMapping("/getAllRole")
	@MappingDescription("超级管理员获取所有角色")
	public Pager<Role> getAllRole(ModelMap modelMap, Integer currentPage, Integer pageSize) {
		List<Role> roles = new ArrayList<>();
		roles = roleService.selectAll();
		return new Pager<>(pageSize, currentPage, roles);
	}

	@RequestMapping("/updateRole")
	@MappingDescription("修改角色")
	@ResponseBody
	public String updateRole(Role role) {
		role.setState((byte) 1);
		if (roleService.updateByPrimaryKey(role) != 0) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	}

	@RequestMapping("/updateRolePermission")
	@MappingDescription("修改角色权限")
	@ResponseBody
	public String updateRolePermission(Role role, ModelMap modelMap, List<Integer> permissionList) {
		List<Permission> list = roleService.selectPermissionByRoleId(role.getId());
		for (Permission permission : list) {
			// 数据库中该角色已经存在该权限
			if (permissionList.contains(permission.getId())) {
				list.remove(permission);
				permissionList.remove(permission.getId());
				continue;
			} else {
				// 不存在则插入
				roleService.insertRolePermission(role.getId(), permission.getId());
			}
		}
		// 角色被删除的权限
		for (Permission permission : list) {
			roleService.deleteByPrimaryKey(permission.getId());
		}
		return SUCCESS;
	}

	@RequestMapping("/getPermissionsByRole")
	@MappingDescription("获取角色权限")
	@ResponseBody
	public List<Permission> getPermissionsByRole(Role role, ModelMap modelMap) {
		return permissionService.selectByRole(role.getId());
	}

	@RequestMapping("/deleteRole")
	@MappingDescription("删除角色")
	@ResponseBody
	public String deleteRole(Role role, ModelMap modelMap) {
		if (userService.getUserByRole(role.getId()) == null) {
			if (roleService.deleteByPrimaryKey(role.getId()) != 0) {
				return SUCCESS;
			} else
				return FAIL;
		}else{
			return "存在用户为该角色，请先对用户角色进行修改";
		}
	}
}
