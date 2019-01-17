package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeixinAuthStatusEnum implements BaseEnum {

    authorized("已授权"),
    unauthorized("未授权"),
    ;

    private String showName;
}
