package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.School;

public interface SchoolService {
	public int deleteByPrimaryKey(Integer id);

	public int insert(School record);

	public int insertSelective(School record);

	public School selectByPrimaryKey(Integer id);

	public int updateByPrimaryKeySelective(School record);

	public int updateByPrimaryKey(School record);

	public Pager<School> selectAllByPage(Integer currentPage, Integer pageSize);

	public List<School> selectAll();

}
