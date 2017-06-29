package com.huiming.emeng.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@MappingDescription("用户登录")
	@ResponseBody
	public String login(HttpServletRequest request, User user) {
		System.out.println("/login");
		user = userService.selectSelective(user);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			Role role = userService.getUserRole(user.getId());
			session.setAttribute("role", role);// 可以不加

			List<Permission> permissions = permissionService.selectByRole(role.getId());
			session.setAttribute("permissionList", permissions);

		} else {
			return "用户名或密码错误";
		}
		return "index";
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
		User temp = new User();
		temp.setUsername(user.getUsername());
		if (userService.selectSelective(temp) == null && userService.insertUser(user) != 0) {
			temp = userService.selectSelective(user);
			userService.insertUserRole(roleId, temp.getId());
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
		System.out.println("/deleteUser");
		// 改为---->修改用户的state为0
		user.setState((byte) 0);
		if (userService.updateUser(user) != 0) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	}

	@RequestMapping("/getUserByRole")
	@MappingDescription("分页获取某种角色的所有用户")
	@ResponseBody
	public Pager<User> getUserByRole(Role role, ModelMap modelMap, Integer currentPage, Integer pageSize) {
		return userService.getUserByRole(role.getId(), currentPage, pageSize);
	}

	@RequestMapping("/findUser")
	@MappingDescription("根据id查询用户")
	@ResponseBody
	public Object findUser(User user, ModelMap modelMap) {
		System.out.println(user.getId());
		UserWithRole temp = new UserWithRole();
		temp.setUser(userService.selectByPrimaryKey(user));
		temp.setRole(userService.getUserRole(user.getId()));
		temp.setSchool(schoolService.selectByPrimaryKey(user.getSchoolId()));
		return JSON.toJSON(temp);
	}

	@RequestMapping("/getAllUser")
	@MappingDescription("分页获取用户信息以及角色")
	@ResponseBody
	public Object getAllUser(ModelMap modelMap, Integer currentPage, Integer pageSize) {
		modelMap.put("userList", getUserWithRole(userService.selectAllUser(currentPage, pageSize)));
		return JSON.toJSON(modelMap);
	}

	@RequestMapping("/updateByPrimaryKey")
	@MappingDescription("更改用户以及角色")
	@ResponseBody
	public String updateByPrimaryKey(User user, Integer roleId, ModelMap modelMap) {
		if (userService.updateUser(user) != 0) {
			Role temp = userService.getUserRole(user.getId());
			if (!roleId.equals(temp.getId())) {
				userService.updateUserRole(roleId, user.getId());
			}
			return SUCCESS;
		} else {
			return FAIL;
		}
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
