package com.open.spring.aop1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.open.spring.aop1.annotation.LogAnnotation;
import com.open.spring.aop1.dao.UserDao;
import com.open.spring.aop1.entity.User;
import com.open.spring.aop1.service.UserService;

/**
 * 
 * The class UserServiceImpl.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2016年1月8日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * 创建用户
	 * @param user
	 */
	public User createUser(User user) {
		return userDao.createUser(user);
	}

	@Override
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public void deleteUser(Long userId) {
		userDao.deleteUser(userId);
	}

	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		userDao.updateUser(user);
	}

	@Override
	public User findOne(Long userId) {
		return userDao.findOne(userId);
	}

	@Override
	@LogAnnotation
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	@LogAnnotation
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
