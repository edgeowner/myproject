package com.huboot.business.common.component.exception;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = -1827198664611457387L;
	//3001 跳转车辆列表页
	protected String code = "400";
	protected String msg;
	private String bizData;

	public static final String RETRY = "retry";
	public static final String DUPLICATE = "duplicate";


	public BizException() {
		super();
	}

	public BizException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.msg = message;
	}

	public BizException(String errorCode, String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = errorCode;
		this.msg = message;
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
		this.msg = message;
	}

	public BizException(String message) {
		super(message);
		this.msg = message;
	}

	public BizException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public BizException(String errorCode, String bizData, String message) {
		super(message);
		this.bizData = bizData;
		this.code = errorCode;
	}

	public BizException(Throwable cause) {
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

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}
}
