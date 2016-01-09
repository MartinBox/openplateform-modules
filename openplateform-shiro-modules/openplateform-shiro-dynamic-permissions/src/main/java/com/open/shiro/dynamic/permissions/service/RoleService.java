package com.open.shiro.dynamic.permissions.service;

import java.util.List;
import java.util.Set;

import com.open.shiro.dynamic.permissions.entity.Role;

/**
 * 
 * The class RoleService.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface RoleService {

	public Role createRole(Role role);

	public Role updateRole(Role role);

	public void deleteRole(Long roleId);

	public Role findOne(Long roleId);

	public List<Role> findAll();

	/**
	 * 根据角色编号得到角色标识符列表
	 * @param roleIds
	 * @return
	 */
	Set<String> findRoles(Long... roleIds);

	/**
	 * 根据角色编号得到权限字符串列表
	 * @param roleIds
	 * @return
	 */
	Set<String> findPermissions(Long[] roleIds);
}
