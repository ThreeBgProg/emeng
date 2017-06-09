package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;

public interface AdvertisementMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);
    /**
     * 返回广告在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromAdvertisement();
    
    /**
     * 查询第几页数据
     */
    List<Advertisement> selectAdvertisementWithPagesizeFromFromindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

}