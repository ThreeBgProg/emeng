package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Teacher;

public interface TeacherMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Teacher record);

	int insertSelective(Teacher record);

	Teacher selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Teacher record);

	int updateByPrimaryKeyWithBLOBs(Teacher record);

	int updateByPrimaryKey(Teacher record);

	/**
	 * 分页根据实例查找名师
	 * @param record
	 * @return
	 */
	List<Teacher> selectSelective(@Param("teacher")Teacher teacher,@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
	
	
	 /** 分页根据实例模糊查詢名师
	 * @param record
	 * @return
	 */
	List<Teacher> selectByTeacherSelective(@Param("teacher")Teacher teacher,@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
	
	
	/**
	 * 分页所有查找名师
	 * @param record
	 * @return
	 */
	List<Teacher> selectAll(@Param("teachertype")Byte teachertype,@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
	
	/**
	 * 查询对应的数据总数
	 * @param record
	 * @return
	 */
	int selectCountSelective(Teacher record);
	
	/**
	 * 查询数据总数
	 * @param record
	 * @return
	 */
	int selectCount(Byte teachertype);
	
	/**
	 * 查询数据总数
	 * @param record
	 * @return
	 */
	int selectCountByTeacher(Teacher teacher);

	public List<Teacher> findTeacher(Map<String, String> map);
	
	
}