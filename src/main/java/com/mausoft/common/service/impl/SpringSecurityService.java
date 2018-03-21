package com.mausoft.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mausoft.common.service.ISpringSecurityService;
import com.mausoft.inv.mgr.entity.User;
import com.mausoft.inv.mgr.repository.IUserRepository;

@Service("springSecurityService")
public class SpringSecurityService implements ISpringSecurityService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public User getUser() {
		return userRepository.findByUsername(getCurrentUser());
	}
	
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