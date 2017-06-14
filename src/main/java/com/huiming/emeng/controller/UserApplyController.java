package com.huiming.emeng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Apply;
import com.huiming.emeng.model.Meeting;
import com.huiming.emeng.model.User;
import com.huiming.emeng.service.ApplyService;
import com.huiming.emeng.service.MeetingService;

/**
 * 用户报名表操作
 * @author zhiwei
 *
 */
@Controller
public class UserApplyController {

	@Autowired
	private ApplyService applyService;
	@Autowired
	private MeetingService meetingService;
	

	@RequestMapping("userApplyformCode")
	@MappingDescription("后台生成报名表(邀请码方式报名)")
	@ResponseBody
	public Object userApplyFormCode(HttpServletRequest request,
			Apply apply,
			@RequestParam("meetingId") Integer meetingId,
			@RequestParam("code") String code){
		
		//获取报名的用户的id（邀请码报名默认0）
		//获取会议id
		    Meeting meeting = meetingService.selectByPrimaryKey(meetingId);
		    Map<String, String> respondate = new HashMap<>();
		    if (meeting.getCode().equals(code)) {
				apply.setUserId(0);
				apply.setMeetingId(meetingId);
				applyService.insert(apply);
				respondate.put("message", "报名成功");
			}else{
				respondate.put("message", "邀请码不正确,请重新填写");
			}

		return respondate;
	}
	
	@RequestMapping("userApplyform")
	@MappingDescription("后台生成报名表(普通方式报名)")
	@ResponseBody
	public Object userApplyForm(HttpServletRequest request,
			Apply apply,
			@RequestParam("meetingId") Integer meetingId){
		
			//获取报名的用户的id（邀请码报名默认0）
			//获取会议id
		    
			HttpSession session = request.getSession();
			User user =(User) session.getAttribute("user");
			apply.setUserId(user.getId());
			apply.setMeetingId(meetingId);
			applyService.insert(apply);
			Map<String, String> respondate = new HashMap<>();
			respondate.put("message", "报名成功");

		return respondate;
	}
	
	

	@RequestMapping("selectAllApply")
	@MappingDescription("管理员查看所有报名信息")
	@ResponseBody
	public Object selectAllApply(Model model){
		List<Apply> applylists=applyService.selectAllApply();
		Map<Object, Object> respondate = new HashMap<>();
		respondate.put("applylists", applylists);
		return respondate;
	}


	@RequestMapping("deleteByPrimaryKey")
	@MappingDescription("根据主键删除报名信息")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			 @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
             @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize
             ,Model model){
		
		int result = applyService.deleteByPrimaryKey(id);
		
		 //添加查询分页结果
        Pager<Apply> applyList = applyService.selectApplyWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> respondate = new HashMap<String, Object>();
        respondate.put("applyList", applyList);
        respondate.put("message", "删除成功");
        
        return respondate;
	}

	@RequestMapping("selectByPrimaryKey")
	@MappingDescription("根据id查找用户报名信息")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model) {
		
		Apply apply = applyService.selectByPrimaryKey(id);
		model.addAttribute("apply", apply);
		return apply;
	}
	
	
	@RequestMapping("upByPKS")
	@MappingDescription("根据user_id进行更新")
	@ResponseBody
	public Object updateByPrimaryKeySelective(Apply record,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize
            ,Model model) {
		
			int result = applyService.updateByPrimaryKeySelective(record);			
			 //添加查询分页结果
	        Pager<Apply> applyList = applyService.selectApplyWithPagesizeFromFromindex(pageNum, pageSize);

	        Map< String, Object> respondate = new HashMap<String, Object>();
	        respondate.put("applyList", applyList);
	        respondate.put("message", "更新成功");
	        return respondate;
					
	}
	
	@RequestMapping("upByPK")
	@MappingDescription("根据user_id进行更新")
	@ResponseBody
	public Object updateByPrimaryKey(Apply record,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize
            ,Model model) {
		
			int result = applyService.updateByPrimaryKey(record);
			 //添加查询分页结果
	        Pager<Apply> applyList = applyService.selectApplyWithPagesizeFromFromindex(pageNum, pageSize);

	        Map< String, Object> respondate = new HashMap<String, Object>();
	        respondate.put("applyList", applyList);
	        respondate.put("message", "更新成功");
	        return respondate;
		
	}
	
	@ResponseBody 
	@MappingDescription("报名表分页查询")
    @RequestMapping("applyPage")
    public Object advertisementPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
        //添加查询分页结果
        Pager<Apply> applyList = applyService.selectApplyWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> applyMap = new HashMap<String, Object>();
        applyMap.put("applyList", applyList);
        return applyMap;
    }

	
	
	
}
