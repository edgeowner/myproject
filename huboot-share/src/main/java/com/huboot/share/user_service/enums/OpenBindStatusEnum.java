package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OpenBindStatusEnum implements BaseEnum {

    bind("已绑定"),
    unbind("未绑定"),
    ;

    private String showName;
}
