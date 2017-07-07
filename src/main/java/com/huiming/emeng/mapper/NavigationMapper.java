package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Navigation;

import sun.awt.image.ImageWatched.Link;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NavigationMapper {
   
	int deleteByPrimaryKey(Integer id);
 
    int insert(Navigation record);
 
    int insertSelective(Navigation record);

    Navigation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Navigation record);

    int updateByPrimaryKey(Navigation record);

    List<Navigation> selectAllNavigation();
    
    /**
     * 返回某类文章在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromNavigation();

    /**
     * 根据文章类型查询第几页数据
     */
    List<Navigation> selectNavigationWithPagesizeFromFromindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

    
 
}