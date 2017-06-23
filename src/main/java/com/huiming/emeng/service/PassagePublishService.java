package com.huiming.emeng.service;

import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by LeoMs on 2017/6/2 0002.
 */
@Service
public class PassagePublishService {

    @Autowired
    private PassageMapper passageMapper;

    //发布文章
    public int insertPassage(Passage passage){

       // passage.setAuthor(user.getUsername());
        passage.setPublishTime(new Date());
        passage.setState(new Byte("2"));
        return passageMapper.insertSelective(passage);
    }

    //更新文章
    public int updatePassage(Passage passage){
        return passageMapper.updateByPrimaryKeySelective(passage);
    }

    //删除文章
    public int deletePassage(Integer passageId){
        return passageMapper.deleteByPrimaryKey(passageId);
    }

}
