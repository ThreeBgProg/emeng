package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.Video;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    //添加课程视频
    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);
    //课程id
    List<Video> selectBylesson(Integer lesson);
    //章节id
    List<Video> selectBychapter(Integer chapter);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}