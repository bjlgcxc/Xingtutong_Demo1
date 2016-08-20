package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfo implements Serializable{
	
	private String userName;
	private String password;
	private String lastLogin;
	private Integer loginCount;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
}
