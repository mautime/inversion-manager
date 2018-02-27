package com.mausoft.inv.mgr.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.mausoft.common.exception.IApplicationException;

public class JwtSecurityException extends AuthenticationException implements IApplicationException {
	private static final String JWT_SECURITY_EXCEPTION_CODE = "JWTSE";
	
	public enum ErrorCode implements IErrorCode {
		JWT_UNKNOWN_EXCEPTION_CODE("00"),
		INVALID_CREDENTIALS_EXCEPTION("01"), 
		EXPIRED_JWT_EXCEPTION_CODE("11");
		
		private String errorCode;
		
		private ErrorCode(String aErrorCode) {
			errorCode = JWT_SECURITY_EXCEPTION_CODE + aErrorCode;
		}
		
		public String getShortErrorCode() {
			return errorCode;
		}
	}
	
	private IErrorCode errorCode;
	
	public JwtSecurityException(ErrorCode aErrorCode, String message, Throwable cause) {
		super(message, cause);
		errorCode = aErrorCode;
	}
	
	public JwtSecurityException(ErrorCode aErrorCode, Throwable cause) {
		this(aErrorCode, aErrorCode.name(), cause);
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