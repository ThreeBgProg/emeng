package com.huiming.emeng.service;

import java.util.List;
import java.util.Map; 

import com.huiming.emeng.model.Meeting;
import com.huiming.emeng.model.Passage;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.Teacher;
import com.huiming.emeng.model.Video;

public interface FindFromAllService {
	
	public List<Meeting> findMeeting(Map<String, String> map);
	
	public List<Passage> findPassage(Map<String, String> map);
	
	public List<Post> findPost(Map<String, String> map);
	
	public List<Teacher> findTeacher(Map<String, String> map);
	
	public List<Video> findVideo(Map<String, String> map);
}
