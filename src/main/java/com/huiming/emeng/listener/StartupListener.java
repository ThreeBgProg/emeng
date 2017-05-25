package com.huiming.emeng.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		
		List<Method> methods = getMethodsNameInClass("com.huiming.emeng.controller");
		
		//考虑优化
		for(Method m : methods) {
			Permission permission = new Permission();
			//获取方法对饮的类上的requestmapping对应的url，拼接成最终的url
			String preUrl = getClassRequestMapping(m.getDeclaringClass());
			String sufUrl = m.getAnnotation(RequestMapping.class).value()[0];
			System.out.print(preUrl + sufUrl+"\t");
			permission.setMapping(preUrl + sufUrl);
			if(m.isAnnotationPresent(MappingDescription.class)) {
				//RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(m, RequestMapping.class);
				System.out.println(m.getName()+ "\t" + m.getAnnotation(MappingDescription.class).value());
				permission.setDescription(m.getAnnotation(MappingDescription.class).value());
			} else {
				System.out.println(m.getName()+"\t未设置解释");
				permission.setDescription("未设置解释");
			}
			permissionMapper.insert(permission);
		}	
	}

	
	
	/**
	 * 获取包下所有需要监控的方法的名称
	 * @param packageName 包名
	 * @return 方法名集合
	 */
	public static List<Method> getMethodsNameInClass(String packageName) {
		
		List<Method> result = new ArrayList<>();
		List<Class> classes = ClassUtil.getClasssFromPackage(packageName);
		for(Class<?> c : classes) {
			
			if(!c.isAnnotationPresent(Controller.class))	{
				continue;
			}
			Method[] methods = c.getMethods();
			for(Method method : methods) {
				if(isMonitorMethod(method)) {
					result.add(method);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取类上的requestmapping
	 * @param packageName 类名
	 * @return url
	 */
	public static String getClassRequestMapping(Class<?> clazz ) {
		if(clazz.isAnnotationPresent(RequestMapping.class))	{
			return clazz.getAnnotation(RequestMapping.class).value()[0];
		}
		
		return "";
	}
	

	/**
	 * 判断方法是否为需要监控的方法(被RequestMapping注解的方法）
	 * @param method
	 * @return 方法是否为需要监控的方法
	 */
	public static boolean isMonitorMethod(Method method) {
		return method.isAnnotationPresent(RequestMapping.class);
	}
	
}
