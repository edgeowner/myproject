package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxmpMenuLevelEnum implements BaseEnum {

    frist("一级菜单"), //
    second("二级菜单") //
    ;

    private String showName;
}
