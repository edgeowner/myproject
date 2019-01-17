package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrganizationSystemEnum implements BaseEnum {

    default_unknown("默认系统"),
    xiehua_admin("内管端"),
    wyc_shop_admin("网约车店铺管理端"),
    wyc_driver_miniapp("网约车店铺司机端"),
    zk_shop("直客商家端"),
    zk_user("直客用户端"),
    ;

    private String showName;
}
