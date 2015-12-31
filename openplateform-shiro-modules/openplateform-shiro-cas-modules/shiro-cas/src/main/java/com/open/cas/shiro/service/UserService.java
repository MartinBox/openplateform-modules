package com.open.cas.shiro.service;

import java.util.List;

import com.open.cas.shiro.SecurityUser;
import com.open.cas.shiro.UserConst.UserStaEnum;
import com.open.cas.shiro.entity.User;

public interface UserService extends HibernateBaseService<User, Long> {

	public SecurityUser<Long> findUserByAccount(String userName, UserStaEnum serStaEnum);

	/**
	 * 根据用户id查询角色组code
	 * @param userId
	 * @return
	 * @author liuheng
	 * @date 2015年8月28日 下午3:40:07
	 */
	public List<String> getSecurityGroups(Long userId);

	/**
	 * 根据用户id查询权限码
	 * @param userId
	 * @return
	 * @author liuheng
	 * @date 2015年8月28日 下午3:39:31
	 */
	public List<String> getSecurityPermissions(Long userId);

}
