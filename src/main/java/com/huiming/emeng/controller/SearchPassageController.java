package com.huiming.emeng.controller;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.SearchPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LeoMs on 2017/6/3 0003.
 */
@RestController
public class SearchPassageController {

    @Autowired
    private SearchPassageService searchPassageService;

    @MappingDescription("模糊查询课程文章")
    @RequestMapping("/lessonPassage/search/list")
    public Object searchLessonPassage(@RequestParam("lessonId") Integer lessonId,
                                      @RequestParam("searchInfo") String searchInfo,
                                      @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,

                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
            Pager<Passage> pager = searchPassageService.searchLessonPassage(lessonId,pageNum,pageSize,searchInfo);
            Object object = JSON.toJSON(pager);
        System.out.println(object);
            return object;
    }

    @ResponseBody
    @MappingDescription("模糊查询文章")
    @RequestMapping("/passage/search/list")
    public Object searchLessonPassage(@RequestParam("searchInfo") String searchInfo,
                                       @RequestParam(value = "pageSize",defaultValue = "15")Integer pageSize,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){

            Pager<Passage> pager = searchPassageService.searchPassage(pageNum,pageSize,searchInfo);
            Object object = JSON.toJSON(pager);
            return object;
    }
}
