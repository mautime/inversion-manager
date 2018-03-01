package com.mausoft.inv.mgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mausoft.common.entity.IEntity;

@Entity
public class User implements IEntity {
	private static final long serialVersionUID = 1734133062276268705L;
	
	@Id
	@Column(columnDefinition="VARCHAR")
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	
	public User() {}
	
	public User(String aEmail) {
		email = aEmail;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String aPassword) {
		password = aPassword;
	}
}