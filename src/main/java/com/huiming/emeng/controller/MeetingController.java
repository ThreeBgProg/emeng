package com.huiming.emeng.controller;

import java.io.File;
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

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;
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
			annex.transferTo(new File(path+File.separator+fileName)); 
			meeting.setLink(path+fileName);
		}
		
//		for(int i=0;i<30;i++){
//			meeting.setCode(""+i+i+i+i);
//			meeting.setTitle("志伟"+i);
//			meeting.setContent("黄慧"+i);
//			meeting.setLink("link"+i);
//			
			meeting.setReleaseDate(new Date());//
			int result = meetingService.insert(meeting);//
//		}
		

		
			Map<String, String> meetingmap=new HashMap<>();
			meetingmap.put("success", "添加成功");
			
			return meetingmap;
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
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		int result = meetingService.deleteByPrimaryKey(id);
		
		Map<String, String> meetingmap=new HashMap<>();
		meetingmap.put("success", "成功删除一条信息");
		
		return meetingmap;
	}
	
	@RequestMapping("meetupdByPKS")
	@MappingDescription("根据id更新会议信息")
	@ResponseBody
	public Object updateByPrimaryKeySelective(Meeting meeting,Model model){
		
		int result = meetingService.updateByPrimaryKeySelective(meeting);
		
		Map<String, String> meetingmap=new HashMap<>();
		meetingmap.put("success", "成功更新一条信息");
		
		return meetingmap;
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

        Map< String, Object> meetingMap = new HashMap<String, Object>();
        meetingMap.put("meetingList", meetingList);
        return meetingList;
    }
	
}
