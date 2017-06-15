package com.huiming.emeng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.PermissionService;
import com.huiming.emeng.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@MappingDescription("用户登录")
	public String login(HttpServletRequest request, User user) {
		System.out.println("login方法");
		user = userService.selectSelective(user);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			Role role = userService.getUserRole(user.getId());
			session.setAttribute("role", role);// 可以不加

			List<Permission> permissions = permissionService.selectByRole(role.getId());
			session.setAttribute("permissionList", permissions);

		} else {
			return "login";
		}
		return "";
	}

	@RequestMapping("/logout")
	@MappingDescription("用户退出登录")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("permissionList");
		session.removeAttribute("role");
		return "";
	}

	@RequestMapping("/addUser")
	@MappingDescription("添加用户/用户注册")
	public String addUser(User user) {
		System.out.println("--------------addUser-----------------");
		if (userService.selectSelective(user) == null) {
			userService.insertUser(user);
		} else {

		}
		return "用户已存在";
	}

	@RequestMapping("/deleteUser")
	@MappingDescription("删除用户")
	public String deleteUser(User user) {
		// 改为---->修改用户的state为0
		user.setState((byte) 0);
		userService.updateUser(user);
		return "";
	}

	@RequestMapping("/getUserByRole")
	@MappingDescription("获取某种角色的所有用户")
	public String getUserByRole(Role role, ModelMap modelMap) {
		// 从user_role表获取关于相关role的用户id，在获取用户
		modelMap.put("users", userService.getUserByRole(role.getId()));
		return "";
	}

	@RequestMapping("/findUser")
	@MappingDescription("根据信息查询用户")
	public String findUser(User user, ModelMap modelMap) {
		modelMap.put("users", userService.findSelective(user));
		return "";
	}

}
