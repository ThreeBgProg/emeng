package com.huiming.emeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.huiming.emeng.mapper.ChapterMapper;
import com.huiming.emeng.model.Chapter;

@Service
public class ChapterService {

	@Autowired
	private ChapterMapper chapterMapper;
	public List<Chapter> selectAllChapterFromLesson(Integer lessonId){
		
		return chapterMapper.selectAllChapterFromLesson(lessonId);
	}
}
