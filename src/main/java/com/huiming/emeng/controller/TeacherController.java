package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Teacher;
import com.huiming.emeng.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@RequestMapping("/getAllTeacher")
	@MappingDescription("获取名师信息")
	@ResponseBody
	public Pager<Teacher> getAllTeacher(Integer currentPage, Integer pageSize) {
		List<Teacher> teachers = teacherService.selectAllTeacher();
		return new Pager<Teacher>(pageSize, currentPage, teachers);
	}

	@RequestMapping("/addTeacher")
	@MappingDescription
	@ResponseBody
	public int addTeacher(Teacher teacher) {
		return teacherService.insertTeacher(teacher);
	}

	@RequestMapping("/updateTeacher")
	@MappingDescription
	@ResponseBody
	public int updateTeacher(Teacher teacher) {
		return teacherService.updateTeacher(teacher);
	}

	@RequestMapping("/deleteTeacher")
	@MappingDescription
	@ResponseBody
	public int deleteTeacher(Integer id) {
		return teacherService.deleteByPrimaryKey(id);
	}

	@RequestMapping("/selectAllSelective")
	@MappingDescription("查找名师信息")
	@ResponseBody
	public Pager<Teacher> selectAllSelective(Integer currentPage, Integer pageSize, Teacher teacher) {
		List<Teacher> teachers = teacherService.selectAllSelective(teacher);
		return new Pager<Teacher>(pageSize, currentPage, teachers);
	}

	@RequestMapping("/selectByPrimaryKey")
	@MappingDescription("根据id查找名师信息")
	@ResponseBody
	public Teacher selectByPrimaryKey(Teacher teacher) {
		return teacherService.selectByPrimaryKey(teacher);
	}
}
