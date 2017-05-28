package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huiming.emeng.model.Apply;
import com.huiming.emeng.service.UserService;

/**
 * 用户操作
 * @author zhiwei
 *
 */
@Controller
public class UserApplyController {

	@Autowired
	private UserService userService;

	/**
	 * 后台生成报名表
	 * @param apply
	 * @return
	 */
	@RequestMapping("userApplyform")
	public String userApplyForm(Apply apply){
		
		userService.insert(apply);
		return "userApplyForm";
	}
	
	/**
	 * 管理员查看所有报名信息
	 * @param model
	 * @return
	 */
	@RequestMapping("selectAllApply")
	public String selectAllApply(Model model){
		List<Apply> lists=userService.selectAllApply();
		model.addAttribute("lists", lists);
		return null;
	}

	/**
	 * 根据主键删除报名信息
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteByPrimaryKey")
	public String deleteByPrimaryKey(@RequestParam("id") Integer id){
		int result = userService.deleteByPrimaryKey(id);
		System.out.println("您已成功删除"+result+"条信息");
		return null;
	}
	
}
