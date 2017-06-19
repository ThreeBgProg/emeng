package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.School;
import com.huiming.emeng.service.SchoolService;

@Controller
public class SchoolController {
	
	private String fail = "操作失败";
	private String success = "操作成功";
	
	@Autowired
	private SchoolService schoolService;

	@RequestMapping("/getSchools")
	@MappingDescription("获取学校信息")
	public List<School> getSchools() {
		return schoolService.selectAll();
	}
	
	@RequestMapping("/getSchool")
	@MappingDescription("获取学校信息")
	public Pager<School> getSchoolPager(Integer currentPage, Integer pageSize) {
		List<School> schoolList = schoolService.selectAll();
		return new Pager<>(pageSize, currentPage, schoolList);
	}
	
	@RequestMapping("/insertSchool")
	@MappingDescription("添加学校信息")
	public int insertSchool(School school) {
		return schoolService.insert(school);
	}
	
	@RequestMapping("/updateSchool")
	@MappingDescription("修改学校信息")
	public int updateSchool(School school) {
		return schoolService.updateByPrimaryKey(school);
	}
	
	@RequestMapping("/deleteSchool")
	@MappingDescription("删除学校信息")
	public String deleteSchool(School school) {
		return "";
	}
	
}
