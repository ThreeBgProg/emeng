package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.bo.PassageWithBanner;
import com.huiming.emeng.model.Banner;

public interface BannerService {

	public List<PassageWithBanner> selectAll();

	public int deleteByPrimaryKey(Integer id);

	public int deleteByPassageId(Integer id);

	public int insert(Banner record);

	public int insertSelective(Banner record);

	public int updateByPrimaryKeySelective(Banner record);

	public int updateByPrimaryKey(Banner record);

	public Banner selectByPrimaryKey(Integer id);

	public Banner selectByPassageId(Integer id);
}
