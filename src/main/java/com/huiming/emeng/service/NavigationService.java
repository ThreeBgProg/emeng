package com.huiming.emeng.service;

import com.huiming.emeng.mapper.NavigationMapper;
import com.huiming.emeng.model.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeoMs on 2017/5/20 0020.
 * 由13124937709用户补充
 */
@Service
public class NavigationService {

    @Autowired
    NavigationMapper navigationMapper;

    public List<Navigation> selectAllNavigation(){
        return navigationMapper.selectAllNavigation();
    }
    
    
    public int deleteByPrimaryKey(Integer id){
    	return navigationMapper.deleteByPrimaryKey(id);
    }
    
    public int insert(Navigation record){
    	return navigationMapper.insert(record);
    }
 
    public int insertSelective(Navigation record){
    	return navigationMapper.insertSelective(record);
    }

    public Navigation selectByPrimaryKey(Integer id){
    	return navigationMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Navigation record){
    	return navigationMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Navigation record){
    	return navigationMapper.updateByPrimaryKey(record);
    }
    
}
