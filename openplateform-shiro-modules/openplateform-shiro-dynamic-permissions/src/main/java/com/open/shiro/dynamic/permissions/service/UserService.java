package com.open.shiro.dynamic.permissions.service;

import java.util.List;
import java.util.Set;

import com.open.shiro.dynamic.permissions.entity.User;

/**
 * 
 * The class UserService.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface UserService {

	/**
	 * 创建用户
	 * @param user
	 */
	public User createUser(User user);

	public User updateUser(User user);

	public void deleteUser(Long userId);

	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword);

	User findOne(Long userId);

	List<User> findAll();

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户名查找其角色
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);

	/**
	 * 根据用户名查找其权限
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);

}
