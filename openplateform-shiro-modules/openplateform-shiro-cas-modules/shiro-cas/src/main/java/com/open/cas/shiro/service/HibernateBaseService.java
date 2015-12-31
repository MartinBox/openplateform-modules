package com.open.cas.shiro.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * hibernate基础Service
 * @author liuheng
 * @date 2015年6月17日 下午5:20:29 
 * @param <T>
 * @param <ID>
 */
public interface HibernateBaseService<T, ID extends Serializable> extends Serializable {

	/**
	 * 根据属性查找对象
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value);

	/**
	 * 根据实体查找对象
	 * @param entiey
	 * @return
	 */
	public List<T> findByEntity(T entiey);

	/**
	 * 获取记录总数 
	 * @return
	 */
	public int getCount();

	/**
	 * 保存返回id
	 * @param entity
	 * @return
	 */
	public Serializable saveBackId(T entity);

	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 保存或更新实体集合
	 * @param entities
	 */
	public void saveOrUpdateAll(Collection<T> entities);

	/**
	 * 更新实体 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 删除实体
	 * @param entityids
	 */
	public void delete(Serializable... entityids);

	/**
	 * 批量删除
	 * @param entities
	 */
	public void deleteAll(Collection<T> entities);

	/**
	 * 删除
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 查找实体
	 * @param entityId 实体id
	 * @return
	 */
	public T findById(Serializable entityId);

	public List<T> find(String hql);

	public List findForHql(String hql);
}
