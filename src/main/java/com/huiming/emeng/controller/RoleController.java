package com.huiming.emeng.controller;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.service.PermissionService;
import com.huiming.emeng.service.RoleService;
import com.huiming.emeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {

	private String SUCCESS = "操作成功";
	private String FAIL = "操作失败";

	/**
	 * Environment变量，用于加载application.properties
	 */
	@Autowired
	private Environment env;

	/**
	 * 实现EnvironmentAware接口的方法确保environment注入成功
	 */
	public void setEnvironment(Environment env) {
		this.env = env;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/addRole")
	@MappingDescription("增加角色")
	@ResponseBody
	public String addRole(Role role, @RequestParam("permissionList[]") List<Integer> permissionList) {
		System.out.println(permissionList);
		if (roleService.selectRole(role.getRolename()) == null) {
			if (roleService.insert(role) != 0) {
				role = roleService.selectRole(role.getRolename());
				Integer roleId = role.getId();
				for (Integer permissionId : permissionList) {
					roleService.insertRolePermission(roleId, permissionId);
				}
				return SUCCESS;
			}
		}
		return FAIL;

	}

	@ResponseBody
	@RequestMapping("/getAllRole")
	@MappingDescription("获取所有角色")
	public List<Role> getAllRole(ModelMap modelMap) {
		return roleService.selectAll();
	}

	@ResponseBody
	@RequestMapping("/getAllRoleByPage")
	@MappingDescription("分页获取所有角色")
	public Pager<Role> getAllRoleByPage(ModelMap modelMap,
			@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		return roleService.selectAllByPage(currentPage, pageSize);
	}

	@RequestMapping("/updateRole")
	@MappingDescription("修改角色")
	@ResponseBody
	public String updateRole(Role role) {
		role.setState((byte) 1);
		System.out.println(roleService.updateByPrimaryKey(role));
		// if (roleService.updateByPrimaryKey(role) != 0) {
		return SUCCESS;
		// } else {
		// return FAIL;
		// }
	}

	@RequestMapping("/updateRolePermission")
	@MappingDescription("修改角色权限")
	@ResponseBody
	public String updateRolePermission(Role role, ModelMap modelMap,
			@RequestParam("permissionList[]") List<Integer> permissionList) {
		if (role.getRolename() != null) {
			roleService.updateByPrimaryKeySelective(role);
		}
		// 角色权限分配之后取消注释
		if (env.getRequiredProperty("role.unChangeRoleId").contains(role.getId().toString())) {
			return "固定角色，无法修改";
		}
		Integer roleId = role.getId();
		//数据库中角色已经拥有的权限
		List<Integer> list = roleService.selectPermissionIdByRoleId(roleId);
		
		//添加新权限权限
		List<Integer> tempList = new ArrayList<>(permissionList);
		tempList.removeAll(list);
		for (Integer permissionId : tempList) {
			roleService.insertRolePermission(roleId, permissionId);
		}
		tempList = null;
		//移除权限
		for (Integer permissionId : list) {
			if(permissionList.contains(permissionId)){
				continue;
			}
			roleService.deleteRolePermission(roleId, permissionId);
		}
		return SUCCESS;

	}

	@RequestMapping("/getPermissionsByRole")
	@MappingDescription("获取角色权限")
	@ResponseBody
	public Map<String, List<Permission>> getPermissionsByRole(Role role, ModelMap modelMap) {
		List<Permission> havedPermissionList = permissionService.selectByRole(role.getId());
		List<Permission> unHavedPermissionList = permissionService.selectAll();
		unHavedPermissionList.removeAll(havedPermissionList);
		Map<String, List<Permission>> list = new HashMap<String, List<Permission>>();
		list.put("havedPermissionList", havedPermissionList);
		list.put("unHavedPermissionList", unHavedPermissionList);
		return list;
	}

	@RequestMapping("/deleteRole")
	@MappingDescription("删除角色")
	@ResponseBody
	public String deleteRole(Role role, ModelMap modelMap) {
		if (env.getRequiredProperty("role.unChangeRoleId").contains(role.getId().toString())) {
			return "固定角色，无法修改";
		}
		if (userService.getUserByRole(role.getId(), 1, 1).getTotalRecord() == 0) {
			if (roleService.deleteByPrimaryKey(role.getId()) != 0) {
				return SUCCESS;
			} else
				return FAIL;
		} else {
			return "存在用户为该角色，请先对用户角色进行修改";
		}
	}
}
