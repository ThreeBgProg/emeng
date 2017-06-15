package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.model.Teacher;

public interface TeacherService {
	public int deleteByPrimaryKey(Integer id);

	public int updateTeacher(Teacher teacher);

	public Teacher selectByPrimaryKey(Teacher teacher);

	public List<Teacher> selectAllTeacher();

	public int insertTeacher(Teacher teacher);

	public List<Teacher> selectAllSelective(Teacher teacher);
}
