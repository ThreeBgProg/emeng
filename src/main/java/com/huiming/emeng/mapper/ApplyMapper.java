package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.Apply;

public interface ApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);
    //管理员查询所有报名信息
    public List<Apply> selectAllApply();
}