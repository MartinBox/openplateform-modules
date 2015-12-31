package com.open.cas.shiro.dao.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.open.cas.shiro.dao.UserDao;
import com.open.cas.shiro.entity.User;

@Repository
public class UserDaoImpl extends HibernateBaseDaoImpl<User, Long> implements UserDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6986422925655615159L;

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setHibernateTemplate() {
		super.setHibernateTemplate(hibernateTemplate);
	}

}
