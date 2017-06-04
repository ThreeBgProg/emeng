package com.huiming.emeng.service;

import com.huiming.emeng.model.Links;

public interface LinksService {
 
	int deleteByPrimaryKey(Integer id);

    int insert(Links record);

    int insertSelective(Links record);

    Links selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Links record);

    int updateByPrimaryKey(Links record);
}
