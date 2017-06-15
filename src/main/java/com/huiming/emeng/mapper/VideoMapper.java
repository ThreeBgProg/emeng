package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.model.Video;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    //添加课程视频
    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);
    //课程id
    List<Video> selectBylesson(Integer lesson);
    //章节id
    List<Video> selectBychapter(Integer chapter);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
    
    public List<Video> findVideo(Map<String, String> map);
    
    /**
     * 返回广告在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromVideo();
    
    /**
     * 查询第几页数据
     */
    List<Video> selectVideoWithPagesizeFromFromindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

}