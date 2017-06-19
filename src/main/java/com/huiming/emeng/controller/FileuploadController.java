package com.huiming.emeng.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable;
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

import com.huiming.emeng.annotation.MappingDescription;

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
   @RequestMapping("picupload")
   @MappingDescription("文件上传接口")
   @ResponseBody
   public Object upload(HttpServletRequest request,
		   @RequestParam("files") MultipartFile[] files)throws Exception
   {
	   

	   List respondate = new ArrayList();

	   if(files.length>0){
		   
		   
		   for(int i=0;i<files.length;i++){
			   String path = request.getServletContext().getRealPath("/wangEditor_images/");
			 //如果文件不为空，写入上传路劲
			   if(!files[i].isEmpty()){
				   //上传文件路劲
				   System.out.println("path:"+path);
				   String fileName = files[i].getOriginalFilename();
				   
				   request.setAttribute("filename", fileName);
				   
				   System.out.println("fileName:"+fileName);
				   File filepath = new File(path,fileName);
				   System.out.println("filepath:"+filepath);
				   //判断路劲是否存在，如果不存在就新建一个
				   if(!filepath.getParentFile().exists()){
					   filepath.getParentFile().mkdirs();
				   }
				   //将文件存到一个目标文件当中
				   
				   long str2 = Date.parse((new Date()).toString());
				   
				   String[] fStrings = fileName.split("\\.");
				   
				   String str = fStrings[0]+str2+"."+fStrings[1];
				   
				   files[i].transferTo(new File(path+File.separator+str));
				   System.out.println(str);
				   respondate.add(i, path+str);
			   }
		   }
	   }
	   
	      
	   return respondate;
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
