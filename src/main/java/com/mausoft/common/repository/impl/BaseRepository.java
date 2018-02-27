package com.mausoft.common.repository.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import com.mausoft.common.entity.IEntity;
import com.mausoft.common.repository.IBaseRepository;

public class BaseRepository<T extends IEntity, ID extends Serializable> extends JpaSpecificationExecutorWithProjectionImpl<T, ID> implements IBaseRepository<T, ID> {
	protected final EntityManager entityManager;
	
	public BaseRepository(JpaEntityInformation<T, ID> jpaEntityInformation, EntityManager aEntityManager) {
		super(jpaEntityInformation, aEntityManager);
		
		entityManager = aEntityManager;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}
}