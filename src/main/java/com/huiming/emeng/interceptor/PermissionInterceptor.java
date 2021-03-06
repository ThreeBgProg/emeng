package com.huiming.emeng.interceptor;

import com.huiming.emeng.listener.StartupListener;
import com.huiming.emeng.model.Permission;
import com.huiming.emeng.model.Role;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RoleService roleService;

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

	/*
	 * /** 判断用户是否拥有请求资源的权限，没有则返回至登录页面，或者主页，或者没有权限页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestUrl = "";
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;

			// 获取url请求对应的方法上的requestMapping注解
			RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
			// 获取类上的requestMapping里的值
			String classRequestMapping = StartupListener
					.getClassRequestMapping(handlerMethod.getMethod().getDeclaringClass());
			// 拼接请求的方法对应的url
			requestUrl = classRequestMapping + requestMapping.value()[0];
		}
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		Role role = (Role) session.getAttribute("role");
		if(request.getRequestURI().contains("indexEM")){
			System.out.println("1");
//			response.sendRedirect("/emeng/noroot.html");
			//管理员进入后台页面
			return role != null && role.getId() == Integer.parseInt(env.getRequiredProperty("role.adminId"));
		}else if(request.getRequestURI().contains("html")){
			return true;
		}
		if (user == null) {
			// 没有登录默认使用游客身份
			user = new User();
			session.setAttribute("user",user);
			List<Permission> permissions = roleService
					.selectPermissionByRoleId(Integer.parseInt(env.getRequiredProperty("role.visitorId")));
			List<String> permissionMappingList = new ArrayList<>();
			for (Permission permission : permissions) {
				permissionMappingList.add(permission.getMapping());
			}
			session.setAttribute("permissionList", permissionMappingList);
		}
		// 从session中获取用户允许访问的资源
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) session.getAttribute("permissionList");

		// 权限集合中包含请求的url
        // 没有权限
		if(null == list){
			return  false;
		}
        return list.contains(requestUrl);
	}

}
