package com.huiming.emeng.controller;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Navigation;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.NavigationService;
import com.huiming.emeng.service.PassageMainService;
import com.huiming.emeng.service.PassagePageService;
import com.huiming.emeng.service.PassageRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/23 0023.
 */
@Controller
public class PassagePageController {

    //导航模块
    @Autowired
    private NavigationService navigationService;

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
    @RequestMapping("/passage/{passageType}/list")
    public String passagePageList(ModelMap modelMap, @PathVariable("passageType") Integer passageType,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
        //添加导航表模块
        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加查询分页结果
        Pager<Passage> passagePage = passagePageService.getPassagePage(passageType, pageNum, pageSize);
        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();

        modelMap.put("navigationList", navigationList);
        modelMap.put("pagingResult", passagePage);
        modelMap.put("recommendList", recommendList);
        return "passage/list";
    }

    /**
     * 文章正文页面
     * @param modelMap
     * @param passageType
     * @param passageId
     * @return
     */
    @RequestMapping("/passage/{passageType}/{passageId}")
    public String passagePageList(ModelMap modelMap, @PathVariable("passageType") Integer passageType,
                                  @PathVariable("passsageId") Integer passageId){
        //添加导航表模块
        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();
        //添加文章正文模块，显示文章正文的是数组下标为1的对象
        List<Passage> passageMainList = passageMainService.getPassageMainList(passageType, passageId);

        modelMap.put("navigationList", navigationList);
        modelMap.put("recommendList", recommendList);
        modelMap.put("passageMainList", passageMainList);
        return "passage/main";
    }

}
