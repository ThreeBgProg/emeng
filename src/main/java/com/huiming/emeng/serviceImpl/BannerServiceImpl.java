package com.huiming.emeng.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.bo.PassageWithBanner;
import com.huiming.emeng.mapper.BannerMapper;
import com.huiming.emeng.mapper.PassageMapper;
import com.huiming.emeng.model.Banner;
import com.huiming.emeng.service.BannerService;

@Service("bannerService")
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private PassageMapper passageMapper;
	
	@Override
	public List<PassageWithBanner> selectAll() {
		List<Banner> temp = bannerMapper.selectAll();
		List<PassageWithBanner> list = new ArrayList<>();
		for (Banner banner : temp) {
			PassageWithBanner passageWithBanner = new PassageWithBanner(banner);
			passageWithBanner.setPassage(passageMapper.selectByPrimaryKey(banner.getPassageId()));
			list.add(passageWithBanner);
		}
		return list;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return bannerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByPassageId(Integer id) {
		return bannerMapper.deleteByPassageId(id);
	}

	@Override
	public int insert(Banner record) {
		return bannerMapper.insert(record);
	}

	@Override
	public int insertSelective(Banner record) {
		return bannerMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Banner record) {
		return bannerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Banner record) {
		return bannerMapper.updateByPrimaryKey(record);
	}

}
