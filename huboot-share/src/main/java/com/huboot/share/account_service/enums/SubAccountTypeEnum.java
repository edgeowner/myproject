package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum SubAccountTypeEnum implements BaseEnum {

    tripartite("三方账户"),
    balance("余额账户"),
    redpackage("红包账户"),
    brokerage("佣金账户"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
