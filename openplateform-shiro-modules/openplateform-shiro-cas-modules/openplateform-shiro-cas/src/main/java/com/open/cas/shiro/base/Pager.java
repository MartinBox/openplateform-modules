package com.open.cas.shiro.base;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Pager implements Paginable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4200889036989358135L;

	public static final int DEF_COUNT = 20;

	protected int totalCount = 0;
	protected int pageSize = 15;
	protected int pageNo = 1;
	// 查询
	protected int startIndex = 1;
	protected int endIndex = 1;
	private String orderProperty = "id";

	private String orderDirection = "ASC";

	public Pager() {
	}

	/**
	 * 构造器
	 */
	public Pager(int pageNo, int pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

	public Pager(int pageNo, int totalCount) {
		setPageNo(pageNo);
		setTotalCount(totalCount);
		adjustPageNo();
	}

	/**
	 * 调整页码，使不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * if totalCount<0 then totalCount=0
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
		adjustPageNo();
	}

	/**
	 * if pageSize< 1 then pageSize=DEF_COUNT
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * if pageNo < 1 then pageNo=1
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}

	public boolean getHasPrevious() {
		return getPageNo() > 1;
	}

	public boolean getIsFirst() {
		return isFirstPage();
	}

	public boolean getIsLastPage() {
		return isLastPage();
	}

	public List<Integer> getSegment() {
		List<Integer> list = new LinkedList<Integer>();
		int totalPage = getTotalPage();
		list.add(pageNo - 2 > 0 ? pageNo - 2 : 0);
		list.add(pageNo - 1 > 0 ? pageNo - 1 : 0);
		list.add(pageNo);
		list.add(pageNo + 1 <= totalPage ? pageNo + 1 : 0);
		list.add(pageNo + 2 <= totalPage ? pageNo + 2 : 0);
		return list;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

}
