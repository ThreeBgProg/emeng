package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.Location;

public interface LocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);
    
    List<Location> selectAll();
}