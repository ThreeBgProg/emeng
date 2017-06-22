package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Links;

import sun.awt.image.ImageWatched.Link;

public interface LinksService {
 
	int deleteByPrimaryKey(Integer id);

    int insert(Links record);

    int insertSelective(Links record);

    Links selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Links record);

    int updateByPrimaryKey(Links record);
    
    public List<Link> selectAllLink();
    

    /**
     * 查询第几页数据
     */
    Pager<Link> selectLinkWithPagesizeFromFromindex(Integer fromIndex,Integer pageSize);

}
