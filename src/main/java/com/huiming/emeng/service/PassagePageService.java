package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LeoMs on 2017/5/25 0025.
 */
@Service
public class PassagePageService {

    @Autowired
    private PassageMapper passageMapper;

    //根据分页信息返回文章分页对象
    public Pager<Passage> getPassagePage(Integer passageType,Integer pageNum,Integer pageSize){


        //总记录数
        Integer totalRecord = passageMapper.selectByPassageType(pageSize);
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

        Pager<Passage> pager = new Pager<Passage>(pageSize, pageNum, totalRecord, totalPage, passageMapper.selectPassageWithPagesizeFromFromindex(passageType, fromIndex, pageSize));
        return pager;
    }


}
