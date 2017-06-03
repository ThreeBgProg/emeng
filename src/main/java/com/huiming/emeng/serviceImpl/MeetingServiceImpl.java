package com.huiming.emeng.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.MeetingMapper;
import com.huiming.emeng.model.Meeting;
import com.huiming.emeng.service.MeetingService;

@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {


	@Autowired
	private MeetingMapper meetingMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return meetingMapper.deleteByPrimaryKey(id);
	}

	@Override 
	public int insert(Meeting record) {
		// TODO Auto-generated method stub
		return meetingMapper.insert(record);
	}

	@Override
	public int insertSelective(Meeting record) {
		// TODO Auto-generated method stub
		return meetingMapper.insertSelective(record);
	}

	@Override
	public Meeting selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return meetingMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Meeting record) {
		// TODO Auto-generated method stub
		return meetingMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Meeting record) {
		// TODO Auto-generated method stub
		return meetingMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(Meeting record) {
		// TODO Auto-generated method stub
		return meetingMapper.updateByPrimaryKey(record);
	}

}
