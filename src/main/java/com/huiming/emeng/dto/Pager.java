package com.huiming.emeng.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 封装了返回给前端页面信息 Created by LeoMs on 2017/5/23 0023.
 */
public class Pager<T> implements Serializable {

	public static final long serialVersonUID = 1L;

	private int pageSize; // 每页显示多少条记录

	private int currentPage; // 当前第几页数据

	private int totalRecord; // 一共多少条记录

	private int totalPage; // 一共多少页记录

	private List<T> dataList; // 要显示的数据

	public Pager() {
	}

	public Pager(int pageSize, int currentPage, int totalRecord, int totalPage, List<T> dataList) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRecord = totalRecord;
		this.totalPage = totalPage;
		this.dataList = dataList;
	}

	public Pager(int pageSize, int currentPage, List<T> dataList) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRecord = dataList.size();
		if (totalRecord % pageSize == 0) {
			this.totalPage = totalRecord / pageSize;
		} else {
			this.totalPage = totalRecord / pageSize + 1;
		}
		int fromIndex = (currentPage - 1) * pageSize;
		int toIndex = currentPage * pageSize < totalRecord ? currentPage * pageSize : totalRecord;
		this.dataList = dataList.subList(fromIndex, toIndex);
	}

	public Pager(int pageSize, int currentPage, int totalRecord,List<T> dataList) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRecord = totalRecord;
		if (totalRecord % pageSize == 0) {
			this.totalPage = totalRecord / pageSize;
		} else {
			this.totalPage = totalRecord / pageSize + 1;
		}
		this.dataList = dataList;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
