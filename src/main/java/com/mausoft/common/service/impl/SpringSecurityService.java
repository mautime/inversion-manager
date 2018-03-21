package com.mausoft.common.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mausoft.common.service.ISpringSecurityService;

@Service("springSecurityService")
public class SpringSecurityService implements ISpringSecurityService {

	@Override
	public String getCurrentUser() {
		return (String) getAuthentication().getPrincipal();
	}
	
	@Override
	public Authentication getAuthentication() {
		return (Authentication) SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Object getCurrentUserDetails(){
		return getAuthentication().getDetails();
	}
}