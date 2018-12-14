package com.huboot.business.base_model.ali_service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wjc on 2017/5/11.
 */
public class SmsVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("短信发送次数")
    private Integer sendNum ;
    @ApiModelProperty("提示内容")
    private String content ;

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
