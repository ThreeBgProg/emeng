package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.bo.SchoolWithLocation;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.School;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.LocationService;
import com.huiming.emeng.service.SchoolService;
import com.huiming.emeng.service.UserService;

@Controller
public class SchoolController {

	private String FAIL = "操作失败";
	private String SUCCESS = "操作成功";

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private LocationService locationService;

	@RequestMapping("/getSchools")
	@MappingDescription("获取学校信息")
	@ResponseBody
	public List<SchoolWithLocation> getSchools() {
		return schoolService.selectAll();
	}

	@RequestMapping("/getSchoolPage")
	@MappingDescription("分页获取学校信息")
	@ResponseBody
	public Pager<SchoolWithLocation> getSchoolPager(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		return schoolService.selectAllByPage(currentPage, pageSize);
	}

	@RequestMapping("/insertSchool")
	@MappingDescription("添加学校信息")
	@ResponseBody
	public String insertSchool(School school) {
		if (schoolService.insert(school) != 0) {
			return SUCCESS;
		}
		return FAIL;
	}

	@RequestMapping("/updateSchool")
	@MappingDescription("修改学校信息")
	@ResponseBody
	public String updateSchool(School school) {
		if (schoolService.updateByPrimaryKey(school) != 0) {
			return SUCCESS;
		}
		return FAIL;
	}

	@RequestMapping("/deleteSchool")
	@MappingDescription("删除学校信息")
	@ResponseBody
	public String deleteSchool(School school) {
		User user = new User();
		user.setSchoolId(school.getId());
		if (userService.selectAllSelective(user, 1, 1).getTotalRecord() == 0) {
			if (schoolService.deleteByPrimaryKey(school.getId()) != 0) {
				return SUCCESS;
			}
		}
		return FAIL;
	}

	@RequestMapping("/selectSchoolByPrimaryKey")
	@MappingDescription("根据id获取学校信息")
	@ResponseBody
	public SchoolWithLocation selectBySchoolPrimaryKey(Integer id) {
		SchoolWithLocation schoolWithLocation = new SchoolWithLocation();
		School school = schoolService.selectByPrimaryKey(id);
		schoolWithLocation.setSchool(school);
		schoolWithLocation.setLocation(locationService.selectByPrimaryKey(school.getProvinceId()));
		return schoolWithLocation;
	}
	
	
	@RequestMapping("/selectSchoolsSelective")
	@MappingDescription("查询获取学校信息(不分页)")
	@ResponseBody
	public List<SchoolWithLocation> selectSchoolsSelective(School school) {
		return schoolService.selectSchoolSelective(school);
	}
	
	@RequestMapping("/selectSchoolsSelectiveByPage")
	@MappingDescription("查询获取学校信息(分页)")
	@ResponseBody
	public Pager<SchoolWithLocation> selectSchoolsSelectiveByPage(School school,@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		return schoolService.selectSchoolSelectivePage(school, currentPage, pageSize);
	}
}
