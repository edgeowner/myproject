package com.task.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
@ConfigurationProperties(prefix = "xiehua.task.client")
public class TaskClientProperties {

    private boolean register = true;

    private String taskServerName = "task-server";

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getTaskServerName() {
        return taskServerName;
    }

    public void setTaskServerName(String taskServerName) {
        this.taskServerName = taskServerName;
    }

}
