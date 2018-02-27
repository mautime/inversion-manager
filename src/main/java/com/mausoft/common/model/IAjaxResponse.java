package com.mausoft.common.model;

import org.springframework.http.HttpStatus;

public interface IAjaxResponse extends IModel{
	public void setMessage(String message);
	public String getMessage();
	public void setHttpStatus(HttpStatus httpStatus);
	public HttpStatus getHttpStatus();
	public void addProperty(String name, Object value);
	public Object getProperty(String name);
	public <T> T getProperty(String name, Class<T> clazz);
}