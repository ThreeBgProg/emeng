package com.huiming.emeng.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
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
	 * 获取所有有效名师
	 * 
	 * @return
	 */
	public Pager<Teacher> selectAllTeacher(Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = teacherMapper.selectCount();
		Pager<Teacher> teacherPage = new Pager<>(pageSize, currentPage, totalRecord,
				teacherMapper.selectAll(fromIndex, pageSize));
		return teacherPage;

	}

	/**
	 * 添加名师
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
	public Pager<Teacher> selectAllSelective(Teacher teacher, Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = teacherMapper.selectCount();
		Pager<Teacher> teacherPage = new Pager<>(pageSize, currentPage, totalRecord,
				teacherMapper.selectSelective(teacher, fromIndex, pageSize));
		return teacherPage;

	}
}
