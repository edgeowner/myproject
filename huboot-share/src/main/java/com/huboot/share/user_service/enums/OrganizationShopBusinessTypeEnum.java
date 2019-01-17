package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrganizationShopBusinessTypeEnum implements BaseEnum {

    default_type("默认"),;

    private String showName;
}
