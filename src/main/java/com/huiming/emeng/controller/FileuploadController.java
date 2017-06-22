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
	
	/**
	 * 照片文件上传
	 * @param request
	 * @param description
	 * @param file
	 * @return
	 * @throws Exception
	 */
   @RequestMapping("picupload")
   @MappingDescription("文件上传接口")
   @ResponseBody
   public Object upload(HttpServletRequest request,
		   @RequestParam("files") MultipartFile[] files)throws Exception
   {
	   
	   List respondate = new ArrayList();

	     respondate = fileuplaodservice.upload(request, files);
	      Object object = JSON.toJSON(respondate);
	   
	   
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
	@RequestMapping("download1")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			@RequestParam("filename") String filename,
			Model model)throws Exception
	{
		//下载文件路径
		String path = request.getServletContext().getRealPath("/images/");
		File file = new File(path+File.separator+filename);
		HttpHeaders headers = new HttpHeaders();
		//下载显示文件的头文件名，解决中文乱码问题
		String downfileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
		//通知浏览器一attachment的（下载方式）打开
		headers.setContentDispositionFormData("attachment", downfileName);
		//application/octet-stream二进制六数据（最常见的文件下载)
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//201 HttpStatus.CREATED
		
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
			String path = request.getServletContext().getRealPath("/meetings/");
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
			
			respondate.put("annex",path+str);
		}
		
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	
	
	
	
}
