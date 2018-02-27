package com.mausoft.common.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.mausoft.common.entity.IEntity;

@NoRepositoryBean
public interface IBaseRepository<T extends IEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, JpaSpecificationExecutorWithProjection<T>{
	public EntityManager getEntityManager();
	public void refresh(T entity);
}