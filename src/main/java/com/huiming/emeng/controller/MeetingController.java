package com.huiming.emeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huiming.emeng.model.Meeting;
import com.huiming.emeng.service.MeetingService;

@Controller
public class MeetingController {

	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping("meetinsert")
	public String insert(Meeting meeting,Model model){
		
		int result = meetingService.insert(meeting);
		
		return null;
	}
	
	@RequestMapping("meetinsertSel")
	public String meetinginsertSelect(Meeting meeting,Model model){
		
		int result = meetingService.insertSelective(meeting);
		
		return null;
	}
	
	@RequestMapping("meetdelPK")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		int result = meetingService.deleteByPrimaryKey(id);
		
		return null;
	}
	
	@RequestMapping("meetupdByPKS")
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
	public String updateByPrimaryKey(Meeting meeting ,Model model){
		
		int result = meetingService.updateByPrimaryKey(meeting);
		 
		return null;
	}
	
	@RequestMapping("meetSelByPK")
	public String selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Meeting meeting = meetingService.selectByPrimaryKey(id);
		
		return null;
	}
	
}
