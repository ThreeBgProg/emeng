package com.huiming.emeng.mapper;

import com.huiming.emeng.model.Passage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PassageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Passage record);

    int insertSelective(Passage record);

    Passage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Passage record);

    int updateByPrimaryKeyWithBLOBs(Passage record);

    int updateByPrimaryKey(Passage record);

    /**
     *主页显示的文章
     * @param passageType 文章在文章表里面对应类型
     * @param showPassageNums 在主页显示条数
     * @return
     */
    List<Passage> selectByTypeAndDescendWithTime(@Param("passageType") Integer passageType, @Param("showPassageNums") Integer showPassageNums);

    /**
     * 返回某类文章在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     * @param passageType
     * @return
     */
    int selectByPassageType(Integer passageType);

    /**
     * 根据文章类型查询第几页数据
     * @param passageType 文章的类型
     * @param fromIndex 从第几条开始
     * @param pageSize 每页显示个数
     * @return
     */
    List<Passage> selectPassageWithPagesizeFromFromindex(@Param("passageType") Integer passageType, @Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

    /**
     * 返回热点推荐文章，默认根据推荐等级正序排列，返回7条数据
     * @return
     */
    List<Passage> selectRecommendPassageList();
    
    public List<Passage> fingPassage(Map<String, String> map);
}