package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatusEnum implements BaseEnum {

    default_status("默认状态"),;

    private String showName;
}
