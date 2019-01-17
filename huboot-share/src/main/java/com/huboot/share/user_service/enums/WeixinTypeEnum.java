package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeixinTypeEnum implements BaseEnum {

    wxmp("公众号"),
    miniapp("小程序"),
    ;

    private String showName;
}
