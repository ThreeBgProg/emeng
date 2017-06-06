package com.huiming.emeng.service;

import com.huiming.emeng.mapper.ChapterMapper;
import com.huiming.emeng.mapper.LessonMapper;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/29 0029.
 */
@Service
public class LessonService {

    @Autowired
    private LessonMapper lessonMapper;

    //文章模块
    @Autowired
    private PassageMapper passageMapper;

    //章节模块
    @Autowired
    private ChapterMapper chapterMapper;

    public List<Lesson> selectAllLesson(){
        return lessonMapper.selectAllLesson();
    }
}
