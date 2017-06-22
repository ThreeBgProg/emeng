package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Navigation;


import java.util.List;

public interface NavigationMapper {
   
	int deleteByPrimaryKey(Integer id);
 
    int insert(Navigation record);
 
    int insertSelective(Navigation record);

    Navigation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Navigation record);

    int updateByPrimaryKey(Navigation record);

    List<Navigation> selectAllNavigation();
    
 
}