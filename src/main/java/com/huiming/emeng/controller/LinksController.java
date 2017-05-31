package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import com.huiming.emeng.model.Links;
import com.huiming.emeng.service.LinksService;

@Controller
public class LinksController {

	@Autowired
	private LinksService linksService;
	
	@RequestMapping("linksinsert")
	public String insert(Links links,Model model){
		
		int result = linksService.insert(links);
		System.out.println("您成功插入"+result+"条友情链接");
		
		return null;
	}
	
	@RequestMapping("linksinsertSelect")
	public String insertSelect(Links links,Model model){
		
		int result = linksService.insertSelective(links);
		System.out.println("您成功插入"+result+"条友情链接");
		
		return null;
	}
	
	@RequestMapping("linksselectPK")
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Links links = linksService.selectByPrimaryKey(id);
		model.addAttribute("links", links);
		return links;
	}
	
	@RequestMapping("linksdeletePK")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		int result = linksService.deleteByPrimaryKey(id);
		System.out.println("您成功删除"+result+"条友情链接");
		return null;
		
	}
	
	@RequestMapping("linksupdPK")
	public String updateByPrimaryKey(Links links,Model model){
		
		int result = linksService.updateByPrimaryKey(links);
		System.out.println("您成功更新"+result+"条友情链接");
		return null;
	}
	
	@RequestMapping("linksupdPKSelect")
	public String updateByPrimaryKeySelect(Links links,Model model){
		
		int result = linksService.updateByPrimaryKeySelective(links);
		System.out.println("您成功更新"+result+"条友情链接");
		return null;
	}
	
	
}
