package com.open.cas.shiro.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.open.cas.shiro.dao.HibernateBaseDao;
import com.open.cas.shiro.service.HibernateBaseService;

public class HibernateBaseServiceImpl<T, ID extends Serializable> implements HibernateBaseService<T, ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 376837238576475285L;

	private HibernateBaseDao<T, ID> baseDao;

	public HibernateBaseDao<T, ID> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(HibernateBaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		return baseDao.findByProperty(propertyName, value);
	}

	@Override
	public List<T> findByEntity(T entiey) {
		return baseDao.findByEntity(entiey);
	}

	@Override
	public int getCount() {
		return baseDao.getCount();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Serializable saveBackId(T entity) {
		return baseDao.saveBackId(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(T entity) {
		baseDao.save(entity);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdateAll(Collection<T> entities) {
		baseDao.saveOrUpdateAll(entities);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T entity) {
		baseDao.update(entity);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Serializable... entityids) {
		baseDao.delete(entityids);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAll(Collection<T> entities) {
		baseDao.deleteAll(entities);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T entity) {
		baseDao.delete(entity);

	}

	@Override
	public T findById(Serializable entityId) {
		return (T) baseDao.findById(entityId);
	}

	@Override
	public List<T> find(String hql) {
		// TODO Auto-generated method stub
		return baseDao.find(hql);
	}

	@Override
	public List findForHql(String hql) {
		// TODO Auto-generated method stub
		return baseDao.findForHql(hql);
	}

}
