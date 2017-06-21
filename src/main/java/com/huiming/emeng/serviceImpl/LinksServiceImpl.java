package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.LinksMapper;
import com.huiming.emeng.model.Links;
import com.huiming.emeng.service.LinksService;

import sun.awt.image.ImageWatched.Link;

@Service("linksService")
public class LinksServiceImpl implements LinksService {

	@Autowired
	private LinksMapper linksMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return linksMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.insert(record);
	}

	@Override
	public int insertSelective(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.insertSelective(record);
	}

	@Override 
	public Links selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return linksMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Links record) {
		// TODO Auto-generated method stub
		return linksMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Link> selectAllLink() {
		// TODO Auto-generated method stub
		return linksMapper.selectAllLink();
	}

	/**
	 * 分页查询友情链接
	 */
	@Override
	public Pager<Link> selectLinkWithPagesizeFromFromindex(Integer pageNum, Integer pageSize) {
		//总记录
		Integer totalRecord = linksMapper.selectNumberfromLink();
		
		//总页数
		Integer totalPage = totalRecord/pageSize;
		
		if (totalRecord ==0) {
			return null;
		}
		
		if(totalRecord % pageSize !=0){
			totalPage++;
		}
		if(pageNum > totalPage){
        pageNum = totalPage;
		}
		
		Integer fromIndex = (pageNum - 1) * pageSize;
		Pager<Link> pager = new Pager<Link>(pageSize, pageNum, totalRecord, totalPage, 
				linksMapper.selectLinkWithPagesizeFromFromindex(fromIndex, pageSize));
		System.out.println(pager.getTotalRecord());
		System.out.println(pager.getTotalRecord());
		return pager;
	}

}
