package com.huiming.emeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.NavigationMapper;
import com.huiming.emeng.model.Navigation;

/**
 * Created by LeoMs on 2017/5/20 0020.
 * 由13124937709用户补充
 */
@Service
public class NavigationService {

    @Autowired
    NavigationMapper navigationMapper;

    public List<Navigation> selectAllNavigation(){
        return navigationMapper.selectAllNavigation();
    }
    
    
    public int deleteByPrimaryKey(Integer id){
    	return navigationMapper.deleteByPrimaryKey(id);
    }
    
    public int insert(Navigation record){
    	return navigationMapper.insert(record);
    }
 
    public int insertSelective(Navigation record){
    	return navigationMapper.insertSelective(record);
    }

    public Navigation selectByPrimaryKey(Integer id){
    	return navigationMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Navigation record){
    	return navigationMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Navigation record){
    	return navigationMapper.updateByPrimaryKey(record);
    }
    
	/**
	 * 分页查询友情链接
	 */
	public Pager<Navigation> selectnavigationMapperWithPagesizeFromFromindex(Integer pageNum, Integer pageSize) {
		//总记录
		Integer totalRecord = navigationMapper.selectNumberfromNavigation();
		
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
		Pager<Navigation> pager = new Pager<Navigation>(pageSize, pageNum, totalRecord, totalPage, 
				navigationMapper.selectNavigationWithPagesizeFromFromindex(fromIndex, pageSize));
		System.out.println(pager.getTotalRecord());
		System.out.println(pager.getTotalRecord());
		return pager;
	}
    
}
