package com.huiming.emeng.controller;

import com.huiming.emeng.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * 主页controller，由我们三个共同开发
 * 负责向前端传我们各自负责的模块对应的参数
 * Created by LeoMs on 2017/5/17 0017.
 */
@Controller("/")
public class HomePageController {

    /**
     * 注入导航页表的service
     */
    @Autowired
    NavigationService navigationService;

    /**
     * 主页控制跳转方法
     * @param model 存放主页所需要的实例化对象
     * @return 返回主页页面
     */
    public String homePage(Model model){

        //添加导航表模块
        model.addAttribute("navigationList", navigationService.selectAllNavigation());

        return "/";
    }


}
