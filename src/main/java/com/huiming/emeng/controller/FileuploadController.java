package com.huiming.emeng.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileuploadController {
	
	
	/**
	 * 文件上传
	 * @param request
	 * @param description
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upload1")
   public String upload(HttpServletRequest request,
		   @RequestParam("description") String description,
		   @RequestParam("file") MultipartFile file)throws Exception
   {
	   System.out.println(description);
	   //如果文件不为空，写入上传路劲
	   if(!file.isEmpty()){
		   //上传文件路劲
		   String path = request.getServletContext().getRealPath("/images/");
		   System.out.println("path:"+path);
		   String fileName = file.getOriginalFilename();
		   
		   request.setAttribute("filename", fileName);
		   
		   System.out.println("fileName:"+fileName);
		   File filepath = new File(path,fileName);
		   System.out.println("filepath:"+filepath);
		   //判断路劲是否存在，如果不存在就新建一个
		   if(!filepath.getParentFile().exists()){
			   filepath.getParentFile().mkdirs();
		   }
		   //将文件存到一个目标文件当中
		   file.transferTo(new File(path+File.separator+fileName));

		   return "userInfo";
	   }
	   return "error";
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
	
	
}
