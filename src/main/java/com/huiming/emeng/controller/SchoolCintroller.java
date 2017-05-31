package com.huiming.emeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.School;

@Controller
public class SchoolCintroller {

	@RequestMapping("/getSchools")
	@MappingDescription("获取学校信息")
	public String getSchools() {
		return "";
	}
	
	
	@RequestMapping("/insertSchool")
	@MappingDescription("添加学校信息")
	public String insertSchool(School school) {
		return "";
	}
	
	@RequestMapping("/updateSchool")
	@MappingDescription("修改学校信息")
	public String updateSchool(School school) {
		return "";
	}
	
	@RequestMapping("/deleteSchool")
	@MappingDescription("删除学校信息")
	public String deleteSchool(School school) {
		return "";
	}
	
}
