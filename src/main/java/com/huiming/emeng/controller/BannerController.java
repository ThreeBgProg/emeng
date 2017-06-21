package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Banner;
import com.huiming.emeng.service.BannerService;

@Controller
@RequestMapping("/banner")
public class BannerController {

	private String SUCCESS = "操作成功";
	private String FAIL = "操作失败";

	@Autowired
	private BannerService bannerService;

	@ResponseBody
	@RequestMapping("/getAllBanner")
	@MappingDescription("查看所有的轮播文章")
	public List<Banner> getAllBanner() {
		return bannerService.selectAll();
	}

	@RequestMapping("/addBanner")
	@ResponseBody
	@MappingDescription("增加轮播")
	public String addBanner(Banner banner) {
		if (bannerService.selectAll().size() < 5) {
			if (bannerService.insert(banner) != 0)
				return FAIL;
			else
				return SUCCESS;
		} else {
			return "轮播数量已满，请先移除已有轮播";
		}

	}

	@RequestMapping("/deleteBannerByPassageId")
	@ResponseBody
	@MappingDescription("根据文章id删除轮播")
	public int deleteBannerByPassageId(Integer passageId) {
		return bannerService.deleteByPassageId(passageId);
	}

	@RequestMapping("/deleteBannerPrimaryKey")
	@ResponseBody
	@MappingDescription("根据id删除轮播")
	public int deleteBannerPrimaryKey(Integer id) {
		return bannerService.deleteByPrimaryKey(id);
	}

	@RequestMapping("/updateBanner")
	@ResponseBody
	@MappingDescription("更新轮播")
	public int updateBanner(Banner banner) {
		return bannerService.updateByPrimaryKeySelective(banner);
	}

}
