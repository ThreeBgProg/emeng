package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Teacher;

public interface TeacherService {
	int deleteByPrimaryKey(Integer id);

	int updateTeacher(Teacher teacher);

	Teacher selectByPrimaryKey(Teacher teacher);

	/**
	 * 分页查找所有名师
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
    Pager<Teacher> selectAllTeacher(Byte type, Integer currentPage, Integer pageSize);

	int insertTeacher(Teacher teacher);

	/**
	 * 分页查询名师信息
	 * 
	 * @param teacher
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
    Pager<Teacher> selectAllSelective(Teacher teacher, Integer currentPage, Integer pageSize);
	
	 /** 分页模糊查询名师信息
	 * 
	 * @param teacher
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
     Pager<Teacher> selectByTeacherSelective(Teacher teacher, Integer currentPage, Integer pageSize);
}
