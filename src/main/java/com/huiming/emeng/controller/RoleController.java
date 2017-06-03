package com.huiming.emeng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.annotation.MappingDescription;
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
	public String addRole(Role role){
		roleService.insert(role);
		return "login";
	}
	
	@RequestMapping("/getAllRole")
	@MappingDescription("超级管理员获取所有角色")
	public String getAllRole(ModelMap modelMap){
		List<Role> roles = new ArrayList<>();
		roles = roleService.selectAll();
		modelMap.put("roles", roles);
		return "login";
	}
	
	@RequestMapping("/updateRole")
	@MappingDescription("修改角色")
	public String updateRole(Role role){
		roleService.updateByPrimaryKey(role);
		return "login";
	}
	
	@RequestMapping("/updateRolePermission")
	@MappingDescription("修改角色权限")
	public String updateRolePermission(Role role,ModelMap modelMap){
		
		return "login";
	}
	
	@RequestMapping("/getPermissionsByRole")
	@MappingDescription("获取角色权限")
	public String getPermissionsByRole(Role role,ModelMap modelMap){
		modelMap.put("permissions", permissionService.selectByRole(role.getId()));
		return "login";
	}
}
