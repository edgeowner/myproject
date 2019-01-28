package com.xiehua.notify.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
@Data
public class SMSLogPagerDTO {

    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("发送状态")
    private String status;
    @ApiModelProperty("发送时间")
    private String sendTime;
    @ApiModelProperty("发送内容")
    private String content;
    @ApiModelProperty("短信任务ID")
    private String taskId;
    @ApiModelProperty("场景码")
    private String code;

}
