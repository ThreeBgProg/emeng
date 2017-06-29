package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.bo.PassageWithBanner;
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
	public List<PassageWithBanner> getAllBanner() {
		System.out.println("getAllBanner");
		return bannerService.selectAll();
	}

	@RequestMapping("/addBanner")
	@ResponseBody
	@MappingDescription("增加轮播")
	public String addBanner(Banner banner) {
		if (bannerService.selectByPassageId(banner.getPassageId()) == null && bannerService.selectAll().size() < 4) {
			if (bannerService.insert(banner) != 0)
				return SUCCESS;
			else
				return FAIL;
		} else {
			return "轮播数量或该文章已被设置为轮播已满，请先移除已有轮播";
		}

	}

	@RequestMapping("/deleteBannerByPassageId")
	@ResponseBody
	@MappingDescription("根据文章id删除轮播")
	public String deleteBannerByPassageId(Integer passageId) {
		if (bannerService.deleteByPassageId(passageId) != 0) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	}

	@RequestMapping("/deleteBannerPrimaryKey")
	@ResponseBody
	@MappingDescription("根据id删除轮播")
	public String deleteBannerPrimaryKey(Integer id) {
		if (bannerService.deleteByPrimaryKey(id) != 0) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	}

	@RequestMapping("/updateBanner")
	@ResponseBody
	@MappingDescription("更新轮播")
	public String updateBanner(Banner banner) {
		if (bannerService.updateByPrimaryKeySelective(banner) != 0) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	}

}
