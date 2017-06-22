package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
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

	@RequestMapping("/getAllPermissionPage")
	@MappingDescription("超级管理员获取所有权限信息分页")
	@ResponseBody
	public Pager<Permission> getAllPermissionPage(Permission permission, ModelMap modelMap,Integer currentPage, Integer pageSize) {
		return new Pager<>(pageSize, currentPage, permissionService.selectAllEffective());
	}
	
	@RequestMapping("/getAllPermission")
	@MappingDescription("超级管理员获取所有权限信息")
	@ResponseBody
	public List<Permission> getAllPermission(Permission permission, ModelMap modelMap) {
		return permissionService.selectAllEffective();
	}
}
