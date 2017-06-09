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

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;
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
	public String insert(Links links,Model model){
		for(int i=0;i<30;i++){
			links.setLink("nihao"+i);
			links.setName("黄慧"+i);
			links.setOrder(i);
			int result = linksService.insert(links);
			System.out.println("您成功插入"+result+"条友情链接");
		}
	
		return null;
	}
	
	@RequestMapping("linksinsertSelect")
	@MappingDescription("插入链接")
	public String insertSelect(Links links,Model model){
		
		int result = linksService.insertSelective(links);
		System.out.println("您成功插入"+result+"条友情链接");
		
		return null;
	}
	
	@RequestMapping("linksselectPK")
	@MappingDescription("根据id查找查找链接")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Links links = linksService.selectByPrimaryKey(id);
		model.addAttribute("links", links);
		return links;
	}
	
	@RequestMapping("linksdeletePK")
	@MappingDescription("根据id删除链接")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		int result = linksService.deleteByPrimaryKey(id);
		System.out.println("您成功删除"+result+"条友情链接");
		return null;
		
	}
	
	@RequestMapping("linksupdPK")
	@MappingDescription("更新链接")
	public String updateByPrimaryKey(Links links,Model model){
		
		int result = linksService.updateByPrimaryKey(links);
		System.out.println("您成功更新"+result+"条友情链接");
		return null;
	}
	
	@RequestMapping("linksupdPKSelect")
	@MappingDescription("更新链接")
	public String updateByPrimaryKeySelect(Links links,Model model){
		
		int result = linksService.updateByPrimaryKeySelective(links);
		System.out.println("您成功更新"+result+"条友情链接");
		return null;
	}
	
	@ResponseBody 
	@MappingDescription("友情链接分页查询")
    @RequestMapping("linkPage")
    public Object linkPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
		
		
        //添加查询分页结果
        Pager<Link> linkList = linksService.selectLinkWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> linkMap = new HashMap<String, Object>();
        linkMap.put("linkList", linkList);
        return linkMap;
    }
	
	
}
