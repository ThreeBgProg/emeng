package com.huiming.emeng.service;

import com.huiming.emeng.model.Video;

public interface VideoService {

    int deleteByPrimaryKey(Integer id);

    //添加课程视频
    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}
