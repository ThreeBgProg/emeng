package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.Links;

import sun.awt.image.ImageWatched.Link;

public interface LinksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Links record);

    int insertSelective(Links record);

    Links selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Links record);

    int updateByPrimaryKey(Links record);
    
    public List<Link> selectAllLink();
}