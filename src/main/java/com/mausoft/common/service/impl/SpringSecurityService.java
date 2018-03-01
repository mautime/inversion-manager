package com.mausoft.common.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mausoft.common.service.ISpringSecurityService;

@Service("springSecurityService")
public class SpringSecurityService implements ISpringSecurityService {

	@Override
	public String getCurrentUser() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	}
}