package com.mausoft.inv.mgr.junit;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mausoft.common.repository.impl.BaseRepository;

@ComponentScan(basePackages= {"com.mausoft.inv.mgr.repository.impl"})
@EntityScan(basePackages= {"com.mausoft.inv.mgr.entity"})
@EnableJpaRepositories(basePackages= {"com.mausoft.inv.mgr.repository"}, repositoryBaseClass=BaseRepository.class)
public class ApplicationTest {
	
}