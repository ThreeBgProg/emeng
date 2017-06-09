package com.huiming.emeng.service;


import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;

public interface AdvertisementService { 
	 
	int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);
    
    
    /**
     * 查询第几页数据
     */
    Pager<Advertisement> selectAdvertisementWithPagesizeFromFromindex(Integer fromIndex,Integer pageSize);

}
