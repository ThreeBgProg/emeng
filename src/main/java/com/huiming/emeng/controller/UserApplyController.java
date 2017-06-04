package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Apply;
import com.huiming.emeng.service.ApplyService;

/**
 * 用户报名表操作
 * @author zhiwei
 *
 */
@Controller
public class UserApplyController {

	@Autowired
	private ApplyService applyService;

	@RequestMapping("userApplyform")
	@MappingDescription("后台生成报名表")
	public String userApplyForm(Apply apply){
		
		//获取报名的用户的id（邀请码报名默认0）
		//获取会议id
	
		applyService.insert(apply);
		return "userApplyForm";
	}
	

	@RequestMapping("selectAllApply")
	@MappingDescription("管理员查看所有报名信息")
	public String selectAllApply(Model model){
		List<Apply> lists=applyService.selectAllApply();
		model.addAttribute("lists", lists);
		return null;
	}


	@RequestMapping("deleteByPrimaryKey")
	@MappingDescription("根据主键删除报名信息")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id,Model model){
		int result = applyService.deleteByPrimaryKey(id);
		System.out.println("您已成功删除"+result+"条信息");
		
		List<Apply> lists=applyService.selectAllApply();
		model.addAttribute("lists", lists);
		return null;
	}

	@RequestMapping("selectByPrimaryKey")
	@MappingDescription("根据id查找用户报名信息")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model) {
		
		Apply apply = applyService.selectByPrimaryKey(id);
		model.addAttribute("apply", apply);
		return apply;
	}
	
	
	@RequestMapping("updateByPrimaryKeySelective")
	@MappingDescription("根据user_id进行更新")
	public String updateByPrimaryKeySelective(Apply record,Model model) {
		
			int result = applyService.updateByPrimaryKeySelective(record);
			System.out.println("您已经成功更新"+result+"条数据");
			//查询查找新的数据
			List<Apply> lists=applyService.selectAllApply();
			model.addAttribute("lists", lists);
		return null;
		
	}
	
	@RequestMapping("updateByPrimaryKey")
	@MappingDescription("根据user_id进行更新")
	public String updateByPrimaryKey(Apply record,Model model) {
		
			int result = applyService.updateByPrimaryKey(record);
			System.out.println("您已经成功更新"+result+"条数据");
			//查询查找新的数据
			List<Apply> lists=applyService.selectAllApply();
			model.addAttribute("lists", lists);
		return null;
		
	}

	
	
	
}
