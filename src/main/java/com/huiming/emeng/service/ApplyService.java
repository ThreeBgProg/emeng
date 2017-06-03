package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.model.Apply;

public interface ApplyService {

	/*
	 * 根据主键删除
	 */
	public int deleteByPrimaryKey(Integer id);
	/*
	 * 后台生成报名表
	 */
	public void insert(Apply apply);
	
	/*
	 * 后台查询报名表的全部信息
	 */
	public List<Apply> selectAllApply();

    int insertSelective(Apply record);

	Apply selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Apply record);

	int updateByPrimaryKey(Apply record);

}
