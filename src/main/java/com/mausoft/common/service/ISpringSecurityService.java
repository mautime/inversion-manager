package com.mausoft.common.service;

import org.springframework.security.core.Authentication;

public interface ISpringSecurityService {
	public String getCurrentUser();
	public Authentication getAuthentication();
	public Object getCurrentUserDetails();
}