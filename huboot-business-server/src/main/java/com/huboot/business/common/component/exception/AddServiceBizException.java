package com.huboot.business.common.component.exception;

public class AddServiceBizException extends BizException {

	private static final long serialVersionUID = -1827198664611457387L;

	private String bizData;



	public AddServiceBizException() {
		super();
	}


	public AddServiceBizException(String message,String bizData) {
		super(message);
		this.bizData = bizData;
	}

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}
}
