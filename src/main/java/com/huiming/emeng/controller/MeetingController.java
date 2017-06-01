package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String insert(Meeting meeting,Model model){
		
		int result = meetingService.insert(meeting);
		
		return null;
	}
	
	@RequestMapping("meetinsertSel")
	@MappingDescription("添加会议信息")
	public String meetinginsertSelect(Meeting meeting,Model model){
		
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
	public String selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Meeting meeting = meetingService.selectByPrimaryKey(id);
		
		return null;
	}
	
}
