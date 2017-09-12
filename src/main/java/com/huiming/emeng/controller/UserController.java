package com.huiming.emeng.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.bo.UserWithRole;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.PermissionService;
import com.huiming.emeng.service.RoleService;
import com.huiming.emeng.service.SchoolService;
import com.huiming.emeng.service.UserService;

@Controller
public class UserController {

	private String SUCCESS = "操作成功";
	private String FAIL = "操作失败";

	@Autowired
	private SchoolService schoolService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/login")
	@MappingDescription("用户登录")
	@ResponseBody
	public String login(HttpServletRequest request, User user) {
		String username = user.getUsername();
		if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
			return null;
		}
		user.setState((byte) 1);
		
		User userMessages = userService.selectSelective(user);
		if (userMessages == null) {
			user.setUsername(null);
			user.setJobId(username);
			userMessages = userService.selectSelective(user);
		}
		if (userMessages != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", userMessages);

			Role role = userService.getUserRole(userMessages.getId());
			session.setAttribute("role", role);// 可以不加

			// 添加用户的权限mapping
			List<Permission> permissions = permissionService.selectByRole(role.getId());
			List<String> permissionMappingList = new ArrayList<>();
			for (Permission permission : permissions) {
				permissionMappingList.add(permission.getMapping());
			}
			session.setAttribute("permissionList", permissionMappingList);

		} else {
			return "用户名或密码错误";
		}
		return "index";
	}

	@RequestMapping(value = "/getUserMessage")
	@MappingDescription("用户登录")
	@ResponseBody
	public User getUserMessage(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

	@RequestMapping("/logout")
	@MappingDescription("用户退出登录")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("permissionList");
		session.removeAttribute("role");
		return "login";
	}

	@RequestMapping("/addUser")
	@MappingDescription("添加用户/用户注册")
	@ResponseBody
	public String addUser(User user, Integer roleId) {
		// 管理员添加用户直接确定用户角色，注册时未定义角色，由管理员审核并修改角色
		User temp = new User();
		temp.setUsername(user.getUsername());
		if (userService.selectSelective(temp) == null && userService.selectByJobId(user.getJobId())) {
			if (roleId != null) {
				user.setState((byte) 1);
				userService.insertSelective(user);
				user = userService.selectSelective(user);
				userService.insertUserRole(roleId, user.getId());
			} else {
				user.setState((byte) 0);
				userService.insertSelective(user);
			}
		} else {
			return FAIL + " 用户名或工号已存在";
		}
		return SUCCESS;
	}

	@RequestMapping("/selectByJobId")
	@ResponseBody
	@MappingDescription("工号校验")
	public String selectByJobId(String jobId) {
		if (!userService.selectByJobId(jobId)) {
			return "工号已存在";
		} else {
			return "";
		}
	}

	@RequestMapping("/passUserRegister")
	@MappingDescription("用户审核通过")
	@ResponseBody
	public String passUserRegister(Integer id, Integer roleId) {
		// 管理员审核注册的用户并赋予角色
		User temp = new User();
		temp.setId(id);
		temp.setState((byte) 1);
		if (userService.updateUser(temp) != 0) {
			if (roleId != null) {
				userService.insertUserRole(roleId, id);
			}
		} else {
			return FAIL + " 用户已存在";
		}
		return SUCCESS;
	}

	@RequestMapping("/addUserPage")
	@MappingDescription("跳转到添加用户页面")
	@ResponseBody
	public Object addUserPage(ModelMap modelMap) {
		modelMap.put("schoolList", schoolService.selectAll());
		modelMap.put("roleList", roleService.selectAll());
		return JSON.toJSON(modelMap);
	}

	@RequestMapping("/deleteUser")
	@MappingDescription("删除用户")
	@ResponseBody
	public String deleteUser(User user) {
		// 改为---->修改用户的state为0
		user.setState((byte) 2);
		if (userService.updateUser(user) != 0) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	}

	@RequestMapping("/ignoreUserRegister")
	@MappingDescription("忽略用户注册")
	@ResponseBody
	public int ignoreUserRegister(Integer id) {
		return userService.deleteReal(id);
	}

	@RequestMapping("/getUserByRole")
	@MappingDescription("分页获取某种角色的所有用户")
	@ResponseBody
	public Pager<User> getUserByRole(Role role, ModelMap modelMap,
			@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		return userService.getUserByRole(role.getId(), currentPage, pageSize);
	}

	@RequestMapping("/findUser")
	@MappingDescription("根据id查询用户")
	@ResponseBody
	public Object findUser(User user, ModelMap modelMap) {
		UserWithRole temp = new UserWithRole();
		temp.setUser(userService.selectByPrimaryKey(user));
		temp.setRole(userService.getUserRole(user.getId()));
		temp.setSchool(schoolService.selectByPrimaryKey(user.getSchoolId()));
		return JSON.toJSON(temp);
	}

	@RequestMapping("/getAllUnpassUser")
	@MappingDescription("分页获取未审核的用户")
	@ResponseBody
	public Object getAllUnpassUser(ModelMap modelMap,
			@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		User user = new User();
		user.setState((byte) 0);
		modelMap.put("userList", getUserWithRole(userService.selectAllSelective(user, currentPage, pageSize)));
		return JSON.toJSON(modelMap);
	}

	@RequestMapping("/getAllUser")
	@MappingDescription("分页获取用户信息以及角色")
	@ResponseBody
	public Object getAllUser(ModelMap modelMap,
			@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		modelMap.put("userList", getUserWithRole(userService.selectAllUser(currentPage, pageSize)));
		return JSON.toJSON(modelMap);
	}

	@RequestMapping("/updateByPrimaryKey")
	@MappingDescription("更改用户以及角色")
	@ResponseBody
	public String updateByPrimaryKey(User user, Integer roleId, ModelMap modelMap) {
		User temp = new User();
		temp.setUsername(user.getUsername());
		temp = userService.selectSelective(temp);
		if (temp == null || (int) temp.getId() == (int) user.getId()) {
			if (userService.updateUser(user) != 0) {
				Role roletemp = userService.getUserRole(user.getId());
				if (roleId != null && !roleId.equals(roletemp.getId())) {
					userService.updateUserRole(roleId, user.getId());
				}
				return SUCCESS;
			}
		}
		return FAIL;
	}

	public Pager<UserWithRole> getUserWithRole(Pager<User> users) {
		List<UserWithRole> userList = new ArrayList<>();
		for (User user : users.getDataList()) {
			UserWithRole temp = new UserWithRole();
			temp.setUser(user);
			temp.setRole(userService.getUserRole(user.getId()));
			temp.setSchool(schoolService.selectByPrimaryKey(user.getSchoolId()));
			userList.add(temp);
		}
		return new Pager<UserWithRole>(users.getPageSize(), users.getCurrentPage(), users.getTotalRecord(), userList);
	}
}
