package com.open.cas.shiro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.open.cas.shiro.SecurityUser;
import com.open.cas.shiro.UserPrincipal;
import com.open.cas.shiro.UserConst.UserStaEnum;
import com.open.cas.shiro.dao.AuthDao;
import com.open.cas.shiro.dao.RoleDao;
import com.open.cas.shiro.dao.UserDao;
import com.open.cas.shiro.entity.Auth;
import com.open.cas.shiro.entity.Role;
import com.open.cas.shiro.entity.User;
import com.open.cas.shiro.service.UserService;
import com.plateform.common.util.CollectionsUtils;

@Service("userService")
public class UserServiceImpl extends HibernateBaseServiceImpl<User, Long> implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 938857961207845105L;

	@Autowired
	private AuthDao authDao;

	@Autowired
	private RoleDao roleDao;

	@Resource
	private void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}

	@Override
	public List<String> getSecurityGroups(Long userId) {
		Role role = roleDao.findById(1001L);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		List<String> list = CollectionsUtils.extractToList(roles, "code");
		return list;

	}

	@Override
	public List<String> getSecurityPermissions(Long userId) {
		Auth auth = authDao.findById(1L);
		List<Auth> auths = new ArrayList<>();
		auths.add(auth);
		List<String> list = CollectionsUtils.extractToList(auths, "authCode");
		return list;

	}

	@Override
	public SecurityUser<Long> findUserByAccount(String userName, UserStaEnum serStaEnum) {
		// TODO Auto-generated method stub
		List<User> userList = super.findByProperty("loginName", userName);
		if (null == userList || userList.isEmpty()) {
			return null;
		}
		User user = userList.get(0);
		return new UserPrincipal(user.getUserId(), user.getLoginName(), user.getUserName(), user.getPwd(), user.getUserSta());

	}
}
