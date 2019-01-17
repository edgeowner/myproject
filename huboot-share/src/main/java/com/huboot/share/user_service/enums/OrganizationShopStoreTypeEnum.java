package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrganizationShopStoreTypeEnum implements BaseEnum {

    store("门店"),
    take_car("提车点"),
    other("其他"),
    ;

    private String showName;
}
