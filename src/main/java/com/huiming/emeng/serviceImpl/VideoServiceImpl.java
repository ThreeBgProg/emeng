package com.huiming.emeng.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.VideoMapper;
import com.huiming.emeng.model.Video;
import com.huiming.emeng.service.VideoService;

@Service("vdieoService")
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoMapper videoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	//上传视屏资源
	@Override
	public int insert(Video record) {
		return videoMapper.insert(record);
	}

	@Override
	public int insertSelective(Video record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Video selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return videoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Video record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Video record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
