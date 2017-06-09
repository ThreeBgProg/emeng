package com.huiming.emeng.controller;

import java.util.List;

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
        model.addAttribute("navigationlists", navigationlists);
        return navigationlists;
    }
    
    @RequestMapping("navidelPK")
    @MappingDescription("删除")
    public String deleteByPrimaryKey(@RequestParam("id") Integer id,Navigation navigation
    		,Model model){
    	int result = navigationService.deleteByPrimaryKey(id);
    	System.out.println("成功删除"+result+"信息");
    	return null;
    }
    
    @RequestMapping("naviInsert")
    @MappingDescription("添加")
    public String  insert(Navigation record,Model model){
    	int result = navigationService.insert(record);
    	System.out.println("成功添加"+result+"信息");
    	return null;
    }
 
    @RequestMapping("naviInsertS")
    @MappingDescription("添加")
    public String insertSelective(Navigation record,Model model){
    	int result = navigationService.insertSelective(record);
    	System.out.println("成功添加"+result+"信息");
    	return null;
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
    public String updateByPrimaryKeySelective(Navigation record,Model model){
    	
    	int result =  navigationService.updateByPrimaryKeySelective(record);
    	System.out.println("成功更新"+result+"信息");
    	
    	return null;
    }

    @RequestMapping("naviupPK")
    @MappingDescription("根据id更新s")
    public String updateByPrimaryKey(Navigation record,Model model){
    	int result = navigationService.updateByPrimaryKey(record);
    	System.out.println("成功更新"+result+"信息");
    	return null;
    }
    
	
	
}
