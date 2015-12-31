package com.open.cas.shiro.base;

import java.util.List;

/**
 * 
 * @description:查询结果集，包括数据和总数    
 * @fileName:PageResult.java 
 * @createTime:2015年1月19日 下午6:06:12  
 * @author:肖震 
 * @version 1.0.0  
 *
 */
public class PageResult<T> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2946771741279994563L;

	/** 查询得出的数据List **/
	private List<T> resultList;

	/** 查询得出的总数 **/
	private long totalCount;

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
