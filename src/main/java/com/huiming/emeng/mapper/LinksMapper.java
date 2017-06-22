package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    /**
     * 返回某类文章在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromLink();

    /**
     * 根据文章类型查询第几页数据
     */
    List<Link> selectLinkWithPagesizeFromFromindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

}