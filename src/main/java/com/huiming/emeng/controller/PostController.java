package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;
import com.huiming.emeng.service.PostService;

/**
 * 
 * @author zhiwei
 * 
 */
@Controller
public class PostController {

	@Autowired
	private PostService postService;
	
	@RequestMapping("postinsert")
	@MappingDescription("插入论坛")
	public String insert(PostWithBLOBs postWithBLOBs,Model model){
		
		int result = postService.insert(postWithBLOBs);
		System.out.println("成功插入"+result+"条论坛信息");
		return null;
	}
	
	@RequestMapping("postdePK")
	@MappingDescription("根据id删除论坛")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		int result = postService.deleteByPrimaryKey(id);
		System.out.println("成功删除"+result+"条论坛信息");
		return null;
	}
	
	@RequestMapping("postsePK")
	@ResponseBody
	@MappingDescription("根据id查找论坛")
	public String selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		PostWithBLOBs postWithBLOBs = postService.selectByPrimaryKey(id);
		model.addAttribute("postWithBLOBs", postWithBLOBs);
		return null;
	}
	
	@RequestMapping("postupPKS")
	@MappingDescription("更新")
	public String updateByPrimaryKeySelective(PostWithBLOBs record) {
		// TODO Auto-generated method stub
		 int result = postService.updateByPrimaryKeySelective(record);
		 System.out.println("成功更新"+result+"条论坛信息");
		 return null;
	}


	@RequestMapping("postupPKW")
	@MappingDescription("更新")
	public String updateByPrimaryKeyWithBLOBs(PostWithBLOBs record) {
		// TODO Auto-generated method stub
		int result = postService.updateByPrimaryKeyWithBLOBs(record);
		System.out.println("成功更新"+result+"条论坛信息");
		return null;
	}


	@RequestMapping("postupPK")
	@MappingDescription("更新")
	public String updateByPrimaryKey(Post record) {
		// TODO Auto-generated method stub
		int result = postService.updateByPrimaryKey(record);
		System.out.println("成功更新"+result+"条论坛信息");
		return null;
	}
}
