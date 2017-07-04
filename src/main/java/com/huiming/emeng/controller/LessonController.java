package com.huiming.emeng.controller;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.common.LessonPageInfo;
import com.huiming.emeng.common.PassageType;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.ChapterMapper;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Chapter;
import com.huiming.emeng.model.Navigation;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/29 0029.
 */
@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    //导航模块
//    @Autowired
//    private NavigationService navigationService;

    //热点推荐模块
    @Autowired
    private PassageRecommendService passageRecommendService;

    @Autowired
    private PassageMainService passageMainService;

    //文章模块
    @Autowired
    private PassageMapper passageMapper;

    //章节模块
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private PassagePageService passagePageService;


    @MappingDescription("所有课程")
    @RequestMapping({"/lesson"})
    public Object allLesson(ModelMap modelMap){

        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();

        modelMap.put("lessonList", lessonService.selectAllLesson());
//        modelMap.put("navigationList", navigationList);
        modelMap.put("recommendList", recommendList);
        Object object = JSON.toJSON(modelMap);
//        System.out.println(object);
//        System.out.println("ok");
        return object;

    }

    @MappingDescription("相应课程章节")
    @RequestMapping({"/{lessonId}/chapter"})
    public Object allChapter(ModelMap modelMap, @PathVariable("lessonId") Integer lessonId,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize){

        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加阅读书目模块(显示15条)
        List<Passage> readPassageList = passageMapper.selectByTypeAndDescendWithTime(PassageType.YUEDUSHUMU, 15);
        //添加精品在线模块(显示10条)
        List<Passage> qualityOnlinePassageList = passageMapper.selectByTypeAndDescendWithTime(PassageType.JINGPINZAIXIAN, 10);
        //课程所有章节
        Pager<Chapter> chapterList = chapterService.selectAllChapterFromLesson(lessonId,pageSize,pageNum);

        modelMap.put("chapterList", chapterList);
//        modelMap.put("navigationList", navigationList);
        modelMap.put("readPassageList", readPassageList);
        modelMap.put("qualityOnlinePassageList", qualityOnlinePassageList);
        Object object = JSON.toJSON(modelMap);
//        System.out.println(object);
        return object;
    }

    @MappingDescription("章节下的分块")
    @RequestMapping("/{lessonId}/{chapterId}/block")
    public Object allBlock(@PathVariable("lessonId") Integer lessonId,
                           @PathVariable("chapterId") Integer chaptId,
                           ModelMap modelMap){

        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加理论剖析模块
        List<Passage> theoryAnalyseList = passageMapper.selectPassageByLessonIdAndChapterIdAndPassageType(
                lessonId,chaptId,PassageType.LILUNPOUXI);
        //添加参考资料模块
        List<Passage> referenceResourcesList = passageMapper.selectPassageByLessonIdAndChapterIdAndPassageType(
                lessonId,chaptId,PassageType.CANKAOZILIAO);
        //添加案例资源模块
        List<Passage> caseResourceList = passageMapper.selectPassageByLessonIdAndChapterIdAndPassageType(
                lessonId,chaptId,PassageType.ANLIZIYUAN);
        //添加教案推荐模块
        List<Passage> recommendResourcesList = passageMapper.selectPassageByLessonIdAndChapterIdAndPassageType(
                lessonId,chaptId,PassageType.JIAOANTUIJIAN);
        //添加精品课件模块
        List<Passage> PPTSourcesList = passageMapper.selectPassageByLessonIdAndChapterIdAndPassageType(
                lessonId,chaptId,PassageType.JINGPINKEJIAN);
        //添加在线课程模块
        List<Passage> onlineVedioList = passageMapper.selectPassageByLessonIdAndChapterIdAndPassageType(
                lessonId,chaptId,PassageType.SHIPINZIYUAN);


//        modelMap.put("navigationList", navigationList);
        modelMap.put("theoryAnalyseList", theoryAnalyseList);
        modelMap.put("referenceResourcesList", referenceResourcesList);
        modelMap.put("caseResourceList", caseResourceList);
        modelMap.put("recommendResourcesList", recommendResourcesList);
        modelMap.put("PPTSourcesList", PPTSourcesList);
        modelMap.put("onlineVedioList", onlineVedioList);
        Object object = JSON.toJSON(modelMap);
//        System.out.println(object);
        return object;
    }

    @MappingDescription("章节下分块的具体文章(或其他资源)数组")
    @RequestMapping("/{lessonId}/{chapterId}/blockpassagelist")
    public Object blockPassages(
            @PathVariable("lessonId") Integer lessonId,
            @PathVariable("chapterId") Integer chaptId,
            @RequestParam("passageType")Byte passageType,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize,
            ModelMap modelMap){

        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加查询分页结果
        Pager<Passage> lessonPassagePage = passagePageService.getLessonPassagePage(new LessonPageInfo(lessonId,chaptId,pageNum,pageSize,passageType));
        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();

//        modelMap.put("navigationList", navigationList);
        modelMap.put("pagingResult", lessonPassagePage);
        modelMap.put("recommendList", recommendList);
        return JSON.toJSON(modelMap);
    }

    @MappingDescription("课程文章正文页面")
    @RequestMapping("/lesson/chapter/lessonpassage")
    public Object passagePageList(ModelMap modelMap, @RequestParam("passageId") Integer passageId) {
        //添加导航表模块
//        List<Navigation> navigationList = navigationService.selectAllNavigation();
        //添加热点推荐模块
        List<Passage> recommendList = passageRecommendService.getRecommondPassageList();

        Passage passage = passageMapper.selectByPrimaryKey(passageId);


//        modelMap.put("navigationList", navigationList);
        modelMap.put("recommendList", recommendList);
        modelMap.put("passage", passage);

        Object object = JSON.toJSON(modelMap);
        return object;

    }
}
