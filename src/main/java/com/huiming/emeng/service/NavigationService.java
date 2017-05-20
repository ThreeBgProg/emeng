package com.huiming.emeng.service;

import com.huiming.emeng.mapper.NavigationMapper;
import com.huiming.emeng.model.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/20 0020.
 */
@Service
public class NavigationService {

    @Autowired
    NavigationMapper navigationMapper;

    public List<Navigation> selectAllNavigation(){
        return navigationMapper.selectAllNavigation();
    }
}
