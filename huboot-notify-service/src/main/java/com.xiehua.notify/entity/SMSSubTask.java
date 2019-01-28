package com.xiehua.notify.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huboot.commons.utils.LocalDateTimeUtils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
@Data
@Document(collection = "sms_sub_task")
public class SMSSubTask {

    @Id
    private String id;
    //
    private String taskId;
    //创建时间
    @JsonFormat(locale = LocalDateTimeUtils.LOCALE, timezone = LocalDateTimeUtils.TIMEZONE, pattern = LocalDateTimeUtils.NORMAL_DATETIME)
    private LocalDateTime createTime;
    //手机号
    private String phones;
    //成功数量
    private Long successNum;
    //失败数量
    private Long failureNum;
    //SMSSendStatusEnum
    private String status;
    //响应求体
    private String responseBody;

}
