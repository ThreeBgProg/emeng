package com.huiming.emeng.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huiming.emeng.annotation.MappingDescription;
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
	public String insert(HttpServletRequest request,
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
			annex.transferTo(new File(path+File.separator+fileName)); 
			meeting.setLink(path+fileName);
		}
		meeting.setReleaseDate(new Date());
		int result = meetingService.insert(meeting);
		
		return null;
	}
	
	@RequestMapping("meetinsertSel")
	@MappingDescription("添加会议信息")
	public String meetinginsertSelect(HttpServletRequest request,
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
			annex.transferTo(new File(path+File.separator+fileName)); 
			meeting.setLink(path+fileName);
		}
		int result = meetingService.insertSelective(meeting);
		
		return null;
	}
	
	@RequestMapping("meetdelPK")
	@MappingDescription("根据id删除会议信息")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		int result = meetingService.deleteByPrimaryKey(id);
		
		return null;
	}
	
	@RequestMapping("meetupdByPKS")
	@MappingDescription("根据id更新会议信息")
	public String updateByPrimaryKeySelective(Meeting meeting,Model model){
		
		int result = meetingService.updateByPrimaryKeySelective(meeting);
		
		return null;
	}
	
	@RequestMapping("meetupdByPKWB")
	public String updateByPrimaryKeyWithBLOBs(Meeting record,Model model){
		
		int result = meetingService.updateByPrimaryKeyWithBLOBs(record);
		
		return null;
	}
	
	@RequestMapping("meetupdByPK")
	@MappingDescription("根据id更新会议信息")
	public String updateByPrimaryKey(Meeting meeting ,Model model){
		
		int result = meetingService.updateByPrimaryKey(meeting);
		 
		return null;
	}
	
	@RequestMapping("meetSelByPK")
	@MappingDescription("根据id查找会议信息")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
//		Meeting meeting = meetingService.selectByPrimaryKey(id);
		
		return meetingService.selectByPrimaryKey(id);
	}
	
	@RequestMapping("meetsousuo")
	@MappingDescription("搜索会议")
	@ResponseBody
	public Object findMeeting(@RequestParam("sousuo") String sousuo,Model model){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "%"+sousuo+"%");
				
		return meetingService.findMeeting(map);
	}
	
}
