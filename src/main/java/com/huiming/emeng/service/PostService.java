package com.huiming.emeng.service;

import java.util.List;

import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;

/**
 * 我有话说
 * @author zhiwei
 *
 */ 
public interface PostService {

	int deleteByPrimaryKey(Integer id);

    int insert(PostWithBLOBs record);

    int insertSelective(PostWithBLOBs record);

    PostWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostWithBLOBs record);

    int updateByPrimaryKey(Post record);
    
    public List<Post> selectAllPost();
}
