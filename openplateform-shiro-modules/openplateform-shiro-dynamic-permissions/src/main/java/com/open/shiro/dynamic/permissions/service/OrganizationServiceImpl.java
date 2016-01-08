package com.open.shiro.dynamic.permissions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.open.shiro.dynamic.permissions.dao.OrganizationDao;
import com.open.shiro.dynamic.permissions.entity.Organization;

/**
 * 
 * The class OrganizationServiceImpl.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public Organization createOrganization(Organization organization) {
		return organizationDao.createOrganization(organization);
	}

	@Override
	public Organization updateOrganization(Organization organization) {
		return organizationDao.updateOrganization(organization);
	}

	@Override
	public void deleteOrganization(Long organizationId) {
		organizationDao.deleteOrganization(organizationId);
	}

	@Override
	public Organization findOne(Long organizationId) {
		return organizationDao.findOne(organizationId);
	}

	@Override
	public List<Organization> findAll() {
		return organizationDao.findAll();
	}

	@Override
	public List<Organization> findAllWithExclude(Organization excludeOraganization) {
		return organizationDao.findAllWithExclude(excludeOraganization);
	}

	@Override
	public void move(Organization source, Organization target) {
		organizationDao.move(source, target);
	}
}
