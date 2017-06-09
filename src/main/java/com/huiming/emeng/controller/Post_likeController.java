package com.huiming.emeng.controller;

import java.util.List; 

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.Post_like;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.PostService;
import com.huiming.emeng.service.Post_likeService;


@Controller
public class Post_likeController {

	@Autowired
	private Post_likeService post_likeService;
	
	@Autowired
	private PostService postService;
	
	@RequestMapping("post_like")
	@MappingDescription("用户论坛点赞")
	public String post_like(HttpServletRequest request,@RequestParam("post_id") Integer post_id){
		
		Post_like post_like = new Post_like();
		User user = (User) request.getSession().getAttribute("user");	
		
		post_like.setPost_id(post_id);		
		post_like.setUser_id(user.getId());
		
		int id=post_id;
		
		List<Post_like> lists = post_likeService.isLiked(post_like);
		if(lists.size()>0){//已经点赞过
			post_likeService.deleteLiked(post_like);
			//并且论坛点赞更新
			Post post=postService.selectByPrimaryKey(id);
			post.setLike(post.getLike()-1);//点赞加一
			postService.updateByPrimaryKey(post);
		}else {
			post_likeService.insert(post_like);//还没点赞过
			//并且论坛点赞更新
			Post post=postService.selectByPrimaryKey(id);
			post.setLike(post.getLike()+1);//点赞加一
			postService.updateByPrimaryKey(post);
			
		}		
		return null;
	}
}