package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeixinMessageNodeEnum implements BaseEnum {
    car_violate_notify("车辆违章通知"),
    ;

    private String showName;
}
