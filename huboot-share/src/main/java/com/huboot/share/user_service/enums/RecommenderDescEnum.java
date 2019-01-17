package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecommenderDescEnum implements BaseEnum {

    inner_employ("内部员工"),
    sole_duty("专职推荐人"),
    sign_driver("签约司机"),
    other("其他"),
    ;

    private String showName;
}
