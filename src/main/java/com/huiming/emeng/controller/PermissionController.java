package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.service.PermissionService;

@Controller
public class PermissionController {

	private String SUCCESS = "操作成功";
	private String FAIL = "操作失败";

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/updatePermission")
	@MappingDescription("修改权限信息")
	@ResponseBody
	public String updatePermission(Permission permission) {
		// 只允许修改权限功能描述
		if (permissionService.updateByPrimaryKey(permission) != 0) {
			return SUCCESS;
		} else
			return FAIL;
	}

	@RequestMapping("/getAllPermission")
	@MappingDescription("超级管理员获取所有权限信息")
	@ResponseBody
	public ModelMap getAllPermission(Permission permission, ModelMap modelMap) {
		modelMap.put("permissions", permissionService.selectAllEffective());
		return modelMap;
	}
}
