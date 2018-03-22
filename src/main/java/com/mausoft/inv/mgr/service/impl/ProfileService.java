package com.mausoft.inv.mgr.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mausoft.inv.mgr.entity.User;
import com.mausoft.inv.mgr.repository.IUserRepository;
import com.mausoft.inv.mgr.service.IProfileService;

@Service("profileService")
public class ProfileService implements IProfileService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public User register(User user) throws Exception{
		User result = null;
		
		if (checkExisting(user.getUsername())) {
			//Throw application exception to indicate the user already exists
			throw new Exception();
		}
		
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		result = userRepository.saveAndFlush(user);
		
		return result;
	}
	
	@Override
	public User getProfile(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public boolean checkExisting(String username) {
		return StringUtils.isBlank(username) || userRepository.countByUsername(username) > 0;
	}
}
