/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.open.cas.shiro;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.open.cas.shiro.service.UserService;

public class ShiroDbRealm extends CasRealm {

	protected UserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	//	@Override
	//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
	//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
	//		SecurityUser<Serializable> user = userService.findUserByLoginName(token.getUsername());
	//		if (user != null) {
	//			if (user.isDisabled()) {
	//				throw new DisabledAccountException();
	//			}
	//
	////			byte[] salt = EncodeUtils.decodeHex(user.getSalt());
	//			String password = userService.getLoginPassword(user.getId());
	//			//return new SimpleAuthenticationInfo(userService.createPrincipal(user), password, ByteSource.Util.bytes(salt), getName());
	//			return new SimpleAuthenticationInfo(userService.createPrincipal(user), password, getName());
	//		} else {
	//			return null;
	//		}
	//	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	/*@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SecurityUser<Long> user = userService.findUserByAccount(token.getUsername(), UserStaEnum.ENABLE);
		if (user != null) {
			token.setUsername(user.getLoginName());
			return new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
		} else {
			return null;
		}
	}
	*/
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();
		/*SecurityUser<Long> user = (SecurityUser) principal;//userHessianService.findUserByPrincipal(principal);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (user != null) {
			List<String> roles = userService.getSecurityGroups(user.getId());
			List<String> auths = userService.getSecurityPermissions(user.getId());
			if (null != roles && !roles.isEmpty()) info.addRoles(roles);
			if (null != auths && !auths.isEmpty()) info.addStringPermissions(auths);

		}
		return info;*/
		return null;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Settings.get("hash.algorithm", Settings.HASH_ALGORITHM));
		//		matcher.setHashIterations(Settings.get("hash.interations", Settings.HASH_INTERATIONS));
		//		setCredentialsMatcher(matcher);

		//该句作用是重写shiro的密码验证，让shiro用我自己的验证  
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	/** 
	* 将一些数据放到ShiroSession中,以便于其它地方使用 
	* @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
	*/
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
