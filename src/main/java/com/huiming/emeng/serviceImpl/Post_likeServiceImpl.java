package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.Post_likeMapper;
import com.huiming.emeng.model.Post_like;
import com.huiming.emeng.service.Post_likeService;

@Service("post_likeService")
public class Post_likeServiceImpl implements Post_likeService {

	@Autowired 
    private Post_likeMapper post_likeMapper;
	
	public List<Post_like> isLiked(Post_like post_like) {
		return post_likeMapper.isLiked(post_like);
	}

	public int insert(Post_like post_like) {
		return post_likeMapper.insert(post_like);
	}

	public int deleteLiked(Post_like post_like) {
		return post_likeMapper.deleteLiked(post_like);
	}

}
