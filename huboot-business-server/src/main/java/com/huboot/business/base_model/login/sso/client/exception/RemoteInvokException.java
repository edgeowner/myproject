package com.huboot.business.base_model.login.sso.client.exception;

public class RemoteInvokException extends RuntimeException{

	private static final long serialVersionUID = -1827198664611457387L;
	private String code = "400";
	private String msg;
    private String bizData;

	public RemoteInvokException() {
		super();
	}

	public RemoteInvokException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RemoteInvokException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = errorCode;
	}

	public RemoteInvokException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteInvokException(String message) {
		super(message);
	}

	public RemoteInvokException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public RemoteInvokException(Throwable cause) {
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
