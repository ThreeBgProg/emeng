package com.huiming.emeng.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.serviceImpl.FileuploadServiceImpl;

@Controller
public class FileuploadController {
	
	@Autowired
	private FileuploadServiceImpl fileuplaodservice;
	
	@Autowired
	private FileuploadServiceImpl fileuploadServiceImpl;
	/**
	 * 照片文件上传
	 * @param request
	 * @param description
	 * @param file
	 * @return
	 * @throws Exception
	 */
   @SuppressWarnings("rawtypes")
@RequestMapping("picupload")
   @MappingDescription("文件上传接口")
   @ResponseBody
   public Object upload(HttpServletRequest request,
		   @RequestParam("file") MultipartFile[] files)throws Exception
   {
	   
	    List src = new ArrayList();

	     src = fileuplaodservice.upload(request, files);
	     Map<Object, Object>  map = new HashMap<>();
	     Map<Object, Object>  map1 = new HashMap<>();
	     map1.put("src", src.get(0));
	     map.put("data", map1);
	     map.put("code", "0");
	     map.put("msg", "上传成功");
	     Object object = JSON.toJSON(map);
	   return object;
   }
	/**
	 * 文件下载
	 * @param request
	 * @param filename
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("filedownload")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			@RequestParam("fileUrl") String filename,
			Model model)throws Exception
	{
		String[] string=filename.split("\\"+File.separator);
		int num=string.length;
	    if (string[num-2].equals("")) {
	    	string[num-2]=string[num-3];
			}
		System.out.println("dier"+string[num-2]);
		
		String path = request.getServletContext().getRealPath(File.separator);
		System.out.println("项目路劲"+path);
		System.out.println(path+File.separator+string[num-2]+File.separator+string[num-1]);
		
		File file = new File(path+File.separator+string[num-2]+File.separator+string[num-1]);	
		HttpHeaders headers = new HttpHeaders();
		String downfileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", downfileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		 return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                headers, HttpStatus.CREATED);
	}
	
	@RequestMapping("meetingupload")
	@MappingDescription("添加会议附件")
	@ResponseBody
	public Object insert(HttpServletRequest request,
			@RequestParam("annex1") MultipartFile annex) throws Exception{
				
		Map<String, String> respondate=new HashMap<>();
		
		if(!annex.isEmpty()){
			String path = request.getServletContext().getRealPath(File.separator+"meetings"+File.separator);
			String fileName=annex.getOriginalFilename();
			File filepath = new File(path, fileName);
			if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			@SuppressWarnings("deprecation")
			long str2 = Date.parse((new Date()).toString());
			String[] fStrings = fileName.split("\\.");   
			   String str = fStrings[0]+str2+"."+fStrings[1];
			   
			annex.transferTo(new File(path+File.separator+str)); 
			String root = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
			System.out.println("端口号获取"+root);
			respondate.put("annex",root+File.separator+"emeng"+File.separator+"meeting"+File.separator+str);
		}
		
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	   @RequestMapping("addVideoLink")
	   @MappingDescription("视频上传")
	   @ResponseBody
	   public Object addVideoLink(HttpServletRequest request,
			   @RequestParam("linklink") MultipartFile link)throws Exception
	   {   
		   if(!link.isEmpty()){
		       Map<String, String> respondate=new HashMap<>();
			   respondate.put("linkName",fileuploadServiceImpl.addVideoLink(request, link).get("linkName"));
			   respondate.put("link",fileuploadServiceImpl.addVideoLink(request, link).get("link"));
		       return respondate;
		   }
		    return null;
		  

	   }

	   @RequestMapping("addpic")
	   @MappingDescription("视频照片上传")
	   @ResponseBody
	   public Object addVideopic(HttpServletRequest request,
			   @RequestParam("picpic") MultipartFile pic)throws Exception
	   {   
		   if(!pic.isEmpty()){
			 Map< String, String> respondate = new HashMap<>();
			   respondate.put("pic",fileuploadServiceImpl.addVideoPic(request, pic));
			   return respondate;
		   }
		  return null;

	   }
	
	
}
