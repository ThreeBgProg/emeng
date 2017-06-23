package com.huiming.emeng.controller;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.PassagePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by LeoMs on 2017/6/3 0003.
 */
@RestController
public class PublisherSubmitPassageController {

    @Autowired
    private PassageMapper passageMapper;

    @Autowired
    private PassagePageService passagePageService;

    @MappingDescription("显示所有用户投稿")
    @RequestMapping("/submission/list")
    public Object showSubmission(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                                 ModelMap modelMap){

        Pager<Passage> submissionPage = passagePageService.getPassagePage(new Byte("0"),pageNum,pageSize);
        modelMap.put("submissionPage", submissionPage);
        Object object = JSON.toJSON(modelMap);
        return object;
    }

    @MappingDescription("显示用户投稿正文")
    @RequestMapping("/show/submission")
    public Object showSubmission(@RequestParam(value = "passageId") Integer passageId,
                                 ModelMap modelMap){

        Passage passage = passageMapper.selectByPrimaryKey(passageId);
        modelMap.put("passage", passage);
        Object object = JSON.toJSON(modelMap);
        return object;
    }

    /**
     * 如果type为热点推荐，则必须设置推荐等级，1-999
     * @param passage
     * @param passageType
     * @param recommendValue
     * @return
     */
    @MappingDescription("通过审核")
    @RequestMapping("/pass/submission")
    public int passSubmission(Passage passage){
        passage.setState(new Byte("1"));
        passage.setPublishTime(new Date());
        return passageMapper.updateByPrimaryKeySelective(passage);
    }

}
