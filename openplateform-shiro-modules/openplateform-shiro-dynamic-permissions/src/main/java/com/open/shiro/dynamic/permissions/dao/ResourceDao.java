package com.open.shiro.dynamic.permissions.dao;

import java.util.List;

import com.open.shiro.dynamic.permissions.entity.Resource;

/**
 * 
 * The class ResourceDao.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface ResourceDao {

	public Resource createResource(Resource resource);

	public Resource updateResource(Resource resource);

	public void deleteResource(Long resourceId);

	Resource findOne(Long resourceId);

	List<Resource> findAll();

}
