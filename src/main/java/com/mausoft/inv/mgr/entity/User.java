package com.mausoft.inv.mgr.entity;

import javax.persistence.Entity;

import com.mausoft.common.entity.AbstractDefaultEntity;

@Entity
public class User extends AbstractDefaultEntity {
	private static final long serialVersionUID = 1734133062276268705L;
	
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	
	public User() {}
	
	public User(String aUsername) {
		username = aUsername;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String aUsername) {
		username = aUsername;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String aEmail) {
		email = aEmail;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String aFirstName) {
		firstName = aFirstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String aLastName) {
		lastName = aLastName;
	}
}