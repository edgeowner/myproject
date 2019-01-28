package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PayTypeEnum implements BaseEnum {

    //微信
    wxpay_jsapi("微信公众号支付"),
    wxpay_native("微信原生扫码支付"),
    wxpay_app("微信app支付"),
    wxpay_lite("微信小程序支付"),

    //支付宝
    alipay_wap("支付宝手机网页支付"),

    //中国银联
    union_agentpay("银联代付"),

    //线下
    offline_person("线下人工操作"),
    offline_system("线下系统操作"),

    //模拟
    xiehua_moni("平台模拟"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
