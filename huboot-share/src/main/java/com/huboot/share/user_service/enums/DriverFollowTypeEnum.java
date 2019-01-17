package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DriverFollowTypeEnum implements BaseEnum {

    telphone("电话"),
    inshop("到店"),
    ;

    private String showName;
}
