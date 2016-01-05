package com.open.cas.shiro;

import java.io.Serializable;

public class UserPrincipal implements SecurityUser<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6259330053556482314L;
	private Long id;
	private String loginName;
	private String name;
	private String passWord;
	private Integer oprStatus;

	public UserPrincipal() {
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	@Override
	public String getLoginName() {
		return this.loginName;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getPassWord() {
		return passWord;
	}

	public UserPrincipal(Long id, String loginName, String name, String passWord) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.name = name;
		this.passWord = passWord;
	}

	public UserPrincipal(Long id, String loginName, String name, String passWord, Integer oprStatus) {
		this(id, loginName, name, passWord);
		this.oprStatus = oprStatus;
	}

	@Override
	public Integer getOprStatus() {
		return oprStatus;
	}

	public String getSessionid() {
		try {
			return "123";
		} catch (Exception e) {
			return "";
		}
	}
}
