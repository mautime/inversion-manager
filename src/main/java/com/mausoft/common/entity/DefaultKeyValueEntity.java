package com.mausoft.common.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DefaultKeyValueEntity<T> extends AbstractDefaultEntity {
	private static final long serialVersionUID = -487749362678863864L;
	
	private String name;
	private T value;
	
	public DefaultKeyValueEntity() {}
	
	public DefaultKeyValueEntity(String aName, T aValue) {
		this(null, aName, aValue);
	}
	
	public DefaultKeyValueEntity(Long aId, String aName, T aValue) {
		super(aId);
		
		name = aName;
		value = aValue;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String aName) {
		name = aName;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T aValue) {
		value = aValue;
	}
}