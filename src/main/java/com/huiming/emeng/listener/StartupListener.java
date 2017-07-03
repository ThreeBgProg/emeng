package com.huiming.emeng.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.common.ClassUtil;
import com.huiming.emeng.mapper.PermissionMapper;
import com.huiming.emeng.model.Permission;

/**
 * 初始化完成后运行
 * 
 * @author Achan
 *
 */
public class StartupListener {

	@Autowired
	private PermissionMapper permissionMapper;

	public void initMethod() throws Exception {

		System.err.println("startupListener---------------");

		List<Permission> permissionList = permissionMapper.selectAll();
		List<Method> methods = getMethodsNameInClass("com.huiming.emeng.controller");
		for (Method m : methods) {

			Permission permission = getPermissionFromMethod(m);
			// 与数据库进行对比
			if (permissionList.contains(permission)) {
				// 权限存在数据库,但无效
				permission.setState((byte) 1);
				permissionMapper.updateByMapping(permission);
				permissionList.remove(permission);
			} else {
				// 插入新权限
				permission.setState((byte) 1);
				permissionMapper.insert(permission);
			}
		}
		// 置无效权限
		for (Permission temp : permissionList) {
			System.out.println(temp);
			if (temp.getState() != 0) {
				temp.setState((byte) 0);
				permissionMapper.updateByPrimaryKeySelective(temp);
			}
		}
	}

	/**
	 * 获取包下所有需要监控的方法的名称
	 * 
	 * @param packageName
	 *            包名
	 * @return 方法名集合
	 */
	public static List<Method> getMethodsNameInClass(String packageName) {

		List<Method> result = new ArrayList<>();
		List<Class> classes = ClassUtil.getClasssFromPackage(packageName);
		for (Class<?> c : classes) {

			if (!(c.isAnnotationPresent(Controller.class) || c.isAnnotationPresent(RestController.class))) {
				continue;
			}
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				if (isMonitorMethod(method)) {
					result.add(method);
				}
			}
		}
		return result;
	}

	/**
	 * 获取类上的requestmapping
	 * 
	 * @param packageName
	 *            类名
	 * @return url
	 */
	public static String getClassRequestMapping(Class<?> clazz) {
		if (clazz.isAnnotationPresent(RequestMapping.class)) {
			return clazz.getAnnotation(RequestMapping.class).value()[0];
		}
		return "";
	}

	/**
	 * 判断方法是否为需要监控的方法(被RequestMapping注解的方法）
	 * 
	 * @param method
	 * @return 方法是否为需要监控的方法
	 */
	public static boolean isMonitorMethod(Method method) {
		return method.isAnnotationPresent(RequestMapping.class);
	}

	/**
	 * 从方法上获取permission信息
	 * 
	 * @param m
	 * @return
	 */
	public static Permission getPermissionFromMethod(Method m) {
		Permission permission = new Permission();
		// 获取方法对饮的类上的requestmapping对应的url，拼接成最终的url
		String preUrl = getClassRequestMapping(m.getDeclaringClass());
		String sufUrl = m.getAnnotation(RequestMapping.class).value()[0];
		permission.setMapping(preUrl + sufUrl);
		// 判断是否存在MappingDescription注解，存在则取值，不存在则设置为--"未设置解释"
		if (m.isAnnotationPresent(MappingDescription.class)) {
			permission.setDescription(m.getAnnotation(MappingDescription.class).value());
		} else {
			permission.setDescription("未设置解释");
		}
		return permission;
	}

}
