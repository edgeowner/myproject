package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ThirdPlatformEnum implements BaseEnum {

    miniapp("小程序"), mp("公众号");

    private String showName;
}
