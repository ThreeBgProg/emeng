package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.School;
import com.huiming.emeng.service.SchoolService;

@Controller
public class SchoolController {

	private String FAIL = "操作失败";
	private String SUCCESS = "操作成功";

	@Autowired
	private SchoolService schoolService;

	@RequestMapping("/getSchools")
	@MappingDescription("获取学校信息")
	@ResponseBody
	public List<School> getSchools() {
		System.out.println("获取学校信息");
		return schoolService.selectAll();
	}

	@RequestMapping("/getSchool")
	@MappingDescription("获取学校信息")
	@ResponseBody
	public Pager<School> getSchoolPager(Integer currentPage, Integer pageSize) {
		List<School> schoolList = schoolService.selectAll();
		return new Pager<>(pageSize, currentPage, schoolList);
	}

	@RequestMapping("/insertSchool")
	@MappingDescription("添加学校信息")
	@ResponseBody
	public String insertSchool(School school) {
		if (schoolService.insert(school) >= 1) {
			return SUCCESS;
		}
		return FAIL;
	}

	@RequestMapping("/updateSchool")
	@MappingDescription("修改学校信息")
	@ResponseBody
	public String updateSchool(School school) {
		if (schoolService.updateByPrimaryKey(school) >= 1) {
			return SUCCESS;
		}
		return FAIL;
	}

	@RequestMapping("/deleteSchool")
	@MappingDescription("删除学校信息")
	@ResponseBody
	public String deleteSchool(School school) {
		if (schoolService.deleteByPrimaryKey(school.getId()) >= 1) {
			return SUCCESS;
		}
		return FAIL;
	}

}
