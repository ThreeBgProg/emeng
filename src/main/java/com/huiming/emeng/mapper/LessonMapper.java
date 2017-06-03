package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Lesson;

import java.util.List;

public interface LessonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lesson record);

    int insertSelective(Lesson record);

    Lesson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lesson record);

    int updateByPrimaryKey(Lesson record);

    //返回所有课程
    List<Lesson> selectAllLesson();
}