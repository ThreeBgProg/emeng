package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.mapper.LessonMapper;

@Controller
public class TestContoller {

	@Autowired
	private LessonMapper mapper;
	
	@RequestMapping("test")
	public String test() {
		
		System.out.println(mapper.selectByPrimaryKey(1).getName());
		return "error";
	}
	
}
