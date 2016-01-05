package com.open.cas.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class SimpleCasRealm extends CasRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<String>();
		roles.add("dd");
		authorizationInfo.setRoles(roles);
		Set<String> perm = new HashSet<String>();
		perm.add("dd");
		authorizationInfo.setStringPermissions(perm);

		return authorizationInfo;
	}
}
