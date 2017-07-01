package com.huiming.emeng.controller;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.PassageMainService;
import com.huiming.emeng.service.PassagePageService;
import com.huiming.emeng.service.PassageRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 非课程文章显示
 * Created by LeoMs on 2017/5/23 0023.
 */
@RestController
public class PassagePageController {

//    //导航模块
//    @Autowired
//    private NavigationService navigationService;

    //分页查询模块
    @Autowired
    private PassagePageService passagePageService;

    //热点推荐模块
    @Autowired
    private PassageRecommendService passageRecommendService;

    //文章正文显示模块
    @Autowired
    private PassageMainService passageMainService;

    /**
     * 文章分页页面
     * @param modelMap
     * @param passageType
     * @param pageNum
     * @param pageSize
     * @return
     */
    @MappingDescription("非课程文章分页查询")
    @RequestMapping("/passage/passagelist")
        public Object passagePageList(ModelMap modelMap, @RequestParam("passageType") Byte passageType,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
                                  @RequestParam(value = "lessonId", required = false) Integer lessonId){
        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加查询分页结果
        Pager<Passage> passagePage = null;
        if(null == lessonId){
            passagePage = passagePageService.getPassagePage(passageType, pageNum, pageSize);
        } else {
            passagePage = passagePageService.getReadPassagePage(passageType,pageNum,pageSize,lessonId);
        }

        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();

//        modelMap.put("navigationList", navigationList);
        modelMap.put("passagePage", passagePage);
        modelMap.put("recommendList", recommendList);
        Object object = JSON.toJSON(modelMap);
//        System.out.println(object);
        return object;
    }

    @MappingDescription("返回名师文章分页")
    @RequestMapping("/teacher/passagelist")
    public Object teacherPassagePageList(ModelMap modelMap, @RequestParam("author") String author,
                                         @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                         @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){

        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();
        Pager<Passage> passagePage = passagePageService.getTeacherPassagePage(author,pageNum,pageSize);
        modelMap.put("passagePage", passagePage);
        modelMap.put("recommendList", recommendList);
        return JSON.toJSON(modelMap);

    }

    /**
     * 文章正文页面
     * @param modelMap
     *
     * @param passageId
     * @return
     */
    @MappingDescription("非课程文章正文页面")
    @RequestMapping("/passage/main")
    public Object passagePageList(ModelMap modelMap, @RequestParam("passageId") Integer passageId){
        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();
        //添加文章正文模块，显示文章正文的是数组下标为1的对象
        List<Passage> passageMainList = passageMainService.getPassageMainList(passageId);

//        modelMap.put("navigationList", navigationList);
        modelMap.put("recommendList", recommendList);
        modelMap.put("passageMainList", passageMainList);
        Object object = JSON.toJSON(modelMap);
        return object;
    }

}
