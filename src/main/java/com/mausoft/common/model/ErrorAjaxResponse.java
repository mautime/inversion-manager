package com.mausoft.common.model;

import org.springframework.http.HttpStatus;

public class ErrorAjaxResponse extends AjaxResponse {
	private static final long serialVersionUID = 4710521533656961394L;
	
	public ErrorAjaxResponse(String aErrorCode, String message, Throwable e, HttpStatus httpStatus){
		super("error", httpStatus);
		addProperty("error", aErrorCode);
		addProperty("cause", e);
		setMessage(message);
	}
	
	public ErrorAjaxResponse(String aErrorCode, String message, HttpStatus httpStatus) {
		this(aErrorCode, message, null, httpStatus);
	}
	
	public ErrorAjaxResponse(String aErrorCode, Throwable e, HttpStatus httpStatus){
		this(aErrorCode, aErrorCode, e, httpStatus);
	}
	
	public ErrorAjaxResponse(String aErrorCode, HttpStatus httpStatus){
		this(aErrorCode, null, null, httpStatus);
	}
}