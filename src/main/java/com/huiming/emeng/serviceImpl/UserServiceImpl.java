package com.huiming.emeng.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huiming.emeng.mapper.ApplyMapper;
import com.huiming.emeng.model.Apply;
import com.huiming.emeng.service.UserService;
/**
 * 用户报名
 * @author zhiwei
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private ApplyMapper applyMapper;
	
    /*
     * 后台生成报名表
     * @see com.huiming.emeng.service.UserService#insert(com.huiming.emeng.model.Apply)
     */
	public void insert(Apply apply) {
		applyMapper.insert(apply);
	}
    /*
     * 后台查询全部报名表的信息 （管理员）
     * @see com.huiming.emeng.service.UserService#selectApplys()
     */
	public List<Apply> selectAllApply() {
		return applyMapper.selectAllApply();
	}
	
	/*
	 * 根据主键删除
	 * @see com.huiming.emeng.service.UserService#deleteByPrimaryKey(java.lang.Integer)
	 */
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return applyMapper.deleteByPrimaryKey(id);
	}

	
}