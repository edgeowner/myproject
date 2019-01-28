package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PayPlatformEnum implements BaseEnum {

    weixin("微信"),
    alipay("支付宝"),
    china_union("中国银联"),
    offline("线下"),
    moni("模拟"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
