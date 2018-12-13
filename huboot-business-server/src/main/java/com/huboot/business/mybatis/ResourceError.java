package com.huboot.business.mybatis;

/**
 * 
* @ClassName: ResourceError 
* @Description: 错误信息
* @author zhangtiebin@hn-zhixin.com
* @date 2015年7月2日 上午10:12:48 
*
 */
public class ResourceError {
    private int code;
    private String message;
    private String bizData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }
}
