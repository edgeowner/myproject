package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DriverHireFollowStatusEnum implements BaseEnum {

    wait_allocation("待分配"),
    wait_follow("待跟进"),
    following("跟进中"),
    signing("已签约"),
    fail("未通过"),
    ;

    private String showName;
}
