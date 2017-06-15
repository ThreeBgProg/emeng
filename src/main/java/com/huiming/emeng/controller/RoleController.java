package com.huiming.emeng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.service.PermissionService;
import com.huiming.emeng.service.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/addRole")
	@MappingDescription("增加角色")
	public String addRole(Role role,List<Integer> permissionList) {
		roleService.insert(role);
		role = roleService.selectRole(role);
		Integer roleId = role.getId();
		for (Integer permissionId : permissionList) {
			roleService.insertRolePermission(roleId, permissionId);
		}
		return "login";
	}

	@RequestMapping("/getAllRole")
	@MappingDescription("超级管理员获取所有角色")
	public String getAllRole(ModelMap modelMap) {
		List<Role> roles = new ArrayList<>();
		roles = roleService.selectAll();
		modelMap.put("roles", roles);
		return "login";
	}

	@RequestMapping("/updateRole")
	@MappingDescription("修改角色")
	public String updateRole(Role role) {
		roleService.updateByPrimaryKey(role);
		return "login";
	}

	@RequestMapping("/updateRolePermission")
	@MappingDescription("修改角色权限")
	public String updateRolePermission(Role role, ModelMap modelMap, List<Integer> permissionList) {
		List<Permission> list = roleService.selectPermissionByRoleId(role.getId());
		for (Permission permission : list) {
			//数据库中该角色已经存在该权限
			if(permissionList.contains(permission.getId())){
				list.remove(permission);
				permissionList.remove(permission.getId());
				continue;
			}else{
				//不存在则插入
				roleService.insertRolePermission(role.getId(), permission.getId());
			}
		}
		//角色被删除的权限
		for (Permission permission : list) {
			roleService.deleteByPrimaryKey(permission.getId());
		}
		return "login";
	}

	@RequestMapping("/getPermissionsByRole")
	@MappingDescription("获取角色权限")
	public String getPermissionsByRole(Role role, ModelMap modelMap) {
		modelMap.put("permissionList", permissionService.selectByRole(role.getId()));
		return "login";
	}
}
