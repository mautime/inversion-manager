package com.mausoft.common.exception;

public class AbstractBaseException extends Exception implements IApplicationException {
	
	protected enum ErrorCode implements IErrorCode {
		DEFAULT_EXCEPTION_CODE("00");
		
		private String errorCode;
		
		private ErrorCode(String aErrorCode) {
			errorCode = aErrorCode;
		}
		
		public String getShortErrorCode() {
			return errorCode;
		}
	}
	protected IErrorCode errorCode;
	
	public AbstractBaseException(String message) {
		this(ErrorCode.DEFAULT_EXCEPTION_CODE, message, null);
	}
	
	public AbstractBaseException(IErrorCode aErrorCode) {
		this(aErrorCode, aErrorCode.getErrorCode(), null);
	}
	
	public AbstractBaseException(IErrorCode aErrorCode, String message) {
		this(aErrorCode, message, null);
	}
	
	public AbstractBaseException(String message, Throwable cause) {
		this(ErrorCode.DEFAULT_EXCEPTION_CODE, message, cause);
	}
	
	public AbstractBaseException(IErrorCode errorCode, Throwable cause) {
		this(errorCode, errorCode.getErrorCode(), cause);
	}
	
	public AbstractBaseException(IErrorCode aErrorCode, String message, Throwable cause) {
		super(message, cause);
		errorCode = aErrorCode;
		
		/*if (cause instanceof AbstractBaseException) {
			errorCode += ((AbstractBaseException) cause).getErrorCode();
		}*/
	}
	
	@Override
	public IErrorCode getErrorCode() {
		return errorCode;
	}
	
	@Override
	public void setErrorCode(IErrorCode aErrorCode) {
		errorCode = aErrorCode;
	}
}