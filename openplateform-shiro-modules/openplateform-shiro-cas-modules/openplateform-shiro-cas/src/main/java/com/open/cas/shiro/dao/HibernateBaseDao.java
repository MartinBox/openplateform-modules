package com.open.cas.shiro.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用泛型作为DAO的通用接口
 * @author liuheng
 * @date 2015年6月16日 下午2:56:43 
 * @param <T>
 * @param <ID>
 */
public interface HibernateBaseDao<T, ID extends Serializable> extends java.io.Serializable {

	public HibernateTemplate gethibernateTeplate();

	/**
	 * 根据属性查找对象 
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value);

	/**
	 * 根据实体查找对象
	 * @param entiey 实体（T类型）
	 * @return
	 */
	public List<T> findByEntity(T entiey);

	/**
	 * 获取记录总数
	 * @return
	 */
	public int getCount();

	/**
	 * 保存实体并返回主键
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
	 * 保存实体集合
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
	 * 删除实体
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 *  查找实体
	 * @param entityId 实体id
	 * @return
	 */
	@Transactional
	public T findById(Serializable entityId);

	/**
	 * 保存或更新
	 * @param entity
	 */
	public void saveOrUpdate(T entity);

	public List<T> find(String hql);

	public List findForHql(String hql);

}
