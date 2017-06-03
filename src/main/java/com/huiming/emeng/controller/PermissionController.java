package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.service.PermissionService;

@Controller
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/updatePermission")
	@MappingDescription("修改权限信息")
	public String updatePermission(Permission permission){
		//只允许修改权限描述
		permissionService.updateByPrimaryKey(permission);
		return "";
	}
	
	@RequestMapping("/getAllPermission")
	@MappingDescription("超级管理员获取所有权限信息")
	public String getAllPermission(Permission permission,ModelMap modelMap){
		modelMap.put("permissions",permissionService.selectAll());
		return "";
	}
}
