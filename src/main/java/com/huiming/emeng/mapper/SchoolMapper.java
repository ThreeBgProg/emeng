package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.School;

public interface SchoolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
    
    List<School> selectAll();
}