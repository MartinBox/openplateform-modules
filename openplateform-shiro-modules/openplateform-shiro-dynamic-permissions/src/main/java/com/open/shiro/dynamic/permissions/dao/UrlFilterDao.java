package com.open.shiro.dynamic.permissions.dao;

import java.util.List;

import com.open.shiro.dynamic.permissions.entity.UrlFilter;

/**
 * 
 * The class UrlFilterDao.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface UrlFilterDao {

	public UrlFilter createUrlFilter(UrlFilter urlFilter);

	public UrlFilter updateUrlFilter(UrlFilter urlFilter);

	public void deleteUrlFilter(Long urlFilterId);

	public UrlFilter findOne(Long urlFilterId);

	public List<UrlFilter> findAll();
}
