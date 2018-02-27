package com.mausoft.inv.mgr.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationUserSecurityDetails extends AuthenticationUser implements UserDetails {
	
	private String uid;
	private String firstName;
	private String lastName;
	private String displayName;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public AuthenticationUserSecurityDetails(String username, Collection<? extends GrantedAuthority> aAuthorities) {
		super(username, null);
		authorities = aAuthorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String aUid) {
		uid = aUid;
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String aDisplayName) {
		displayName = aDisplayName;
	}
}