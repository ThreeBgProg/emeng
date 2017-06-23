package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LeoMs on 2017/6/14 0014.
 */
@Service
public class SearchPassageService {

    @Autowired
    private PassageMapper passageMapper;

    /**
     * 返回模糊查询课程文章分页
     * @param lessonId
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    public Pager<Passage> searchLessonPassage(Integer lessonId,
                                              Integer pageNum,
                                              Integer pageSize,
                                              String title){

        //总记录
        int totalRecord = passageMapper.selectCountOfLessonPassageByTitle(lessonId,title);
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
        Pager<Passage> pager = new Pager<Passage>(pageSize, pageNum, totalRecord,
                totalPage, passageMapper.selectLessonPassageByTitleWithPagesizeFromFromindex(title,lessonId,pageSize,fromIndex));;
        return pager;
    }

    public Pager<Passage> searchPassage(Integer pageNum,
                                        Integer pageSize,
                                        String title){
        //总记录
        int totalRecord = passageMapper.selectCountByTitle(title);
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
        Pager<Passage> pager = new Pager<Passage>(pageSize, pageNum, totalRecord,
                totalPage, passageMapper.selectPassageByTitle(title,fromIndex,pageSize));;
        return pager;
    }
}
