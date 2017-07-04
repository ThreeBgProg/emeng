package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.School;

public interface SchoolMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(School record);

	int insertSelective(School record);

	School selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(School record);

	int updateByPrimaryKey(School record);

	List<School> selectAllByPage(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

	List<School> selectAll();

	List<School> selectByProvince(Integer provinceId);

	List<School> selectSchoolsByTypeAndProvinceId(Integer provinceId,Byte type);

	List<School> selectSchoolsByType(Byte type);

	int selectCount();
}