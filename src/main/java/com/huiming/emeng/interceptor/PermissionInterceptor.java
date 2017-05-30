package com.huiming.emeng.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huiming.emeng.model.User;

@Service
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 判断用户是否拥有请求资源的权限，没有则返回至登录页面，或者主页，或者没有权限页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("PermissionInterceptor---->preHandle");
		System.out.println(request.getRequestURI().substring(6));
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("login.jsp");
			return false;
		} else {

			// 从session中获取用户允许访问的资源
			List<String> list = (List<String>) session.getAttribute("permissions");

			String url = request.getRequestURI();

			// 权限集合中包含请求的url
			if (list.contains(url)) {
				return true;
			} else {
				// 没有权限
				response.sendRedirect("/nopermission");
			}
		}
		return super.preHandle(request, response, handler);
	}

}
