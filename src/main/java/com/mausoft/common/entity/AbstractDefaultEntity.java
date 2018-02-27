package com.mausoft.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDefaultEntity implements IEntity {
	private static final long serialVersionUID = 2953410301226147282L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	public AbstractDefaultEntity() {}
	
	public AbstractDefaultEntity(Long aId) {
		id = aId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long aId) {
		id = aId;
	}
}