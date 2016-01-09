package com.open.shiro.dynamic.permissions.dao;

import java.util.List;

import com.open.shiro.dynamic.permissions.entity.Role;

/**
 * 
 * The class RoleDao.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface RoleDao {

	public Role createRole(Role role);

	public Role updateRole(Role role);

	public void deleteRole(Long roleId);

	public Role findOne(Long roleId);

	public List<Role> findAll();
}
