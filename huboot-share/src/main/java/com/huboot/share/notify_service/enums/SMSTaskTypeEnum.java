package com.huboot.share.notify_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum SMSTaskTypeEnum implements BaseEnum {

    notice("通知类型"),
    marketing("营销类型"),
    ;

    private String showName;

}
