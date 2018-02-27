package com.mausoft.common.exception;

public interface IApplicationException {
	public static interface IErrorCode {
		default String getErrorCode() {
			return name();
		}
		
		String getShortErrorCode();
		
		String name();
	}
	
	public void setErrorCode(IErrorCode errorCode);
	public IErrorCode getErrorCode();
}