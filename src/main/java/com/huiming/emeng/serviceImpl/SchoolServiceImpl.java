package com.huiming.emeng.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.bo.SchoolWithLocation;
import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.LocationMapper;
import com.huiming.emeng.mapper.SchoolMapper;
import com.huiming.emeng.model.School;
import com.huiming.emeng.service.SchoolService;

@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolMapper schoolmapper;

	@Autowired
	private LocationMapper locationMapper;

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
	public Pager<SchoolWithLocation> selectAllByPage(Integer currentPage, Integer pageSize) {
		int fromIndex = (currentPage - 1) * pageSize;
		int totalRecord = schoolmapper.selectCount();
		List<School> schools = schoolmapper.selectAllByPage(fromIndex, pageSize);
		List<SchoolWithLocation> schoolList = new ArrayList<>();
		for (School school : schools) {
			SchoolWithLocation schoolWithLocation = new SchoolWithLocation(school);
			schoolWithLocation.setLocation(locationMapper.selectByPrimaryKey(school.getProvinceId()));
			schoolList.add(schoolWithLocation);
		}
		Pager<SchoolWithLocation> schoolPage = new Pager<>(pageSize, currentPage, totalRecord, schoolList);
		return schoolPage;
	}

	public List<School> selectByProvince(Integer provinceId) {
		return schoolmapper.selectByProvince(provinceId);
	}

	@Override
	public List<SchoolWithLocation> selectAll() {
		List<School> schools = schoolmapper.selectAll();
		List<SchoolWithLocation> schoolList = new ArrayList<>();
		for (School school : schools) {
			SchoolWithLocation schoolWithLocation = new SchoolWithLocation(school);
			schoolWithLocation.setLocation(locationMapper.selectByPrimaryKey(school.getProvinceId()));
			schoolList.add(schoolWithLocation);
		}
		return schoolList;
	}

	@Override
	public List<School> selectSchoolsByTypeAndProvinceId(Integer provinceId, Byte type) {
		return schoolmapper.selectSchoolsByTypeAndProvinceId(provinceId, type);
	}

	@Override
	public List<School> selectSchoolsByType(Byte type) {
		return schoolmapper.selectSchoolsByType(type);
	}
}
