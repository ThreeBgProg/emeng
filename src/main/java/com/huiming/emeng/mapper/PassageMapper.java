package com.huiming.emeng.mapper;

import com.huiming.emeng.enums.HomePagePassage;
import com.huiming.emeng.model.Passage;

import java.util.List;

public interface PassageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Passage record);

    int insertSelective(Passage record);

    Passage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Passage record);

    int updateByPrimaryKeyWithBLOBs(Passage record);

    int updateByPrimaryKey(Passage record);

    /**
     *
     * @param homePagePassage 在主页显示的文章以及文章数目，是一个枚举类
     * @return 返回文章数组
     */
    List<Passage> selectByTypeAndDescendWithTime(HomePagePassage homePagePassage);
}