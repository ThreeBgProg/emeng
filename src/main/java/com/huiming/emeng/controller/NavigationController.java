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
	
	/*以下是分页的版本
	@RequestMapping("naviselect")
	@ResponseBody
	@MappingDescription("查找所有二级菜单")
	public Object selectAllNavigation(Navigation navigation,Model model){
        List<Navigation> navigationlists = navigationService.selectAllNavigation();
        Map<String, Object> respondate = new HashMap<String, Object>();
        respondate.put("navigationlists", navigationlists);
        return respondate;
    }
    
    @RequestMapping("navidelPK")
    @MappingDescription("删除")
    @ResponseBody
    public Object deleteByPrimaryKey(@RequestParam("id") Integer id,Navigation navigation
    		,@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
    	navigationService.deleteByPrimaryKey(id);
        Pager<Navigation> navigationLists = navigationService.selectnavigationMapperWithPagesizeFromFromindex(pageNum, pageSize);
    	Map<String, Object> respondate = new HashMap<String, Object>();
    	respondate.put("message", "删除成功");
    	respondate.put("navigationLists", navigationLists);
    	return respondate;
    }
    
    @RequestMapping("naviInsert")
    @MappingDescription("添加")
    @ResponseBody
    public Object  insert(Navigation record,
    		@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
    	navigationService.insert(record);
    	Map<String, Object> respondate = new HashMap<String, Object>();
        Pager<Navigation> navigationLists = navigationService.selectnavigationMapperWithPagesizeFromFromindex(pageNum, pageSize);

    	respondate.put("message", "添加成功");
    	respondate.put("navigationLists", navigationLists);
    	return respondate;
    }
 
    @RequestMapping("naviInsertS")
    @MappingDescription("添加")
    @ResponseBody
    public Object insertSelective(Navigation record,
    		@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
    	navigationService.insertSelective(record);
    	Map<String, Object> respondate = new HashMap<String, Object>();
    	
        Pager<Navigation> navigationLists = navigationService.selectnavigationMapperWithPagesizeFromFromindex(pageNum, pageSize);
    	respondate.put("message", "添加成功");
    	respondate.put("navigationLists", navigationLists);
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
    public Object updateByPrimaryKeySelective(Navigation record,
    		@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
    	
    	navigationService.updateByPrimaryKeySelective(record);
        Pager<Navigation> navigationLists = navigationService.selectnavigationMapperWithPagesizeFromFromindex(pageNum, pageSize);
    	Map<String, Object> respondate = new HashMap<String, Object>();
    	respondate.put("message", "更新成功");
    	respondate.put("navigationLists", navigationLists);
    	return respondate;
    }

    @RequestMapping("naviupPK")
    @MappingDescription("根据id更新")
    @ResponseBody
    public Object updateByPrimaryKey(Navigation record,
    		@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
    	navigationService.updateByPrimaryKey(record);
        Pager<Navigation> navigationLists = navigationService.selectnavigationMapperWithPagesizeFromFromindex(pageNum, pageSize);
    	Map<String, Object> respondate = new HashMap<String, Object>();
    	respondate.put("message", "更新成功");
    	respondate.put("navigationLists", navigationLists);
    	return respondate;
    }
    
	@ResponseBody 
	@MappingDescription("友情链接分页查询")
    @RequestMapping("navigationPage")
    public Object linkPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize){
		
        //添加查询分页结果
        Pager<Navigation> navigationLists = navigationService.selectnavigationMapperWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> respondata = new HashMap<String, Object>();
        respondata.put("navigationLists", navigationLists);
        
        Object object = JSON.toJSON(respondata);
        return object;
    }
	*/
	
}
