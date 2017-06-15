package com.huiming.emeng.mapper;

import java.util.List;

import com.huiming.emeng.model.Post_like;

public interface Post_likeMapper {
 
	public List<Post_like> isLiked(Post_like post_like);//是否已经点赞过
	
	public int insert(Post_like post_like);//第一次点赞存储
	
	public int deleteLiked(Post_like  post_like);//取消点赞
}
