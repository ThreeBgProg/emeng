package com.huiming.emeng.service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.ChapterMapper;
import com.huiming.emeng.model.Chapter;
import com.huiming.emeng.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

	@Autowired
	private ChapterMapper chapterMapper;
	public Pager<Chapter> selectAllChapterFromLesson(Integer lessonId, Integer pageSize, Integer pageNum){

		//总记录数
		Integer totalRecord = chapterMapper.selectCountByLesson(lessonId);
		//总页数
		Integer totalPage = totalRecord / pageSize;
		if(totalRecord % pageSize != 0){
			totalPage++;
		}
		if(pageNum > totalPage){
			pageNum = totalPage;
		}
		//起始索引
		Integer fromIndex = (pageNum - 1) * pageSize;

		//如果fromIndex为负数，则设为0
		if(fromIndex < 0) {
			fromIndex = 0;
		}
		Pager<Chapter> pager = new Pager<>(pageSize, pageNum, totalRecord, totalPage,
				chapterMapper.selectChapterPageFromLesson(lessonId,pageSize,fromIndex));
		return pager;
//		return chapterMapper.selectAllChapterFromLesson(lessonId);
	}

	public List<Chapter> selectAllChapterFromLesson(Integer lessonId){
		return chapterMapper.selectAllChapterFromLesson(lessonId);
	}
}
