package com.huboot.business.base_model.login.sso.client.exception;

public class UnAuthorizationException extends RuntimeException{

	private static final long serialVersionUID = -1827198664611457387L;
	private String code = "401";
	private String msg;

	public UnAuthorizationException() {
		super();
	}

	public UnAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnAuthorizationException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = errorCode;
	}

	public UnAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnAuthorizationException(String message) {
		super(message);
	}

	public UnAuthorizationException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public UnAuthorizationException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
