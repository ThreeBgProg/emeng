package com.huiming.emeng.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.service.AdvertisementService;


@Controller 
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping("adverinsert")
	@MappingDescription("添加广告")
	@ResponseBody
	public Object insert(Advertisement advertisement)throws Exception{
		
		advertisementService.insert(advertisement);
		Map<String, String> respondate=new HashMap<>();
		respondate.put("message", "添加成功");
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("adverdelPK")
	@MappingDescription("根据id删除广告")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		advertisementService.deleteByPrimaryKey(id);
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "删除成功");
		 //添加查询分页结果
        Pager<Advertisement> advertisement = advertisementService.selectAdvertisementWithPagesizeFromFromindex(pageNum, pageSize);
        respondate.put("advertisementList", advertisement);
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("adverinsertSel")
	@MappingDescription("添加广告")
	@ResponseBody
	public Object insertSelective(Advertisement advertisement)throws Exception{
		advertisementService.insert(advertisement);
		Map<String, String> respondate=new HashMap<>();
		respondate.put("message", "添加成功");
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("adverselectByPK")
	@MappingDescription("根据id查找广告")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Advertisement advertisement = advertisementService.selectByPrimaryKey(id);
		Object object = JSON.toJSON(advertisement);
		return object;
	}
	
	@RequestMapping("adverupdateByPKS")
	@MappingDescription("根据id更新广告")
	@ResponseBody
	public Object updateByPrimaryKeySelective(Advertisement advertisement,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize
            )throws Exception{
		
		advertisementService.updateByPrimaryKeySelective(advertisement);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		//添加查询分页结果
        Pager<Advertisement> advertisementList = advertisementService.selectAdvertisementWithPagesizeFromFromindex(pageNum, pageSize);

		respondate.put("advertisementList", advertisementList);
		
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@RequestMapping("adverupdateByPK")
	@MappingDescription("根据id更新广告")
	@ResponseBody
	public Object updateByPrimaryKey(Advertisement advertisement,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize)throws Exception{

		advertisementService.updateByPrimaryKeySelective(advertisement);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		//添加查询分页结果
        Pager<Advertisement> advertisementList = 
        		advertisementService.selectAdvertisementWithPagesizeFromFromindex(
        				pageNum, pageSize);

		respondate.put("advertisementList", advertisementList);
		
		Object object = JSON.toJSON(respondate);
		return object;
	}
	
	@ResponseBody 
	@MappingDescription("广告位分页查询")
    @RequestMapping("adverPage")
    public Object advertisementPageList(
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
        //添加查询分页结果
        Pager<Advertisement> advertisement = advertisementService.selectAdvertisementWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> advertisementList = new HashMap<String, Object>();
        advertisementList.put("advertisementList", advertisement);
        
        Object object = JSON.toJSON(advertisementList);
        return object;
    }
	
}
