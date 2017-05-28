package com.huiming.emeng.service;

import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章正文显示service处理
 * Created by LeoMs on 2017/5/28 0028.
 */
@Service
public class PassageMainService {

    @Autowired
    private PassageMapper passageMapper;

    public List<Passage> getPassageMainList(Integer passageType, Integer passageId){

        List<Passage> passageList = new ArrayList<Passage>();

        return null;
    }
}
