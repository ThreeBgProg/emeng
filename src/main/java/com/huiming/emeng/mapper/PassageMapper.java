package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Passage;

public interface PassageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Passage record);

    int insertSelective(Passage record);

    Passage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Passage record);

    int updateByPrimaryKeyWithBLOBs(Passage record);

    int updateByPrimaryKey(Passage record);
}