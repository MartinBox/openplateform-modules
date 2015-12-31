package com.open.cas.shiro.dao.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.open.cas.shiro.dao.HibernateBaseDao;
import com.open.cas.shiro.util.GenericsUtils;

/**
 * HibernateBaseDao实现类   
 * @author liuheng
 * @date 2015年6月16日 下午2:58:06 
 * @param <T>
 * @param <ID>
 */
public class HibernateBaseDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements HibernateBaseDao<T, ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5552828190676541382L;

	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected String entityClassName = getEntityName(this.entityClass);

	protected String keyFieldName = getKeyFieldName(this.entityClass);

	@Override
	public HibernateTemplate gethibernateTeplate() {
		return super.getHibernateTemplate();
	}

	/**
	 * 获取实体的名称
	 * 
	 * @param <E>
	 * @param clazz 实体类
	 * @return
	 */
	protected static <E> String getEntityName(Class<E> clazz) {
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

	@Override
	public List<T> findByEntity(T entiey) {
		return super.getHibernateTemplate().findByExample(entiey);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		String queryString = "from " + entityClassName + " o where o." + propertyName + "= ?";
		return super.getHibernateTemplate().find(queryString, value);
	}

	@Override
	public void delete(Serializable... entityids) {
		for (Object id : entityids) {
			super.getHibernateTemplate().delete(findById((Serializable) id));
		}
	}

	@Override
	public T findById(Serializable entityId) {
		if (null != entityId) return (T) super.getHibernateTemplate().get(entityClass, entityId);
		return null;
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public int getCount() {
		String hql = "select count( " + keyFieldName + ") from " + entityClassName;
		int count = Integer.parseInt(super.getHibernateTemplate().find(hql).get(0).toString());
		return count;
	}

	@Override
	public Serializable saveBackId(T entity) {
		return super.getHibernateTemplate().save(entity);
	}

	@Override
	public void save(T entity) {
		super.getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		super.getHibernateTemplate().update(entity);
	}

	/**
	 * 获取实体的主键
	 * 
	 * @param <E>
	 * @param clazz 实体类
	 * @return 主键名
	 */
	protected static <E> String getKeyFieldName(Class<E> clazz) {
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (null != method && null != method.getAnnotation(javax.persistence.Id.class)) {
					return propertydesc.getName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "id";
	}

	@Transactional
	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);

	}

	@Override
	public void saveOrUpdateAll(Collection<T> entities) {
		this.getHibernateTemplate().saveOrUpdateAll(entities);

	}

	@Override
	public List findForHql(String hql) {
		// TODO Auto-generated method stub
		List list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<T> find(String hql) {
		List<T> list = (List<T>) this.getHibernateTemplate().find(hql);
		return list;
	}

}
