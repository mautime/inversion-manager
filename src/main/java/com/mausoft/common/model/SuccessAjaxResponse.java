package com.mausoft.common.model;

import org.springframework.http.HttpStatus;

public class SuccessAjaxResponse extends AjaxResponse {
	private static final long serialVersionUID = 4710521533656961394L;

	public SuccessAjaxResponse(){
		super("success", HttpStatus.OK);
	}
	
	public SuccessAjaxResponse(String propertyName, Object value) {
		this();
		
		addProperty(propertyName, value);
	}
}