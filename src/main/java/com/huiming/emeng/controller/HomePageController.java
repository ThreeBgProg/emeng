package com.huiming.emeng.controller;

import com.huiming.emeng.service.HomePagePassageService;
import com.huiming.emeng.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页controller，由我们三个共同开发
 * 负责向前端传我们各自负责的模块对应的参数
 * Created by LeoMs on 2017/5/17 0017.
 */
@Controller()
public class HomePageController {

    /**
     * 注入导航页表的service
     */
    @Autowired
    private NavigationService navigationService;
    @Autowired
    private HomePagePassageService homePagePassageService;

    /**
     * 主页
     * @param model 存放主页所需要的实例化对象
     * @return 返回主页页面
     */
    @RequestMapping("mytest")
    public String homePage(Model model){


        //添加导航表模块
        model.addAttribute("navigationList", navigationService.selectAllNavigation());
        //添加最新资料模块
        model.addAttribute("newestPassageList", homePagePassageService.selectByTypeAndDescendWithTime(1,7));
        //添加思政动态模块
        model.addAttribute("dynamicList",homePagePassageService.selectByTypeAndDescendWithTime(2,12));
        //添加马院头条模块
        model.addAttribute("headlineList",homePagePassageService.selectByTypeAndDescendWithTime(3,12));
        //添加经典作家（名师新作）模块
        model.addAttribute("teacherPassageList",homePagePassageService.selectByTypeAndDescendWithTime(4,3));
        return "error";
    }


}
