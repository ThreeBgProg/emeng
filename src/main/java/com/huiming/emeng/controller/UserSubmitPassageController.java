package com.huiming.emeng.controller;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.UserSubmitPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 处理用户投稿
 * Created by LeoMs on 2017/5/31 0031.
 */
@Controller
public class UserSubmitPassageController {

    @Autowired
    UserSubmitPassageService userSubmitPassageService;

    @MappingDescription("用户投稿页面跳转")
    @RequestMapping("/submit/page")
    public String submitPage(ModelMap modelMap){
        return "submit";
    }

    @MappingDescription("用户投稿提交")
    @ResponseBody
    @RequestMapping(value = "/submit/passage", method = RequestMethod.POST)
    public int submitPassage(Passage passage, HttpSession httpSession){

        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            throw new RuntimeException("用户未登录！");
        }
        return userSubmitPassageService.submitPassage(passage,user);

    }
}
