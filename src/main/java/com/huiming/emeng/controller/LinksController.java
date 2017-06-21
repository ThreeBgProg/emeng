package com.huiming.emeng.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Links;
import com.huiming.emeng.service.LinksService;

import sun.awt.image.ImageWatched.Link;

/**
 * 友情链接模块
 * @author zhiwei
 *
 */
@Controller
public class LinksController {

	@Autowired
	private LinksService linksService;
	
	@RequestMapping("linksinsert")
	@MappingDescription("插入链接")
	@ResponseBody
	public Object insert(Links links,Model model){

		Map<String, String> respondate = new HashMap<>();
		int result = linksService.insert(links);			
		respondate.put("message", "添加成功");

		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("linksinsertSelect")
	@MappingDescription("插入链接")
	@ResponseBody
	public Object insertSelect(Links links,Model model){
		Map<String, String> respondate = new HashMap<>();
		int result = linksService.insertSelective(links);
		respondate.put("message", "添加成功");
		
		Object object=JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("linksselectPK")
	@MappingDescription("根据id查找查找链接")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Links links = linksService.selectByPrimaryKey(id);

		return links;
	}
	
	@RequestMapping("linksdeletePK")
	@MappingDescription("根据id删除链接")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model){
		
		int result = linksService.deleteByPrimaryKey(id);
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "删除成功");
		//添加查询分页结果
        Pager<Link> linkList = linksService.selectLinkWithPagesizeFromFromindex(pageNum, pageSize);
        respondate.put("linkList", linkList);
        
        Object object=JSON.toJSON(respondate);
		return object;
		
	}
	
	@RequestMapping("linksupdPK")
	@MappingDescription("更新链接")
	@ResponseBody
	public Object updateByPrimaryKey(Links links,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model){
		
		int result = linksService.updateByPrimaryKey(links);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		//添加查询分页结果
        Pager<Link> linkList = linksService.selectLinkWithPagesizeFromFromindex(pageNum, pageSize);
        respondate.put("linkList", linkList);
		
        Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("linksupdPKSelect")
	@MappingDescription("更新链接")
	@ResponseBody
	public Object updateByPrimaryKeySelect(Links links,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model){
		
		Map<Object, Object> respondate=new HashMap<>();
		int result = linksService.updateByPrimaryKeySelective(links);
		respondate.put("message", "更新成功");
		//添加查询分页结果
        Pager<Link> linkList = linksService.selectLinkWithPagesizeFromFromindex(pageNum, pageSize);
        respondate.put("linkList", linkList);
        
        Object object=JSON.toJSON(respondate);
		return object;
	}
	
	@ResponseBody 
	@MappingDescription("友情链接分页查询")
    @RequestMapping("linkPage")
    public Object linkPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
		
		
        //添加查询分页结果
        Pager<Link> linkLists = linksService.selectLinkWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> linkList = new HashMap<String, Object>();
        linkList.put("linkList", linkLists);
        
        Object object = JSON.toJSON(linkList);
        return object;
    }
	
	
}
