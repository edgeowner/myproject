package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserEmployeeStatusEnum implements BaseEnum {

    enabled("使用中"),disable("已禁用"),;

    private String showName;
}
