package com.open.cas.shiro.dao.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.open.cas.shiro.dao.AuthDao;
import com.open.cas.shiro.entity.Auth;

@Repository
public class AuthDaoImpl extends HibernateBaseDaoImpl<Auth, Long> implements AuthDao {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */

	private static final long serialVersionUID = 2604320936920706398L;

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setHibernateTemplate() {
		super.setHibernateTemplate(hibernateTemplate);
	}
}
