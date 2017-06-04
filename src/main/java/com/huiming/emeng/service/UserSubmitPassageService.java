package com.huiming.emeng.service;

import com.huiming.emeng.common.PassageType;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

/**
 * Created by LeoMs on 2017/5/31 0031.
 */
@Service
public class UserSubmitPassageService {


    @Autowired
    PassageMapper passageMapper;

    public int submitPassage (Passage passage, User user) {

        passage.setAuthor(user.getUsername());
        passage.setType(PassageType.OTHER);
        passage.setState(new Byte("0"));
        passage.setPublishTime(new Date());
        return passageMapper.insertSelective(passage);
    }
}
