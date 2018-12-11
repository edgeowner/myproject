package com.huboot.business.base_model.login.sso.client.exception;

public class SsoException extends RuntimeException{

	private static final long serialVersionUID = -1827198664611457387L;
	private String code = "403";
	private String msg;

	public SsoException() {
		super();
	}

	public SsoException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SsoException(String errorCode, String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = errorCode;
	}

	public SsoException(String message, Throwable cause) {
		super(message, cause);
	}

	public SsoException(String message) {
		super(message);
	}

	public SsoException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public SsoException(Throwable cause) {
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
