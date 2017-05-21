package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Passage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Passage record);

    int insertSelective(Passage record);

    Passage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Passage record);

    int updateByPrimaryKeyWithBLOBs(Passage record);

    int updateByPrimaryKey(Passage record);


    List<Passage> selectByTypeAndDescendWithTime(@Param("passageType") Integer passageType, @Param("showPassageNums") Integer showPassageNums);
}