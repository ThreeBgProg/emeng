package com.huiming.emeng.controller;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.*;
import com.huiming.emeng.service.FindFromAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class FindFromAllController {

	@Autowired
	private FindFromAllService findFromAllService;

	@RequestMapping("sousuo")
	@MappingDescription("全文搜索")
	@ResponseBody
	public String fingFromAll(@RequestParam("sousuo") String sousuo, Model model){
		Map<String, String> map =new HashMap<String, String>();

		map.put("title", "%"+sousuo+"%");//视频
		List<Video> listVideo = findFromAllService.findVideo(map);
		map.remove("title");

		map.put("titile",  "%"+sousuo+"%");
		map.put("author",  "%"+sousuo+"%");
		List<Passage> listPassage = findFromAllService.findPassage(map);
		map.remove("author");

		List<Post> listPost = findFromAllService.findPost(map);
		map.remove("titile");

		map.put("name",  "%"+sousuo+"%");
		map.put("direction",  "%"+sousuo+"%");
		map.put("subject", "%"+sousuo+"%");
		List<Teacher> listTeacher=findFromAllService.findTeacher(map);
		map.remove("name");
		map.remove("direction");
		map.remove("subject");

		map.put("title",  "%"+sousuo+"%");
		List<Meeting> listMeeting=findFromAllService.findMeeting(map);
		map.remove("title");

		model.addAttribute("listMeeting", listMeeting);
		model.addAttribute("listPassage", listPassage);
		model.addAttribute("listPost", listPost);
		model.addAttribute("listTeacher", listTeacher);
		model.addAttribute("listVideo", listVideo);


		return null;
	}


}
