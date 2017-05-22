package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Video;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    //添加课程视频
    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}