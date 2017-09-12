package com.huiming.emeng.controller;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.PassagePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LeoMs on 2017/6/2 0002.
 */
@Controller
public class PassagePublishController {

    @Autowired
    private PassagePublishService passagePublishService;

    
    @ResponseBody
    @MappingDescription("添加文章")
    @RequestMapping(value = "/insert/passage", method = RequestMethod.POST)    
    public int insertPassage(Passage passage){

        return passagePublishService.insertPassage(passage);
    }

    
    @ResponseBody
    @MappingDescription("更新文章")
    @RequestMapping(value = "/update/passage",method = RequestMethod.POST)
    public int updatePassage(Passage passage){

        return passagePublishService.updatePassage(passage);
    }

    
    @ResponseBody
    @MappingDescription("删除文章")
    @RequestMapping(value = "/delete/passage", method = RequestMethod.POST)   
    public int deletePassage(@RequestParam("passageId") Integer passageId){

        return passagePublishService.deletePassage(passageId);
    }

}
