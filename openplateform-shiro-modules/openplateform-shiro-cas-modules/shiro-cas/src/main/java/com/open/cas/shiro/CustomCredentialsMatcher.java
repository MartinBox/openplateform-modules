package com.open.cas.shiro;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.open.cas.shiro.util.EncodeUtils;
import com.open.cas.shiro.util.PasswordUtils;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken tokenCredentials = (UsernamePasswordToken) token;
		Object accountCredentials = getCredentials(info);
		SecurityUser<Serializable> user = (SecurityUser<Serializable>) info.getPrincipals().asList().get(0);
		return checkPwd(tokenCredentials, accountCredentials, user);
	}

	private boolean checkPwd(UsernamePasswordToken tokenCredentials, Object accountCredentials, SecurityUser<Serializable> user) {
		String passWord = String.valueOf(tokenCredentials.getPassword());
		String salt = EncodeUtils.encodeHex(tokenCredentials.getUsername().getBytes());
		System.out.println("salt:" + salt);
		String passwordEncoded = PasswordUtils.encode(passWord, salt);
		System.out.println("passwordEncoded:" + passwordEncoded);
		return equals(passwordEncoded, accountCredentials);
	}
}
