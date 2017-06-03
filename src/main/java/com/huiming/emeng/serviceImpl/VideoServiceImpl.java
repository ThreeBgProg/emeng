package com.huiming.emeng.serviceImpl;

import java.util.List;

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
		return videoMapper.deleteByPrimaryKey(id);
	}

	//上传视屏资源
	@Override
	public int insert(Video record) {
		return videoMapper.insert(record);
	}

	//选择字段上传视频
	public int insertSelective(Video record) {
		// TODO Auto-generated method stub
		return videoMapper.insertSelective(record);
	}

	@Override
	public Video selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return videoMapper.selectByPrimaryKey(id);
	}

	//选择性字段更新
	@Override
	public int updateByPrimaryKeySelective(Video record) {
		// TODO Auto-generated method stub
		return videoMapper.updateByPrimaryKeySelective(record);
	}

	//全部字段更新
	@Override
	public int updateByPrimaryKey(Video record) {
		// TODO Auto-generated method stub
		return videoMapper.updateByPrimaryKey(record);
	}

	//根据课程id查找
	public List<Video> selectBylesson(Integer lesson) {
		// TODO Auto-generated method stub
		return videoMapper.selectBylesson(lesson);
	}

	//根据章节id查找
	public List<Video> selectBychapter(Integer chapter) {
		// TODO Auto-generated method stub
		return videoMapper.selectBychapter(chapter);
	}
	
	

}
