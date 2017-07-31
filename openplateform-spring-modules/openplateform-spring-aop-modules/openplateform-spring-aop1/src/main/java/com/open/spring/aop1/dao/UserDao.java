package com.open.spring.aop1.dao;

import java.util.List;

import com.open.spring.aop1.entity.User;

/**
 * 
 * The class UserDao.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface UserDao {

	public User createUser(User user);

	public User updateUser(User user);

	public void deleteUser(Long userId);

	User findOne(Long userId);

	List<User> findAll();

	User findByUsername(String username);

}
