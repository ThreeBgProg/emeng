package com.huiming.emeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.LocationMapper;
import com.huiming.emeng.model.Location;

@Service
public class LocationService {

	@Autowired
	private LocationMapper locationMapper;

	int deleteByPrimaryKey(Integer id) {
		return locationMapper.deleteByPrimaryKey(id);
	}

	int insert(Location record) {
		return locationMapper.insert(record);
	}

	int insertSelective(Location record) {
		return insertSelective(record);
	}

	 public Location selectByPrimaryKey(Integer id) {
		return locationMapper.selectByPrimaryKey(id);
	}

	int updateByPrimaryKeySelective(Location record) {
		return locationMapper.updateByPrimaryKeySelective(record);
	}

	int updateByPrimaryKey(Location record) {
		return locationMapper.updateByPrimaryKey(record);
	}

	public List<Location> selectAll(){
		return locationMapper.selectAll();
	}
}
