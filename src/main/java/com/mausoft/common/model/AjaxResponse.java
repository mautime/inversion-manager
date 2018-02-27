package com.mausoft.common.model;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

public class AjaxResponse extends HashMap<String, Object> implements IAjaxResponse {
	private static final long serialVersionUID = 3839051046940478659L;
	
	public AjaxResponse(){}
	
	public AjaxResponse(HttpStatus aHttpStatus){
		this();
		put("httpStatus", aHttpStatus);
	}
	
	public AjaxResponse(String aMessage, HttpStatus aHttpStatus){
		this();
		put("message", aMessage);
		put("httpStatus", aHttpStatus);
	}

	@Override
	public void setMessage(String message) {
		put("message", message);
	}

	@Override
	public String getMessage() {
		return getProperty("message", String.class);
	}
	
	@Override
	public void setHttpStatus(HttpStatus httpStatus) {
		put("httpStatus", httpStatus);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return getProperty("httpStatus", HttpStatus.class);
	}

	@Override
	public void addProperty(String name, Object value) {
		put(name, value);
	}

	@Override
	public Object getProperty(String name) {
		return get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProperty(String name, Class<T> clazz) {
		return (get(name) != null) ? (T) get(name) : null;
	}
}