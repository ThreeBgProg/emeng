package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.TeacherMapper;
import com.huiming.emeng.model.Teacher;
import com.huiming.emeng.service.TeacherService;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherMapper teacherMapper;

	/*
	 * 根据主键删除
	 */
	public int deleteByPrimaryKey(Integer id) {
		return teacherMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 用户信息更新
	 * 
	 * @return
	 */
	public int updateTeacher(Teacher teacher) {
		return teacherMapper.updateByPrimaryKey(teacher);

	}

	/**
	 * 查询
	 * 
	 * @param user
	 * @return
	 */
	public Teacher selectByPrimaryKey(Teacher teacher) {
		return teacherMapper.selectByPrimaryKey(teacher.getId());

	}

	/**
	 * 获取所有有效用户
	 * 
	 * @return
	 */
	public List<Teacher> selectAllTeacher() {
		return teacherMapper.selectSelective(new Teacher());

	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	public int insertTeacher(Teacher teacher) {
		return teacherMapper.insert(teacher);

	}

	/**
	 * 使用某个或某些字段查询符合的所有信息
	 * 
	 * @param user
	 * @return
	 */
	public List<Teacher> selectAllSelective(Teacher teacher) {
		return teacherMapper.selectSelective(teacher);

	}
}
