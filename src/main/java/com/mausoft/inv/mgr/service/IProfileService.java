package com.mausoft.inv.mgr.service;

import com.mausoft.inv.mgr.entity.User;

public interface IProfileService {
	public User register(User user) throws Exception;
	public User getProfile(String username);
	public boolean checkExisting(String username);
}