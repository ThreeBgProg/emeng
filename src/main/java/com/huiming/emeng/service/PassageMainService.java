package com.huiming.emeng.service;

import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 非课程文章正文显示service处理
 * Created by LeoMs on 2017/5/28 0028.
 */
@Service
public class PassageMainService {

    @Autowired
    private PassageMapper passageMapper;

    public List<Passage> getPassageMainList(Integer passageId){
        Passage passage = passageMapper.selectByPrimaryKey(passageId);
        Byte passageType = passage.getType();
        List<Passage> allPassageList = passageMapper.selectAllPassageByPassageType(passageType);

        List<Passage> threePassage = new ArrayList<Passage>();
        int i = 0;
        int allPassageLength = allPassageList.size();
        for(Passage passage1 : allPassageList){
            if(passage.equals(passage1)) break;
            i++;
        }
        if(0 == i){
            threePassage.add(0,null);
        } else {
            threePassage.add(0,allPassageList.get(i - 1));
        }
        threePassage.add(1,passage);
        if(i >= allPassageLength-1){
            threePassage.add(2,null);
        } else {
            threePassage.add(2,allPassageList.get(i + 1));
        }
        return threePassage;

    }
}
