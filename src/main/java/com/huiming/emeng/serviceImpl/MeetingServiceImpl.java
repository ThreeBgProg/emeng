package com.huiming.emeng.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.MeetingMapper;
import com.huiming.emeng.model.Advertisement;
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

	@Override
	public List<Meeting> findMeeting(Map<String, String> map) {
		// TODO Auto-generated method stub
		return meetingMapper.findMeeting(map);
	}

	@Override
	public List<Meeting> selectAllMeeting() {
		// TODO Auto-generated method stub
		return meetingMapper.selectAllMeeting();
	}

	@Override
	public Pager<Meeting> selectMeetingWithPagesizeFromFromindex(Integer pageNum, Integer pageSize) {
		//总记录
				Integer totalRecord = meetingMapper.selectNumberfromMeeting();
				
				//总页数
				Integer totalPage = totalRecord/pageSize;
				if (totalRecord ==0) {
					return null;
				}
				{
					if(totalRecord % pageSize !=0){
						totalPage++;
					}
					if(pageNum > totalPage){
		            pageNum = totalPage;
					}
				}
				Integer fromIndex = (pageNum - 1) * pageSize;
				Pager<Meeting> pager = new Pager<Meeting>(pageSize, pageNum, totalRecord, totalPage, 
						meetingMapper.selectMeetingWithPagesizeFromFromindex(fromIndex, pageSize));
				return pager;
	}

}
