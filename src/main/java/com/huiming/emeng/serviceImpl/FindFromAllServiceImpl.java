package com.huiming.emeng.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.MeetingMapper;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.mapper.PostMapper;
import com.huiming.emeng.mapper.TeacherMapper;
import com.huiming.emeng.mapper.VideoMapper;
import com.huiming.emeng.model.Meeting;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.Teacher;
import com.huiming.emeng.model.Video;
import com.huiming.emeng.service.FindFromAllService;

@Service("findFromAllService") 
public class FindFromAllServiceImpl implements FindFromAllService{

	@Autowired
	private MeetingMapper meetingMapper;
	@Autowired
	private PassageMapper passageMapper; 
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private PostMapper postMapper;
		
	public List<Meeting> findMeeting(Map<String, String> map) {
		return meetingMapper.findMeeting(map);
	}

	public List<Post> findPost(Map<String, String> map) {
		return postMapper.findPost(map);
	}

	public List<Teacher> findTeacher(Map<String, String> map) {
		return teacherMapper.findTeacher(map);
	}

	public List<Video> findVideo(Map<String, String> map) {
		return videoMapper.findVideo(map);
	}

	@Override
	public List<Passage> findPassage(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
