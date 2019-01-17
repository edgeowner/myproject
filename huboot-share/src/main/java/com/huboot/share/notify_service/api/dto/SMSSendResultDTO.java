package com.huboot.share.notify_service.api.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Data
public class SMSSendResultDTO {

    private boolean success;

    private String taskId;

    public SMSSendResultDTO() {
    }

    public SMSSendResultDTO(boolean success, String taskId) {
        this.success = success;
        this.taskId = taskId;
    }
}
