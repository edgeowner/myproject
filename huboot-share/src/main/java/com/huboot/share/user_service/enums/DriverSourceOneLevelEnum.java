package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DriverSourceOneLevelEnum implements BaseEnum {

    company_promotion("公司推广"),
    person_promotion("常规推广者"),
    agent_promotion("活动推广者"),
    other("其他"),
    ;

    private String showName;
}
