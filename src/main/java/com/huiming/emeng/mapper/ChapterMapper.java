package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    Chapter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    List<Chapter> selectAllChapterFromLesson(Integer lessonId);

    int selectCountByLesson(@Param("lessonId") Integer lessonId);

    List<Chapter> selectChapterPageFromLesson(@Param("lessonId") Integer lessonId,
                                               @Param("pageSize") Integer pageSize,
                                               @Param("fromIndex") Integer fromIndex);
}