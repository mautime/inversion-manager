package com.mausoft.inv.mgr.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.mausoft.common.exception.IApplicationException;

public class AuthenticationSecurityException extends AuthenticationException implements IApplicationException {
	private static String AUTHENTICATION_SECURITY_EXCEPTION_CODE = "AUTH";
	
	public enum ErrorCode implements IErrorCode {
		UNKNOWN_AUTHENTICATION_EXCEPTION_CODE("00"), 
		INVALID_CREDENTIALS_EXCEPTION_CODE("01");
		
		private String errorCode;
		
		private ErrorCode(String aErrorCode) {
			errorCode = AUTHENTICATION_SECURITY_EXCEPTION_CODE + aErrorCode;
		}
		
		public String getShortErrorCode() {
			return errorCode;
		}
	}
	
	private IErrorCode errorCode;
	
	public AuthenticationSecurityException(IErrorCode aErrorCode, Throwable cause) {
		super(aErrorCode.getErrorCode(), cause);
		errorCode = aErrorCode;
	}
	
	@Override
	public void setErrorCode(IErrorCode aErrorCode) {
		errorCode = aErrorCode;
	}

	@Override
	public IErrorCode getErrorCode() {
		return errorCode;
	}

}
