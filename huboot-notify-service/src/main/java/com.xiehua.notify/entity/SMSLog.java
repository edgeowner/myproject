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
@Document(collection = "sms_log")
public class SMSLog {

    @Id
    private String id;
    //创建时间
    @JsonFormat(locale = LocalDateTimeUtils.LOCALE, timezone = LocalDateTimeUtils.TIMEZONE, pattern = LocalDateTimeUtils.NORMAL_DATETIME)
    private LocalDateTime createTime;
    //手机号
    private String phone;
    //状态
    private String status;
    //
    private String taskId;
    //
    private String subTaskId;
    //
    private String code;
    //
    private String errorReason;
}
