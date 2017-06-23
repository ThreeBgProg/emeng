package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Teacher;

public interface TeacherService {
	public int deleteByPrimaryKey(Integer id);

	public int updateTeacher(Teacher teacher);

	public Teacher selectByPrimaryKey(Teacher teacher);

	/**
	 * 分页查找所有名师
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<Teacher> selectAllTeacher(Integer currentPage, Integer pageSize);

	public int insertTeacher(Teacher teacher);

	/**
	 * 分页查询名师信息
	 * 
	 * @param teacher
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<Teacher> selectAllSelective(Teacher teacher, Integer currentPage, Integer pageSize);
}
