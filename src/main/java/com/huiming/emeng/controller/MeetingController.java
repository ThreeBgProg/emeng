package com.huiming.emeng.controller;

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

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.dto.StringToDate;
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
			@RequestParam("date")  String date,
			Meeting meeting) throws Exception{
		
			meeting.setReleaseDate(StringToDate.StringToDate(date));		    
			meetingService.insert(meeting);
			Map<String, String> respondate=new HashMap<>();
			respondate.put("message", "添加成功");
			
			Object object = JSON.toJSON(respondate);
			return object;
	}
	
	@RequestMapping("meetinsertSel")
	@MappingDescription("添加会议信息")
	@ResponseBody
	public Object meetinginsertSelect(HttpServletRequest request,
			@RequestParam(value="date",defaultValue="")  String date,
			Meeting meeting)throws Exception{
		
		if (date.equals("")) {
			meeting.setReleaseDate(new Date());
		}
		meeting.setReleaseDate(StringToDate.StringToDate(date));
		
		meetingService.insertSelective(meeting);
		
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
		
		meetingService.deleteByPrimaryKey(id);
		
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
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            @RequestParam(value="date",defaultValue="") String date) throws Exception{
		

		if (!date.equals("")) {
			meeting.setReleaseDate(StringToDate.StringToDate(date));
		}
		meetingService.updateByPrimaryKeySelective(meeting);
		
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
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            @RequestParam(value="date",defaultValue="") String date)throws Exception{

		
		if (!date.equals("")) {
			record.setReleaseDate(StringToDate.StringToDate(date));
		}
		meetingService.updateByPrimaryKeyWithBLOBs(record);
		
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
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            @RequestParam(value="date",defaultValue="") String date)throws Exception{

		if (!date.equals("")) {
			meeting.setReleaseDate(StringToDate.StringToDate(date));
		}
		meetingService.updateByPrimaryKey(meeting);
		 
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
