package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.bo.SchoolWithLocation;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.School;

public interface SchoolService {
	public int deleteByPrimaryKey(Integer id);

	public int insert(School record);

	public int insertSelective(School record);

	public School selectByPrimaryKey(Integer id);

	public int updateByPrimaryKeySelective(School record);

	public int updateByPrimaryKey(School record);

	public Pager<SchoolWithLocation> selectAllByPage(Integer currentPage, Integer pageSize);

	public List<SchoolWithLocation> selectAll();

	public List<School> selectByProvince(Integer provinceId);

//	public List<School> selectSchoolsByTypeAndProvinceId(Integer provinceId, Byte type);
//
//	public List<School> selectSchoolsByType(Byte type);

}
