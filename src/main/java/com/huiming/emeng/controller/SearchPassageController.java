package com.huiming.emeng.controller;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LeoMs on 2017/6/3 0003.
 */
@Controller
public class SearchPassageController {

    @Autowired
    private PassageMapper passageMapper;

    @ResponseBody
    @MappingDescription("模糊查询课程文章")
    @RequestMapping("/lessonPassage/search/list")
    public List<Passage> searchLessonPassage(@RequestParam("lessonId") Integer lessonId,
                                             @RequestParam("searchInfo") String searchInfo){
        return passageMapper.selectLessonPassageByTitle(searchInfo,lessonId);
    }

    @ResponseBody
    @MappingDescription("模糊查询非课程文章")
    @RequestMapping("/lessonPassage/search/list")
    public List<Passage> searchLessonPassage(@RequestParam("passageType") Byte passageType,
                                             @RequestParam("searchInfo") String searchInfo){
        return passageMapper.selectPassageByPassageType(searchInfo,passageType);
    }
}
