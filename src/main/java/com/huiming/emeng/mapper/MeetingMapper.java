package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import com.huiming.emeng.model.Meeting;

public interface MeetingMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(Meeting record);

    int insertSelective(Meeting record);

    Meeting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Meeting record);

    int updateByPrimaryKeyWithBLOBs(Meeting record);

    int updateByPrimaryKey(Meeting record);
    
    public List<Meeting> findMeeting(Map<String, String> map);
}