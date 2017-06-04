package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;

public interface PostMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(PostWithBLOBs record);

    int insertSelective(PostWithBLOBs record);

    PostWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostWithBLOBs record);

    int updateByPrimaryKey(Post record);
    
    public List<Post> findPost(Map<String, String> map);
}