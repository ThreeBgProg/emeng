package com.huiming.emeng.service;

import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/28 0028.
 */
@Service
public class PassageRecommendService {

    @Autowired
    private PassageMapper passageMapper;

    //返回每个分页界面中的推荐文章模块
    public List<Passage> getRecommondPassageList(){
        //显示7条记录
        return passageMapper.selectRecommendPassageList();
    }
}
