package com.mausoft.inv.mgr.security.model;

import com.mausoft.common.model.IModel;

public class AuthenticationUser implements IModel {
	private String username;
	private String password;
	
	public AuthenticationUser() {}
	
	public AuthenticationUser(String aUsername, String aPassword) {
		username = aUsername;
		password = aPassword;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String aUsername) {
		username = aUsername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String aPassword) {
		password = aPassword;
	}
}