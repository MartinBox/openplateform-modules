package com.open.cas.shiro.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user_info", catalog = "plateform")
public class User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4642036673713273887L;
	private Long userId;
	private String loginName;
	//	private Long role_group_id;
	private Integer userSta;
	private String userName;
	private Integer gender;
	private String pwd;
	private String mobile;
	private String email;
	private Integer pwdWrongTimes;
	private Integer pwdWrongTimesTotal;
	private Date pwdWrongLastTime;
	private Integer isPwdChanged;
	private String remarks;// 备注
	private Date createDate;// 创建时间
	private Date loginDate;// 最后登录时间

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "user_code")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "user_sta")
	public Integer getUserSta() {
		return userSta;
	}

	public void setUserSta(Integer userSta) {
		this.userSta = userSta;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_gender")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Column(name = "user_pwd")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "user_mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "user_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "pwd_wr_tm")
	public Integer getPwdWrongTimes() {
		return pwdWrongTimes;
	}

	public void setPwdWrongTimes(Integer pwdWrongTimes) {
		this.pwdWrongTimes = pwdWrongTimes;
	}

	@Column(name = "pwd_wr_tm_total")
	public Integer getPwdWrongTimesTotal() {
		return pwdWrongTimesTotal;
	}

	public void setPwdWrongTimesTotal(Integer pwdWrongTimesTotal) {
		this.pwdWrongTimesTotal = pwdWrongTimesTotal;
	}

	@Column(name = "pwd_wr_last_dt")
	public Date getPwdWrongLastTime() {
		return pwdWrongLastTime;
	}

	public void setPwdWrongLastTime(Date pwdWrongLastTime) {
		this.pwdWrongLastTime = pwdWrongLastTime;
	}

	@Column(name = "isPswChanged")
	public Integer getIsPwdChanged() {
		return isPwdChanged;
	}

	public void setIsPwdChanged(Integer isPwdChanged) {
		this.isPwdChanged = isPwdChanged;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "login_date")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

}
