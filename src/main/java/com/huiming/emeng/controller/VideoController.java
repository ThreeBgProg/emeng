package com.huiming.emeng.controller;

import java.io.File;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Chapter;
import com.huiming.emeng.model.Lesson;
import com.huiming.emeng.model.Video;
import com.huiming.emeng.service.ChapterService;
import com.huiming.emeng.service.LessonService;
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
	
	@Autowired
	private LessonService LessonService;
	
	@Autowired
	private ChapterService chapterService;
	
	
	@RequestMapping("addvideo")
	@MappingDescription("进入视频管理添加页面")
	@ResponseBody
	public Object fileUpload(Model model){
		
		List<Lesson> lessionlist= LessonService.selectAllLesson();
        Map<Object, Object> respondate = new HashMap<>();
        respondate.put("lessionlist", lessionlist);
		return respondate;
	}
	
	@RequestMapping("selectlession")
	@MappingDescription("选择课程之后，加载该课程的所有章节")
	@ResponseBody
     public Object selectAllChapterFromLesson(Integer lessonId){
		List<Chapter> chapters = chapterService.selectAllChapterFromLesson(lessonId);
		Map<Object, Object> respondate = new HashMap<>();
        respondate.put("chapters", chapters);
		return respondate;
	}
	
	
   @RequestMapping("videoinsert")
   @MappingDescription("视频上传")
   @ResponseBody
   public Object videoInsert(HttpServletRequest request,
		   @RequestParam("link") MultipartFile link,
		   @RequestParam("pic") MultipartFile pic,
		   Video video,
		   Model model)throws Exception
   {
	   if(!link.isEmpty()){
		   String path = request.getServletContext().getRealPath("/videos/");
		   String linkName = link.getOriginalFilename();
		   File linkpath = new File(path,linkName);
		   if(!linkpath.getParentFile().exists()){
			   linkpath.getParentFile().mkdirs();
		   }
		   link.transferTo(new File(path+File.separator+linkName));

		   video.setName(linkName);
		   video.setLink(path+linkName);
	   }
	   
	   if(!pic.isEmpty()){
		   String path = request.getServletContext().getRealPath("/images/");
		   String picName = pic.getOriginalFilename();
		   File picpath = new File(path,picName);
		   if(!picpath.getParentFile().exists()){
			   picpath.getParentFile().mkdirs();
		   }
		   link.transferTo(new File(path+File.separator+picName));
		   video.setPic(path+picName);
	   }
	  
	   int result = videoService.insert(video);
	  
	   Map<Object, Object> respondate = new HashMap<>();
       respondate.put("message", "添加成功");
	   return respondate;

   }

	@RequestMapping("videodownload")
	@MappingDescription("视频下载")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			@RequestParam("filename") String filename,
			Model model)throws Exception
	{
		String path = request.getServletContext().getRealPath("/videos/");
		File file = new File(path+File.separator+filename);
		HttpHeaders headers = new HttpHeaders();
		String downfileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", downfileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		 return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                headers, HttpStatus.CREATED);
	}

	@RequestMapping("videoselectPK")
	@MappingDescription("根据主键查找视频")
	@ResponseBody
	public Object selectByPrimaryKey(HttpServletRequest request,
			@RequestParam("id") Integer id
             )throws Exception{
		Video video = videoService.selectByPrimaryKey(id);
		
		return video;
	}

	@RequestMapping("videoupdByPK")
	@MappingDescription("全部字段更新")
	@ResponseBody
	public Object updateByPrimaryKey(Video video,
			 @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
             @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
             Model model){
		
		int result = videoService.updateByPrimaryKey(video);
		  //添加查询分页结果
        Pager<Video> videoList = videoService.selectVideoWithPagesizeFromFromindex(pageNum, pageSize);

        Map<Object, Object> respondate = new HashMap<>();
        respondate.put("message", "添加成功");
        respondate.put("videoList", videoList);
 	   return respondate;
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
	@ResponseBody
	public Object selectBylesson(@RequestParam("lesson") Integer lesson,Model model){
		
		List<Video> videoList = videoService.selectBylesson(lesson);
		
		Map<Object, Object> respondate = new HashMap<>();
        respondate.put("videoList", videoList);
 	   return respondate;
		}
	
	@RequestMapping("videoselectBycha")
	@MappingDescription("根据章节id查找所有")
	@ResponseBody
	public Object selectBychapter(@RequestParam("chapter") Integer chapter,Model model){
		
		List<Video> videoList = videoService.selectBylesson(chapter);
		
		Map<Object, Object> respondate = new HashMap<>();
        respondate.put("videoList", videoList);
 	   return respondate;
	}
	
	@RequestMapping("videodelByPK")
	@MappingDescription("根据id删除视频")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model){
		
		int result = videoService.deleteByPrimaryKey(id);
		  //添加查询分页结果
        Pager<Video> videoList = videoService.selectVideoWithPagesizeFromFromindex(pageNum, pageSize);

        Map<Object, Object> respondate = new HashMap<>();
        respondate.put("message", "添加成功");
        respondate.put("videoList", videoList);
 	   return respondate;
	}	
	
	@ResponseBody 
	@MappingDescription("视频资源分页查询")
    @RequestMapping("videoPage")
    public Object advertisementPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
		  //添加查询分页结果
        Pager<Video> videoList = videoService.selectVideoWithPagesizeFromFromindex(pageNum, pageSize);

        Map<Object, Object> respondate = new HashMap<>();
        respondate.put("message", "添加成功");
        respondate.put("videoList", videoList);
 	   return respondate;
    }
	
  
	
}
