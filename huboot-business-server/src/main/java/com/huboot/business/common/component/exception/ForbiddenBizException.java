package com.huboot.business.common.component.exception;

public class ForbiddenBizException extends RuntimeException {

	private static final long serialVersionUID = -1827198664611457387L;
	private String code = "400";
	private String msg;

	public static final String CLEAR_LOCAL_TOKEN = "clear_local_token";


	public ForbiddenBizException() {
		super();
	}

	public ForbiddenBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.msg = message;
	}

	public ForbiddenBizException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = errorCode;
		this.msg = message;
	}

	public ForbiddenBizException(String message, Throwable cause) {
		super(message, cause);
		this.msg = message;
	}

	public ForbiddenBizException(String message) {
		super(message);
		this.msg = message;
	}

	public ForbiddenBizException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public ForbiddenBizException(Throwable cause) {
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
