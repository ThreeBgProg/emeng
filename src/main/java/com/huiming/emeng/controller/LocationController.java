package com.huiming.emeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huiming.emeng.annotation.MappingDescription;
import com.huiming.emeng.model.Location;
import com.huiming.emeng.service.LocationService;

@Controller
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	
	@RequestMapping("/getAllLocation")
	@MappingDescription("修改学校信息")
	@ResponseBody
	public List<Location> getAllLocation() {
		return locationService.selectAll();
	}
}
