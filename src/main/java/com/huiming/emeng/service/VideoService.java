package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Video;

public interface VideoService {

    int deleteByPrimaryKey(Integer id);

    //添加课程视频
    int insert(Video record);

    int insertSelective(Video record);

    //根据主键查找id
    Video selectByPrimaryKey(Integer id);

    //课程id
    List<Video> selectBylesson(Integer lesson);
    //章节id
    List<Video> selectBychapter(Integer chapter);
    
    //选择性更新
    int updateByPrimaryKeySelective(Video record);
    //所有字段更新
    int updateByPrimaryKey(Video record);
    
    
    /**
     * 查询第几页数据
     */
    Pager<Video> selectVideoWithPagesizeFromFromindex(Integer fromIndex,Integer pageSize);

}
