package com.huiming.emeng.service;

import com.huiming.emeng.enums.HomePagePassage;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/21 0021.
 */
@Service
public class HomePagePassageService {

    @Autowired
    PassageMapper passageMapper;

    public List<Passage> selectByTypeAndDescendWithTime(HomePagePassage homePagePassage){
        return passageMapper.selectByTypeAndDescendWithTime(homePagePassage);
    }

}