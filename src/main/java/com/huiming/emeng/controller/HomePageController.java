package com.huiming.emeng.controller;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.common.PassageType;
import com.huiming.emeng.service.HomePagePassageService;
import com.huiming.emeng.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页controller，由我们三个共同开发
 * 负责向前端传我们各自负责的模块对应的参数
 * Created by LeoMs on 2017/5/17 0017.
 */
@RestController
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
     * @param modelMap 存放主页所需要的实例化对象
     * @return 返回主页页面
     */
    @MappingDescription("主页所有需要用到的对象")
    @RequestMapping("/")
    public Object homePage(ModelMap modelMap){


        //添加导航表模块
        modelMap.put("navigationList", navigationService.selectAllNavigation());
        //添加最新资料模块（最近资料type为1，在主页显示7条）
        modelMap.put("newestPassageList", homePagePassageService.selectByTypeAndDescendWithTime(PassageType.ZUIXINZILIAO,7));
        //添加思政动态模块（思政动态type为2，在主页显示12条）
        modelMap.put("dynamicList",homePagePassageService.selectByTypeAndDescendWithTime(PassageType.SIZHENGDONGTAI,12));
        //添加马院头条模块（马院头条type为3，在主页显示12条）
        modelMap.put("headlineList",homePagePassageService.selectByTypeAndDescendWithTime(PassageType.MAYUANTOUTIAO,12));
        //其他模块你们看着添加


        Object object = JSON.toJSON(modelMap);
        return object;
    }


}
