package com.huiming.emeng.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Meeting;
import com.huiming.emeng.service.MeetingService;
/**
 * 会议模块
 * @author zhiwei
 *
 */
@Controller
public class MeetingController {

	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping("meetinsert")
	@MappingDescription("添加会议信息")
	@ResponseBody
	public Object insert(HttpServletRequest request,
			@RequestParam("annex") MultipartFile annex,
			Meeting meeting,
			Model model) throws Exception{
				
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
			meeting.setLink(path+str);
		}
		
//			meeting.setReleaseDate(new Date());
			int result = meetingService.insert(meeting);

			Map<String, String> respondate=new HashMap<>();
			respondate.put("message", "添加成功");
			
			Object object = JSON.toJSON(respondate);
			return object;
	}
	
	@RequestMapping("meetinsertSel")
	@MappingDescription("添加会议信息")
	@ResponseBody
	public Object meetinginsertSelect(HttpServletRequest request,
			@RequestParam("annex") MultipartFile annex,
			Meeting meeting,
			Model model)throws Exception{
		
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
			meeting.setLink(path+str);
		}
		int result = meetingService.insertSelective(meeting);
		
		Map<String, String> respondate=new HashMap<>();
		respondate.put("message", "添加成功");
		
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("meetdelPK")
	@MappingDescription("根据id删除会议信息")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			 @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
             @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
             Model model){
		
		int result = meetingService.deleteByPrimaryKey(id);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "成功删除一条信息");
		 //添加查询分页结果
        Pager<Meeting> meetingList= meetingService.selectMeetingWithPagesizeFromFromindex(pageNum, pageSize);

        respondate.put("meetingList", meetingList);
		
        Object object=JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("meetupdByPKS")
	@MappingDescription("根据id更新会议信息")
	@ResponseBody
	public Object updateByPrimaryKeySelective(Meeting meeting,
			HttpServletRequest request,
			@RequestParam("annex") MultipartFile annex,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model) throws Exception{
		
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
			meeting.setLink(path+str);
		}
		
		int result = meetingService.updateByPrimaryKeySelective(meeting);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "成功更新一条信息");
		 //添加查询分页结果
        Pager<Meeting> meetingList= meetingService.selectMeetingWithPagesizeFromFromindex(pageNum, pageSize);

        respondate.put("meetingList", meetingList);
		
        Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("meetupdByPKWB")
	@MappingDescription("根据id更新会议(包含会议内容）")
	@ResponseBody
	public Object updateByPrimaryKeyWithBLOBs(Meeting record,
			HttpServletRequest request,
			@RequestParam("annex") MultipartFile annex,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model)throws Exception{
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
			record.setLink(path+str);
		}
		int result = meetingService.updateByPrimaryKeyWithBLOBs(record);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "成功更新一条信息");
		 //添加查询分页结果
        Pager<Meeting> meetingList= meetingService.selectMeetingWithPagesizeFromFromindex(pageNum, pageSize);

        respondate.put("meetingList", meetingList);
		
        Object object=JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("meetupdByPK")
	@MappingDescription("根据id更新会议信息(不包含会议内容）")
	@ResponseBody
	public Object updateByPrimaryKey(Meeting meeting ,
			HttpServletRequest request,
			@RequestParam("annex") MultipartFile annex,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model)throws Exception{
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
			meeting.setLink(path+str);
		}
		int result = meetingService.updateByPrimaryKey(meeting);
		 
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "成功更新一条信息");
		 //添加查询分页结果
        Pager<Meeting> meetingList= meetingService.selectMeetingWithPagesizeFromFromindex(pageNum, pageSize);

        respondate.put("meetingList", meetingList);
		
		return respondate;
	}
	
	@RequestMapping("meetSelByPK")
	@MappingDescription("根据id查找会议信息")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Meeting meeting = meetingService.selectByPrimaryKey(id);
		
		return meeting;
	}
	
	@RequestMapping("meetsousuo")
	@MappingDescription("搜索会议")
	@ResponseBody
	public Object findMeeting(@RequestParam("sousuo") String sousuo,Model model){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "%"+sousuo+"%");
				
		return meetingService.findMeeting(map);
	}
	
	@ResponseBody 
	@MappingDescription("会议分页查询")
    @RequestMapping("meetingPage")
    public Object meetingPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
        //添加查询分页结果
        Pager<Meeting> meetingList= meetingService.selectMeetingWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> respondate = new HashMap<String, Object>();
        respondate.put("meetingList", meetingList);
        return respondate;
    }
	
}
