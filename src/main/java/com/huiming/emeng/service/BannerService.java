package com.huiming.emeng.service;

import com.huiming.emeng.bo.PassageWithBanner;
import com.huiming.emeng.model.Banner;

import java.util.List;

public interface BannerService {

	List<PassageWithBanner> selectAll();

	int deleteByPrimaryKey(Integer id);

	int deleteByPassageId(Integer id);

	int insert(Banner record);

	int insertSelective(Banner record);

	int updateByPrimaryKeySelective(Banner record);

	int updateByPrimaryKey(Banner record);

	Banner selectByPrimaryKey(Integer id);

	Banner selectByPassageId(Integer id);
}
