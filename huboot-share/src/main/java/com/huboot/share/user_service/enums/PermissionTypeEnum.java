package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PermissionTypeEnum implements BaseEnum {

    Menu("菜单"), API("接口、事件、按钮");
    private String showName;

}
