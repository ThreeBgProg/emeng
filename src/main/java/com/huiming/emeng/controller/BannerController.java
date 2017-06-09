package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.huiming.emeng.model.Banner;
import com.huiming.emeng.service.BannerService;

@Controller
public class BannerController {

	@Autowired
	private BannerService bannerService;

	public List<Banner> getAllBanner() {
		return bannerService.selectAll();
	}

	public int addBanner(Banner banner) {
		return bannerService.insert(banner);
	}

	public int deleteBannerByPassageId(Integer passageId) {
		return bannerService.deleteByPassageId(passageId);
	}

	public int deleteBannerPrimaryKey(Integer id) {
		return bannerService.deleteByPrimaryKey(id);
	}

	public int updateBanner(Banner banner) {
		return bannerService.updateByPrimaryKeySelective(banner);
	}

}
