package com.huboot.business.common.component.exception;

import com.huboot.business.common.utils.JsonUtils;
import feign.FeignException;

import java.io.Serializable;

public class ExceptionResponse implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String code = "400";

    private String message;

    private String bizData;

    public ExceptionResponse() {

    }

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 如果FeignException里的content不是按照ExceptionResponse处理错误的，则忽略
     * @param e
     * @return
     */
    public static ExceptionResponse getExceptionResponse(FeignException e) {
        if(e == null) {
            return new ExceptionResponse();
        }
        String message = e.getMessage();
        String content = message.split("content:\n")[1];
        try {
            return JsonUtils.fromJson(content, ExceptionResponse.class);
        } catch (Exception e1) {
            return new ExceptionResponse();
        }
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

}
