package com.mausoft.inv.mgr.util;

public interface IParameterHolder {
	public Object getParam(String name);
	public <T> T getParam(String name, Class<T> returnType);
}