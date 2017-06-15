package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Advertisement;
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

	public List<Meeting> selectAllMeeting();
	
	/**
     * 返回广告在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromMeeting();
    
    /**
     * 查询第几页数据
     */
    List<Meeting> selectMeetingWithPagesizeFromFromindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

}