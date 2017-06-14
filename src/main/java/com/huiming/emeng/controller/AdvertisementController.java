package com.huiming.emeng.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.model.Navigation;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.service.AdvertisementService;


@Controller 
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping("adverinsert")
	@MappingDescription("添加广告")
	@ResponseBody
	public Object insert(Advertisement advertisement,
			Model model, 
			HttpServletRequest request,
			@RequestParam("pic") MultipartFile pic)throws Exception{
		
		if(!pic.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = pic.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   pic.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName); 

		}
		
		int result = advertisementService.insert(advertisement);
		Map<String, String> respondate=new HashMap<>();
		respondate.put("message", "添加成功");
		
		return respondate;
	}
	
	@RequestMapping("adverdelPK")
	@MappingDescription("根据id删除广告")
	@ResponseBody
	public Object deleteByPrimaryKey(@RequestParam("id") Integer id,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize,
            Model model){
		int result = advertisementService.deleteByPrimaryKey(id);
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "删除成功");
		 //添加查询分页结果
        Pager<Advertisement> advertisement = advertisementService.selectAdvertisementWithPagesizeFromFromindex(pageNum, pageSize);
        respondate.put("advertisementList", advertisement);
		
		return respondate;
	}
	
	@RequestMapping("adverinsertSel")
	@MappingDescription("添加广告")
	@ResponseBody
	public Object insertSelective(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("pic") MultipartFile pic)throws Exception{
		if(!pic.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = pic.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   pic.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);

		}

		int result = advertisementService.insert(advertisement);
		Map<String, String> respondate=new HashMap<>();
		respondate.put("message", "添加成功");
		return respondate;
	}
	
	@RequestMapping("adverselectByPK")
	@MappingDescription("根据id查找广告")
	@ResponseBody
	public Object selectByPrimaryKey(@RequestParam("id") Integer id,Model model){
		
		Advertisement advertisement = advertisementService.selectByPrimaryKey(id);
		
		return advertisement;
	}
	
	@RequestMapping("adverupdateByPKS")
	@MappingDescription("根据id更新广告")
	@ResponseBody
	public Object updateByPrimaryKeySelective(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize
            )throws Exception{
		
		if(!file.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = file.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   file.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);

		}

		int result = advertisementService.updateByPrimaryKeySelective(advertisement);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		//添加查询分页结果
        Pager<Advertisement> advertisementList = advertisementService.selectAdvertisementWithPagesizeFromFromindex(pageNum, pageSize);

		respondate.put("advertisementList", advertisementList);
		return respondate;
	}
	
	@RequestMapping("adverupdateByPK")
	@MappingDescription("根据id更新广告")
	@ResponseBody
	public Object updateByPrimaryKey(Advertisement advertisement,
			Model model,
			HttpServletRequest request,
			@RequestParam("file") MultipartFile file,
			@RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize)throws Exception{
		if(!file.isEmpty()){
			   //上传文件路劲
			   String path = request.getServletContext().getRealPath("/images/");
			   String fileName = file.getOriginalFilename();   
			   File filepath = new File(path,fileName);
			   if(!filepath.getParentFile().exists()){
				   filepath.getParentFile().mkdirs();
			   }
			   file.transferTo(new File(path+File.separator+fileName));
			   advertisement.setPic(path+fileName);
		}

		int result = advertisementService.updateByPrimaryKeySelective(advertisement);
		
		Map<Object, Object> respondate=new HashMap<>();
		respondate.put("message", "更新成功");
		//添加查询分页结果
        Pager<Advertisement> advertisementList = 
        		advertisementService.selectAdvertisementWithPagesizeFromFromindex(
        				pageNum, pageSize);

		respondate.put("advertisementList", advertisementList);
		return respondate;
	}
	
	@ResponseBody 
	@MappingDescription("广告位分页查询")
    @RequestMapping("adverPage")
    public Object advertisementPageList(ModelMap modelMap,
                                  @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value="pageSize", defaultValue = "15") Integer pageSize){
		
        //添加查询分页结果
        Pager<Advertisement> advertisement = advertisementService.selectAdvertisementWithPagesizeFromFromindex(pageNum, pageSize);

        Map< String, Object> advertisementList = new HashMap<String, Object>();
        advertisementList.put("advertisementList", advertisement);
        return advertisementList;
    }
	
}
