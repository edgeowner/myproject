package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DriverFollowContactEnum implements BaseEnum {

    no_contact("未接通"),
    has_contact("已接通"),
    ;

    private String showName;
}
