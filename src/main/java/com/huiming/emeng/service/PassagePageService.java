package com.huiming.emeng.service;

import com.huiming.emeng.common.LessonPageInfo;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/25 0025.
 */
@Service
public class PassagePageService {

    @Autowired
    private PassageMapper passageMapper;

    //根据分页信息返回非课程文章分页对象
    public Pager<Passage> getPassagePage(Byte passageType,Integer pageNum,Integer pageSize){


        //总记录数
        Integer totalRecord = passageMapper.selectByPassageType(passageType);
        //总页数
        Integer totalPage = totalRecord / pageSize;
        if(totalRecord % pageSize != 0){
            totalPage++;
        }
        if(pageNum > totalPage){
            pageNum = totalPage;
        }
        //起始索引
        Integer fromIndex = (pageNum - 1) * pageSize;
        //如果fromIndex为负数，则设为0
        if(fromIndex < 0) {
            fromIndex = 0;
        }

        Pager<Passage> pager = new Pager<Passage>(pageSize, pageNum, totalRecord, totalPage, passageMapper.selectPassageWithPagesizeFromFromindex(passageType,fromIndex,pageSize));
        return pager;
    }

    //根据分页信息返回阅读书目文章分页对象
    public Pager<Passage> getReadPassagePage(Byte passageType,Integer pageNum,Integer pageSize, Integer lessonId){
        //总记录数
        Integer totalRecord = passageMapper.selectByPassageType(passageType);
        //总页数
        Integer totalPage = totalRecord / pageSize;
        if(totalRecord % pageSize != 0){
            totalPage++;
        }
        if(pageNum > totalPage){
            pageNum = totalPage;
        }
        //起始索引
        Integer fromIndex = (pageNum - 1) * pageSize;

        //如果fromIndex为负数，则设为0
        if(fromIndex < 0) {
            fromIndex = 0;
        }
        Pager<Passage> pager = new Pager<>(pageSize, pageNum, totalRecord, totalPage,
                passageMapper.selectReadPassageWithPagesizeFromFromindex(passageType,fromIndex,pageSize,lessonId));
        return pager;
    }

    //根据分页信息返回名师文章分页
    public Pager<Passage> getTeacherPassagePage(String author,Integer pageNum,Integer pageSize){
        //总记录数
        Integer totalRecord = passageMapper.selectCountByAuthor(author);
        //总页数
        Integer totalPage = totalRecord / pageSize;
        if(totalRecord % pageSize != 0){
            totalPage++;
        }
        if(pageNum > totalPage){
            pageNum = totalPage;
        }
        //起始索引
        Integer fromIndex = (pageNum - 1) * pageSize;

        //如果fromIndex为负数，则设为0
        if(fromIndex < 0) {
            fromIndex = 0;
        }
        Pager<Passage> pager = new Pager<>(pageSize, pageNum, totalRecord, totalPage,
                passageMapper.selectPassageByAuthor(author,fromIndex,pageSize));
        return pager;
    }
    //根据分页信息返回课程文章分页对象
    public Pager<Passage> getLessonPassagePage(LessonPageInfo lessonPageInfo){

        Integer pageNum = lessonPageInfo.getPageNum();
        Integer pageSize = lessonPageInfo.getPageSize();
        Byte passageType = lessonPageInfo.getPassageType();

        //总记录数
        Integer totalRecord = passageMapper.selectCountByLessonIdAndChapterIdAndPassageType(
                lessonPageInfo.getLessonId(),
                lessonPageInfo.getChapterId(),
                passageType);
        //总页数
        Integer totalPage = totalRecord / pageSize;
        if(totalRecord % pageSize != 0){
            totalPage++;
        }
        if(pageNum > totalPage){
            pageNum = totalPage;
        }
        //起始索引
        Integer fromIndex = (pageNum - 1) * pageSize;
        //如果fromIndex为负数，则设为0
        if(fromIndex < 0) {
            fromIndex = 0;
        }
        lessonPageInfo.setFromIndex(fromIndex);

        List<Passage> passageList = passageMapper.selectLessonPassageWithPagesizeFromFromindex(lessonPageInfo);
        Pager<Passage> pager = new Pager<Passage>(pageSize, pageNum, totalRecord,
                totalPage, passageList);
        return pager;
    }

}
