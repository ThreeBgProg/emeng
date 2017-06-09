package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.Banner;

public interface BannerMapper {
	int deleteByPrimaryKey(Integer id);

	int deleteByPassageId(Integer id);

	int insert(Banner record);

	int insertSelective(Banner record);

	Banner selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Banner record);

	int updateByPrimaryKey(Banner record);

	List<Banner> selectAll();
}