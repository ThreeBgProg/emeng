package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.mapper.ChapterMapper;
import com.huiming.emeng.model.Chapter;

/**
 * Created by LeoMs on 2017/6/3 0003.
 */
@RestController
public class ChapterPublishController {

    @Autowired
    private ChapterMapper chapterMapper;

    //显示所有章节
//    @MappingDescription("显示所有章节")
//    @RequestMapping("/list/chapter")
//    public List<Chapter> showChapter(@RequestParam("lessonId") Integer lessonId){
//        return chapterMapper.selectAllChapterFromLesson(lessonId);
//    }
//    //新增章节
    @MappingDescription("增加章节")
    @RequestMapping(value = "/insert/chapter",method = RequestMethod.POST)
    public int insertChapter(Chapter chapter){

//        System.out.println(chapter);
        return chapterMapper.insertSelective(chapter);
    }

    //删除章节
    @MappingDescription("删除章节")
    @RequestMapping(value = "/delete/chapter",method = RequestMethod.POST)
    public int deleteChapter(@RequestParam("chapterId") Integer chapterId){
        return chapterMapper.deleteByPrimaryKey(chapterId);
    }

    //更新章节
    @MappingDescription("更新章节")
    @RequestMapping(value = "/update/chapter",method = RequestMethod.POST)
    public int deleteChapter(Chapter chapter){
        System.out.println(chapter);
        return chapterMapper.updateByPrimaryKeySelective(chapter);
    }

}
