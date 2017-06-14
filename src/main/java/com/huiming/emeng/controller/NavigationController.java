package com.huiming.emeng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Navigation;
import com.huiming.emeng.service.NavigationService;

@Controller
public class NavigationController {

	@Autowired
	private NavigationService navigationService;
	
	
	
	@RequestMapping("naviselect")
	@ResponseBody
	@MappingDescription("查找所有二级菜单")
	public Object selectAllNavigation(Navigation navigation,Model model){
        List<Navigation> navigationlists = navigationService.selectAllNavigation();
        return navigationlists;
    }
    
    @RequestMapping("navidelPK")
    @MappingDescription("删除")
    @ResponseBody
    public Object deleteByPrimaryKey(@RequestParam("id") Integer id,Navigation navigation
    		,Model model){
    	int result = navigationService.deleteByPrimaryKey(id);
    	List<Navigation> navigationlists = navigationService.selectAllNavigation();
        return navigationlists;
    }
    
    @RequestMapping("naviInsert")
    @MappingDescription("添加")
    @ResponseBody
    public Object  insert(Navigation record,Model model){
    	int result = navigationService.insert(record);
    	Map<String, String> respondate = new HashMap<String, String>();
    	respondate.put("message", "添加成功");
    	return respondate;
    }
 
    @RequestMapping("naviInsertS")
    @MappingDescription("添加")
    @ResponseBody
    public Object insertSelective(Navigation record,Model model){
    	int result = navigationService.insertSelective(record);
    	Map<String, String> respondate = new HashMap<String, String>();
    	respondate.put("message", "添加成功");
    	return respondate;
    }

    @RequestMapping("naviselPK")
    @ResponseBody
    @MappingDescription("根据id查找")
    public Object selectByPrimaryKey(@RequestParam("id") Integer id,Navigation navigation
    		,Model model){
    	
    	navigation = navigationService.selectByPrimaryKey(id);
    	model.addAttribute("navigation", navigation);
    	return navigation;
    }

    @RequestMapping("naviupdatePKS")
    @MappingDescription("根据id更新")
    @ResponseBody
    public Object updateByPrimaryKeySelective(Navigation record,Model model){
    	
    	int result =  navigationService.updateByPrimaryKeySelective(record);
    	List<Navigation> navigationlists = navigationService.selectAllNavigation();
        return navigationlists;
    }

    @RequestMapping("naviupPK")
    @MappingDescription("根据id更新")
    @ResponseBody
    public Object updateByPrimaryKey(Navigation record,Model model){
    	int result = navigationService.updateByPrimaryKey(record);
    	List<Navigation> navigationlists = navigationService.selectAllNavigation();
        return navigationlists;
    }
    
	
	
}
