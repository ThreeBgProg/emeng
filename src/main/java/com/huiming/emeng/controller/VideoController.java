package com.huiming.emeng.controller;

import java.io.File;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Video;
import com.huiming.emeng.service.VideoService;

/**
 * 视屏处理
 * @author zhiwei
 *
 */
@Controller
public class VideoController {

	@Autowired
	private VideoService videoService;
	
	
	@RequestMapping("videofileupload")
	public String fileUpload(){
		System.out.println("进入");
		return "fileupload";
	}
	
	
   @RequestMapping("videoupload")
   @MappingDescription("视频上传")
   public String upload(HttpServletRequest request,
		   @RequestParam("description") String description,
		   @RequestParam("file") MultipartFile file)throws Exception
   {
	   System.out.println(description);
	   if(!file.isEmpty()){
		   String path = request.getServletContext().getRealPath("/videos/");
		   String fileName = file.getOriginalFilename();
		   File filepath = new File(path,fileName);
		   if(!filepath.getParentFile().exists()){
			   filepath.getParentFile().mkdirs();
		   }
		   file.transferTo(new File(path+File.separator+fileName));
		   Video video = new Video();
		   video.setName(fileName);
		   video.setLink(path+fileName);
		   video.setLesson(1);
		   video.setChapter(1);
		   video.setPic("picture");
		   int result = videoService.insert(video);
		   System.out.println(result);
		   return "userInfo";
	   }
	   return "error";
   }

	@RequestMapping("videodownload")
	@MappingDescription("视频下载")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			@RequestParam("filename") String filename,
			Model model)throws Exception
	{
		String path = request.getServletContext().getRealPath("/images/");
		File file = new File(path+File.separator+filename);
		HttpHeaders headers = new HttpHeaders();
		String downfileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", downfileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		 return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                headers, HttpStatus.CREATED);
	}

	@RequestMapping("selectvideo")
	@MappingDescription("根据主键查找id删除视频")
	public String selectByPrimaryKey(HttpServletRequest request,
			@RequestParam("id") Integer id,Model model)throws Exception{
		Video video = videoService.selectByPrimaryKey(id);
		model.addAttribute("video", video);
		System.out.println(video);
		return "userInfo";
	}

	@RequestMapping("videoupdByPK")
	@MappingDescription("全部字段更新")
	public String updateByPrimaryKey(Video video,Model model){
		
		int result = videoService.updateByPrimaryKey(video);
		System.out.println("您更新了"+result+"条视频数据");
		return null;
	}

	@RequestMapping("videoupdByPKS")
	@MappingDescription("选择字段更新")
	public String updateByPrimaryKeySelective(Video video ,Model model){
		int result = videoService.updateByPrimaryKeySelective(video);
		System.out.println("您更新了"+result+"条视频数据");
		return null;
	}
	

	@RequestMapping("videoselectByle")
	@MappingDescription("根据课程id查找所有")
	public String selectBylesson(@RequestParam("lesson") Integer lesson,Model model){
		
		List<Video> lists = videoService.selectBylesson(lesson);
		model.addAttribute("lists", lists);
		
		return null;
	}
	

	@RequestMapping("videoselectBycha")
	@MappingDescription("根据章节id查找所有")
	public String selectBychapter(@RequestParam("lesson") Integer chapter,Model model){
		
		List<Video> lists = videoService.selectBylesson(chapter);
		model.addAttribute("lists", lists);
		
		return null;
	}
	
	@RequestMapping("videodelByPK")
	@MappingDescription("根据id删除视频")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		int result = videoService.deleteByPrimaryKey(id);
		System.out.println("您已成功删除"+result+"条视频数据");
		return null;
	}
	
   @RequestMapping("videoinsSelect")
   @MappingDescription("添加视频")
   public String insertSelect(HttpServletRequest request,
		   @RequestParam("description") String description,
		   @RequestParam("file") MultipartFile file)throws Exception
   {
	   System.out.println(description);
	   if(!file.isEmpty()){
		   String path = request.getServletContext().getRealPath("/images/");
		   System.out.println("path:"+path);
		   String fileName = file.getOriginalFilename();
		   System.out.println("fileName:"+fileName);
		   File filepath = new File(path,fileName);
		   System.out.println("filepath:"+filepath);
		   if(!filepath.getParentFile().exists()){
			   filepath.getParentFile().mkdirs();
		   }
		   file.transferTo(new File(path+File.separator+fileName));
		   Video video = new Video();
		   video.setName(fileName);
		   video.setLink(path+fileName);
		   video.setLesson(1);
		   video.setChapter(1);
		   video.setPic("picture");
		   int result = videoService.insertSelective(video);
		   System.out.println(result);
		   return "userInfo";
	   }
	   return "error";
   }
    
	
	
}
