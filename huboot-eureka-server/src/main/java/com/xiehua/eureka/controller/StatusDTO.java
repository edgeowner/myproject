package com.xiehua.eureka.controller;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class StatusDTO {

    private String instanceId;

    private Integer status;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
