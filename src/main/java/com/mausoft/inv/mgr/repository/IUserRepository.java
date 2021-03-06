package com.mausoft.inv.mgr.repository;

import org.springframework.stereotype.Repository;

import com.mausoft.common.repository.IBaseRepository;
import com.mausoft.inv.mgr.entity.User;

@Repository("userRepository")
public interface IUserRepository extends IBaseRepository<User, Long> {
	public Long countByUsername(String email);
	public User findByUsername(String username);
}
