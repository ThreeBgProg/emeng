package com.huiming.emeng.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public Object insert(HttpServletRequest request,PostWithBLOBs postWithBLOBs,Model model){
		
		//获取发帖者信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		postWithBLOBs.setUserId(user.getId());
		postWithBLOBs.setUsername(user.getUsername());
		postWithBLOBs.setReleaseTime(new Date());
		
		
		postService.insert(postWithBLOBs);
		
		Map<String, String> respondate=new HashMap<>();
		respondate.put("message", "添加成功");
		
		return respondate;
	}
	
	@RequestMapping("postdePK")
	@MappingDescription("根据id删除论坛")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model){
		
		postService.deleteByPrimaryKey(id);
		//已经通过审核的
		Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "删除成功");
		respondate.put("postList", postList);
		
		return respondate;
	}
	
	@RequestMapping("postsePK")
	@ResponseBody
	@MappingDescription("管理员根据id查找论坛")
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		PostWithBLOBs postWithBLOBs = postService.selectByPrimaryKey(id);
		return postWithBLOBs;
	}
	
	@RequestMapping("userpostsePK")
	@ResponseBody
	@MappingDescription("普通用户根据id查找论坛")
	public Object userselectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		PostWithBLOBs postWithBLOBs = postService.selectByPrimaryKey(id);
		//访问量添加1
		postWithBLOBs.setVisit(postWithBLOBs.getVisit()+1);
		postService.updateByPrimaryKeySelective(postWithBLOBs);
		
		return postWithBLOBs;
	}	
	
	
	@RequestMapping("postupPKS") 
	@MappingDescription("更新") 
	@ResponseBody
	public Object updateByPrimaryKeySelective(PostWithBLOBs record,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize) {
			// TODO Auto-generated method stub
			 postService.updateByPrimaryKeySelective(record);
			//已经通过审核的
			Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);
			
			Map<Object, Object> respondate=new HashMap<>();
			respondate.put("message", "更新成功");
			respondate.put("postList", postList);
			
			return respondate;
	}


	@RequestMapping("postupPKW")
	@MappingDescription("更新")
	@ResponseBody
	public Object updateByPrimaryKeyWithBLOBs(PostWithBLOBs record,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize) {
		postService.updateByPrimaryKeyWithBLOBs(record);
		//已经通过审核的
		Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		respondate.put("postList", postList);
		
		return respondate;
	}


	@RequestMapping("postupPK")
	@MappingDescription("更新")
	@ResponseBody
	public Object updateByPrimaryKey(Post record,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize) { 
		postService.updateByPrimaryKey(record);
		//已经通过审核的
		Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		respondate.put("postList", postList);
		
		return respondate;
	}
	
	@ResponseBody 
	@MappingDescription("论坛分页查询")
    @RequestMapping("postPage")
    public Object postPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
                                  @RequestParam(value="states",defaultValue="-2") Integer states ){
		Map< String, Object> respondate = new HashMap<String, Object>();
		//刚开始进入页面，三个都要查询
		if(states==-2){
			Pager<Post> postList0 = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,0);
			Pager<Post> postList1 = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);
			Pager<Post> postList2 = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,-1);
			respondate.put("postList0", postList0);
			respondate.put("postList1", postList1);
			respondate.put("postList2", postList2);
		}
		else{
			 //添加查询分页结果
	        Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,states);
	        respondate.put("postList", postList);
		}
   
        return respondate;
    }
	
	@ResponseBody 
	@MappingDescription("论坛分页查询（按最新")
    @RequestMapping("userpostPage")
    public Object userpostPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		    Map< String, Object> respondate = new HashMap<String, Object>();

			Pager<Post> postList = postService.selectPostWithPagesizeFromFromindex(pageNum, pageSize,1);	
			respondate.put("postList", postList);
   
        return respondate;
    }
	
	
	@ResponseBody 
	@MappingDescription("论坛分页查询（按热度）")
    @RequestMapping("userpostPageVist")
    public Object userpostPageListVist(
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		    Map< String, Object> respondate = new HashMap<String, Object>();

			Pager<Post> postList = postService.selectPostByVist(pageNum, pageSize,1);	
			respondate.put("postList", postList);
   
        return respondate;
    }
	
}
