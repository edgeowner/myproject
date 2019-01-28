package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PaymentDiscountTypeEnum implements BaseEnum {

    //
    redpackage("红包优惠"),
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }

}
