package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectSelective(User record);
    
    List<User> selectAll();
    
    List<User> selectAllSelective(User record);
}