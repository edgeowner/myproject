package com.xiehua.notify.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huboot.commons.utils.LocalDateTimeUtils;
import com.xiehua.notify.config.BeanConfig;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
@Data
@Document(collection = "sms_task")
@ToString
public class SMSTask {

    @Id
    private String id;
    //创建时间
    @JsonFormat(locale = LocalDateTimeUtils.LOCALE, timezone = LocalDateTimeUtils.TIMEZONE, pattern = LocalDateTimeUtils.NORMAL_DATETIME)
    private LocalDateTime createTime;
    //短信签名
    private String sign;
    //短信美容
    private String content;
    //手机号
    private String phones;
    //成功数量
    private Long successNum;
    //失败数量
    private Long failureNum;
    //状态
    private String status;
    //类型 SMSTaskTypeEnum
    private String type;
    //
    private String code;
    //
    private String remark;
}
