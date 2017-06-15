package com.huiming.emeng.service;

import java.util.List;
import java.util.Map;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.model.Meeting;

public interface MeetingService {
 
	int deleteByPrimaryKey(Integer id);

    int insert(Meeting record);

    int insertSelective(Meeting record);

    Meeting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Meeting record);

    int updateByPrimaryKeyWithBLOBs(Meeting record);

    int updateByPrimaryKey(Meeting record);
    
    public List<Meeting> findMeeting(Map<String, String> map);
    
    public List<Meeting> selectAllMeeting();
    

    /**
     * 查询第几页数据
     */
    Pager<Meeting> selectMeetingWithPagesizeFromFromindex(Integer fromIndex,Integer pageSize);

}
