package com.mausoft.inv.mgr.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("globalParameters")
@ConfigurationProperties(prefix="app.global")
public class GlobalParameters implements IParameterHolder{
	private Map<String, Object> params;
	private static IParameterHolder instance;
	
	private GlobalParameters() {
		params = new HashMap<>();
	}
	
	public static IParameterHolder getInstance() {
		if (instance == null) {
			instance = new GlobalParameters();
		}
		
		return instance;
	}
	
	public Map<String, Object> getParams(){
		return params;
	}
	
	public <T> T getParam(String name, Class<T> returnType) {
		return returnType.cast(getParam(name));
	}
	
	public Object getParam(String name) {
		return params.get(name);
	}
}