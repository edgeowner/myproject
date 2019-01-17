package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonalSexEnum implements BaseEnum {

    man("男"),
    woman("女"),;

    private String showName;
}
