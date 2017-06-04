package com.huiming.emeng.controller;

import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.PassagePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by LeoMs on 2017/6/2 0002.
 */
@Controller
@RequestMapping("/publisher")
public class PassagePublishController {

    @Autowired
    private PassagePublishService passagePublishService;


    @ResponseBody
    @RequestMapping(value = "/insert/passage", method = RequestMethod.POST)
    public int insertPassage(@RequestBody Passage passage){
        passage.setState(new Byte("2"));
        passage.setPublishTime(new Date());
        return passagePublishService.insertPassage(passage);
    }

    @ResponseBody
    @RequestMapping(value = "/update/passage",method = RequestMethod.PUT)
    public int updatePassage(@RequestBody Passage passage){

        return passagePublishService.updatePassage(passage);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/passage", method = RequestMethod.DELETE)
    public int deletePassage(@RequestParam("passageId") Integer passageId){

        return passagePublishService.deletePassage(passageId);
    }

}
