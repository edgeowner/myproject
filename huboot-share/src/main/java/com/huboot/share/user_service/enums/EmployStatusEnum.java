package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmployStatusEnum implements BaseEnum {

    work("在职"),
    leave("离职"),
    ;

    private String showName;
}
