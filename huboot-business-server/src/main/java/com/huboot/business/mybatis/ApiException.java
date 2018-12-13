package com.huboot.business.mybatis;



import com.huboot.business.common.utils.JsonUtils;
import feign.FeignException;

/**
 * @Author by maitian on 14/12/18.
 */
public class ApiException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3542451561250518138L;
	private int code;
	private String bizData;
	
	public ApiException(){
	}

	public ApiException(FeignException feignException) {
		super(JsonUtils.fromJsonToMap(feignException.getMessage().split("content:\n")[1]).get("message").toString());
		this.code =  400;
	}

	public ApiException(FeignException feignException, String bizData) {
		super(JsonUtils.fromJsonToMap(feignException.getMessage().split("content:\n")[1]).get("message").toString());
		this.bizData = bizData;
		this.code =  400;
	}
    public ApiException(ErrorCodeEnum error){
    	super(error.getDescription());
    	this.code =  error.getCode();
    }
    public ApiException(ErrorCodeEnum error, String message){
    	super(message);
    	this.code =  error.getCode();
    }
	public ApiException(String message){
		super(message);
		this.code =  ErrorCodeEnum.SystemError.getCode();
	}
	public ApiException(int code, String message){
		super(message);
		this.code =  code;
	}
	public ApiException(String message, String bizData){
		super(message);
		this.bizData = bizData;
		this.code =  ErrorCodeEnum.SystemError.getCode();
	}

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}

	public int getCode() {
		return code;
	}


    
}
