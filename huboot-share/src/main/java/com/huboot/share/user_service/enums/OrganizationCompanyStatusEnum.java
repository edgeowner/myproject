package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrganizationCompanyStatusEnum implements BaseEnum {

    auditing("审核中"),
    audited("已审核"),
    returned("已退回"),
    ;

    private String showName;
}
