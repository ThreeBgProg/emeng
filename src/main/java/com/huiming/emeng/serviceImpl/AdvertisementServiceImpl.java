package com.huiming.emeng.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.dto.Pager;
import com.huiming.emeng.mapper.AdvertisementMapper;
import com.huiming.emeng.model.Advertisement;
import com.huiming.emeng.service.AdvertisementService;

@Service("advertisementService")
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired 
	private AdvertisementMapper advertisementMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return advertisementMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Advertisement record) {
		// TODO Auto-generated method stub
		return advertisementMapper.insert(record);
	}

	@Override
	public int insertSelective(Advertisement record) {
		// TODO Auto-generated method stub
		return advertisementMapper.insertSelective(record);
	}

	@Override
	public Advertisement selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return advertisementMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Advertisement record) {
		// TODO Auto-generated method stub
		return advertisementMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Advertisement record) {
		// TODO Auto-generated method stub
		return advertisementMapper.updateByPrimaryKey(record);
	}

	@Override
	public Pager<Advertisement> selectAdvertisementWithPagesizeFromFromindex(Integer pageNum, Integer pageSize) {
		//总记录
		Integer totalRecord = advertisementMapper.selectNumberfromAdvertisement();
		
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
		Pager<Advertisement> pager = new Pager<Advertisement>(pageSize, pageNum, totalRecord, totalPage, 
				advertisementMapper.selectAdvertisementWithPagesizeFromFromindex(fromIndex, pageSize));
		return pager;
	}
	
	}
	
