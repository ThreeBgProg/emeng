package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.model.Apply;

public interface ApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);
    //管理员查询所有报名信息
    public List<Apply> selectAllApply();
    
    /**
     * 返回广告在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromApply();
    
    /**
     * 查询第几页数据
     */
    List<Apply> selectApplyWithPagesizeFromFromindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

}