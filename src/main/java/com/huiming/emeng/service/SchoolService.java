package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.bo.SchoolWithLocation;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.School;

public interface SchoolService {
	int deleteByPrimaryKey(Integer id);

	int insert(School record);

	int insertSelective(School record);

	School selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(School record);

	int updateByPrimaryKey(School record);

	Pager<SchoolWithLocation> selectAllByPage(Integer currentPage, Integer pageSize);

	List<SchoolWithLocation> selectAll();

	Pager<SchoolWithLocation> selectSchoolSelectivePage(School school, Integer currentPage, Integer pageSize);
	
	List<SchoolWithLocation> selectSchoolSelective(School school);
}
