package com.mausoft.common.service;

import org.springframework.security.core.Authentication;

import com.mausoft.inv.mgr.entity.User;

public interface ISpringSecurityService {
	public String getCurrentUser();
	public Authentication getAuthentication();
	public Object getCurrentUserDetails();
	public User getUser();
}