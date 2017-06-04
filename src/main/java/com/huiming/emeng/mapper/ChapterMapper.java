package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Chapter;

import java.util.List;

public interface ChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    Chapter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    List<Chapter> selectAllChapterFromLesson(Integer lessonId);
}