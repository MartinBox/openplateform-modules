package com.open.shiro.dynamic.permissions.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.open.shiro.dynamic.permissions.dao.UrlFilterDao;
import com.open.shiro.dynamic.permissions.entity.UrlFilter;

/**
 * 
 * The class UrlFilterServiceImpl.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Service
public class UrlFilterServiceImpl implements UrlFilterService {

	@Autowired
	private UrlFilterDao urlFilterDao;

	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;

	@Override
	public UrlFilter createUrlFilter(UrlFilter urlFilter) {
		urlFilterDao.createUrlFilter(urlFilter);
		initFilterChain();
		return urlFilter;
	}

	@Override
	public UrlFilter updateUrlFilter(UrlFilter urlFilter) {
		urlFilterDao.updateUrlFilter(urlFilter);
		initFilterChain();
		return urlFilter;
	}

	@Override
	public void deleteUrlFilter(Long urlFilterId) {
		urlFilterDao.deleteUrlFilter(urlFilterId);
		initFilterChain();
	}

	@Override
	public UrlFilter findOne(Long urlFilterId) {
		return urlFilterDao.findOne(urlFilterId);
	}

	@Override
	public List<UrlFilter> findAll() {
		return urlFilterDao.findAll();
	}

	@PostConstruct
	public void initFilterChain() {
		shiroFilerChainManager.initFilterChains(findAll());
	}

}
