package com.huiming.emeng.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.LinksMapper;
import com.huiming.emeng.model.Links;
import com.huiming.emeng.service.LinksService;

@Service("linksService")
public class LinksServiceImpl implements LinksService {

	@Autowired
	private LinksMapper linksMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return linksMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.insert(record);
	}

	@Override
	public int insertSelective(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.insertSelective(record);
	}

	@Override 
	public Links selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return linksMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.updateByPrimaryKey(record);
	}

}
