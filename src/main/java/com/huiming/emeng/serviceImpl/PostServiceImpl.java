package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.PostMapper;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;
import com.huiming.emeng.service.PostService;

/**
 * 
 * @author zhiwei
 *
 */
@Service("postservice")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostMapper postMapper;


	public int deleteByPrimaryKey(Integer id) {
		
		return postMapper.deleteByPrimaryKey(id);
	}


	public int insert(PostWithBLOBs record) {
		
		return postMapper.insert(record);
	}


	public int insertSelective(PostWithBLOBs record) {
		
		return postMapper.insertSelective(record);
	}


	public PostWithBLOBs selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return postMapper.selectByPrimaryKey(id);
	}
 

	public int updateByPrimaryKeySelective(PostWithBLOBs record) {
		// TODO Auto-generated method stub
		return postMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKeyWithBLOBs(PostWithBLOBs record) {
		// TODO Auto-generated method stub
		return postMapper.updateByPrimaryKeyWithBLOBs(record);
	}


	public int updateByPrimaryKey(Post record) {
		// TODO Auto-generated method stub
		return postMapper.updateByPrimaryKey(record);
	}


	@Override
	public List<Post> selectAllPost() {
		// TODO Auto-generated method stub
		return postMapper.selectAllPost();
	}
}
