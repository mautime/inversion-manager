package com.mausoft.common.model;

public class KeyValue<T> implements IModel {
	private static final long serialVersionUID = -4908710924441993394L;
	
	private String name;
	private T value;
	
	public KeyValue(String aName, T aValue) {
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