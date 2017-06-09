package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.huiming.emeng.model.Teacher;
import com.huiming.emeng.service.TeacherService;

@Controller
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	public List<Teacher> getAllTeacher() {
		return teacherService.selectAllTeacher();
	}

	public int addTeacher(Teacher teacher) {
		return teacherService.insertTeacher(teacher);
	}

	public int updateTeacher(Teacher teacher) {
		return teacherService.updateTeacher(teacher);
	}

	public int deleteTeacher(Integer id) {
		return teacherService.deleteByPrimaryKey(id);
	}

	public List<Teacher> selectAllSelective(Teacher teacher) {
		return teacherService.selectAllSelective(teacher);
	}

	public Teacher selectByPrimaryKey(Teacher teacher) {
		return teacherService.selectByPrimaryKey(teacher);
	}
}
