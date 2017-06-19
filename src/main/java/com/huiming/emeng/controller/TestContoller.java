package com.huiming.emeng.controller;

import com.huiming.emeng.model.Navigation;
import com.huiming.emeng.service.NavigationService;
import com.mysql.fabric.xmlrpc.base.Array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.common.CustomException;
import com.huiming.emeng.common.CustomException.UnauthorizedError;
import com.huiming.emeng.mapper.LessonMapper;

import java.util.ArrayList;
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
	
	public String test(ModelMap map) {

		//List<Navigation> navigationList = navigationService.selectAllNavigation();
		List<Navigation> navigationList = new ArrayList<Navigation>(); 
//		for (Navigation navigation : navigationList){
//			System.out.println(navigation);
//		}
		for(int i=0;i<5;i++){
			navigationList.add(new Navigation());
		}
		map.put("navigationList", navigationList);
//		return "success";
		return "WEB-INF/thinkEM/indexEM.html";
	}
	
	
	/**
	 * 测试自定义异常
	 */
	@RequestMapping("testException")
	public String testException() {
		throw CustomException.genException(UnauthorizedError.class, "错误消息");
	}
	
	@RequestMapping("/test1")
	@ResponseBody
	public String test1() {
		return "fileupload";
	}
	
}
