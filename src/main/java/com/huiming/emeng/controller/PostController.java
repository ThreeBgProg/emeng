package com.huiming.emeng.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Apply;
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
		
		for(int i=0;i<30;i++){
			postWithBLOBs.setContent("黄慧"+i);
			postWithBLOBs.setLike(0);
			postWithBLOBs.setReleaseTime(new Date());
			postWithBLOBs.setReply("黄慧"+i);
			postWithBLOBs.setUsername("黄慧"+i);
			postWithBLOBs.setUserId(i);
			postWithBLOBs.setTitile("黄慧"+i);
			
			postWithBLOBs.setVisit(0);
			int result = postService.insert(postWithBLOBs);
			System.out.println("成功插入"+result+"条论坛信息");
		}
		
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
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		PostWithBLOBs postWithBLOBs = postService.selectByPrimaryKey(id);
		model.addAttribute("postWithBLOBs", postWithBLOBs);
		return postWithBLOBs;
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
	
	@ResponseBody 
	@MappingDescription("论坛分页查询")
    @RequestMapping("postPage")
    public Object postPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
                                  @RequestParam(value="states",defaultValue="-2") Integer states ){
		Map< String, Object> postMap = new HashMap<String, Object>();
		//刚开始进入页面，三个都要查询
		if(states==-2){
			Pager<Post> postList0 = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,0);
			Pager<Post> postList1 = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);
			Pager<Post> postList2 = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,-1);
			postMap.put("postList0", postList0);
			postMap.put("postList1", postList1);
			postMap.put("postList2", postList2);
		}
		else{
			 //添加查询分页结果
	        Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,0);
	        postMap.put("postList", postList);
		}
   
        return postMap;
    }
}
