package com.huiming.emeng.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.service.AdvertisementService;


@Controller 
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping("adverinsert")
	public String insert(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file)throws Exception{
		
		if(!file.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = file.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   file.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);

		}
		
		int result = advertisementService.insert(advertisement);
		System.out.println("插入啦"+result+"条广告");
		
		return null;
	}
	
	@RequestMapping("adverdelPK")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		int result = advertisementService.deleteByPrimaryKey(id);
		System.out.println("删除啦"+result+"条广告");
		return null;
	}
	
	@RequestMapping("adverinsertSel")
	public String insertSelective(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file)throws Exception{
		if(!file.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = file.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   file.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);

		}

		int result = advertisementService.insert(advertisement);
		System.out.println("插入啦"+result+"条广告");
		
		return null;
	}
	
	@RequestMapping("adverselectByPK")
	public String selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Advertisement advertisement = advertisementService.selectByPrimaryKey(id);
		model.addAttribute("advertisement", advertisement);
		
		return null;
	}
	
	@RequestMapping("adverupdateByPKS")
	public String updateByPrimaryKeySelective(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file)throws Exception{
		if(!file.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = file.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   file.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);

		}

		int result = advertisementService.updateByPrimaryKeySelective(advertisement);
		
		System.out.println("更新啦"+result+"条广告");
		return null;
	}
	
	@RequestMapping("adverupdateByPK")
	public String updateByPrimaryKey(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file)throws Exception{
		if(!file.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = file.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   file.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);
		}

		int result = advertisementService.updateByPrimaryKeySelective(advertisement);
		
		System.out.println("更新啦"+result+"条广告");
		return null;
	}
	
}
