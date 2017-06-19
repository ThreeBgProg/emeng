package com.huiming.emeng.controller;

import com.huiming.emeng.annotation.MappingDescription;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器
 * Created by LeoMs on 2017/6/19 0019.
 */
@Controller
public class PageSkipController {

    @MappingDescription("页面跳转")
    @RequestMapping("/pageskip/{page}")
    public String pageSkip(@PathVariable("page") String page){
        return page;
    }
}
