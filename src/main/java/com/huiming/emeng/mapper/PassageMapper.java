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

    /**
     * 根据时间的倒叙查询文章
     * @param type 文章类型
     * @param selectSum 查询数目
     * @return
     */
   // List<Passage> selectByTypeAndDescendWithTime(Integer type, Integer selectSum);
}