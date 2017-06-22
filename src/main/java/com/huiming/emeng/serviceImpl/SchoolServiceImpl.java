package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.SchoolMapper;
import com.huiming.emeng.model.School;
import com.huiming.emeng.service.SchoolService;

@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolMapper schoolmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return schoolmapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(School record) {
		return schoolmapper.insert(record);
	}

	@Override
	public int insertSelective(School record) {
		return schoolmapper.insertSelective(record);
	}

	@Override
	public School selectByPrimaryKey(Integer id) {
		return schoolmapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(School record) {
		return schoolmapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(School record) {
		return schoolmapper.updateByPrimaryKey(record);
	}

	@Override
	public List<School> selectAll() {
		return schoolmapper.selectAll();
	}

}
