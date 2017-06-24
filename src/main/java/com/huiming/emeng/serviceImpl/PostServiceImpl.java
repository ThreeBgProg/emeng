package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.PostMapper;
import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;
import com.huiming.emeng.model.States;
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


	@Override
	public Pager<Post> selectPostWithPagesizeFromFromindex(Integer pageNum, Integer pageSize,Integer status) {
		//总记录
		Integer totalRecord = postMapper.selectNumberfromPost(new States(status));
		
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
		Pager<Post> pager = null;
		//审核通过
		if(status>0){
			pager = new Pager<Post>(pageSize, pageNum, totalRecord, totalPage, 
					postMapper.selectPostWithPagesizeFromFromindex(fromIndex, pageSize));

		}
		//待审核
		if(status==0){
			pager = new Pager<Post>(pageSize, pageNum, totalRecord, totalPage, 
					postMapper.selectPostWithPagesizeFromFromindex1(fromIndex, pageSize));

		}else{
			//审核不通过
			pager = new Pager<Post>(pageSize, pageNum, totalRecord, totalPage, 
					postMapper.selectPostWithPagesizeFromFromindex2(fromIndex, pageSize));
		}
		
	    return pager;
	}
	
	@Override
	public Pager<Post> selectPostByVist(Integer pageNum, Integer pageSize,Integer status) {
		//总记录
		Integer totalRecord = postMapper.selectNumberfromPost(new States(status));
		
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
		Pager<Post> pager = null;

		pager = new Pager<Post>(pageSize, pageNum, totalRecord, totalPage, 
					postMapper.selectPostByVist(fromIndex, pageSize));
	    return pager;
	}
}
