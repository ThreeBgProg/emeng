package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import com.huiming.emeng.model.Teacher;

public interface TeacherMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Teacher record);

	int insertSelective(Teacher record);

	Teacher selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Teacher record);

	int updateByPrimaryKeyWithBLOBs(Teacher record);

	int updateByPrimaryKey(Teacher record);

	List<Teacher> selectSelective(Teacher record);

	public List<Teacher> findTeacher(Map<String, String> map);
}