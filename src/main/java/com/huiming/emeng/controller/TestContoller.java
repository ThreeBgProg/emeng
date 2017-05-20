package com.huiming.emeng.controller;

import com.huiming.emeng.model.Navigation;
import com.huiming.emeng.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.common.CustomException;
import com.huiming.emeng.common.CustomException.UnauthorizedError;
import com.huiming.emeng.mapper.LessonMapper;

import java.util.List;

/**
 * 测试
 * 
 * @author Jack
 * @date 2017年5月15日
 */
@Controller
public class TestContoller {

	@Autowired
	private LessonMapper mapper;

	@Autowired
	private NavigationService navigationService;
	
	/**
	 * 测试数据库连接情况
	 * @return
	 */
	@RequestMapping("/test")
	public String test() {

		List<Navigation> navigationList = navigationService.selectAllNavigation();
		for (Navigation navigation : navigationList){
			System.out.println(navigation);
		}
		System.out.println(mapper.selectByPrimaryKey(1).getName());
		return "error";
	}
	
	
	/**
	 * 测试自定义异常
	 */
	@RequestMapping("testException")
	public String testException() {
		throw CustomException.genException(UnauthorizedError.class, "错误消息");
	}
}
