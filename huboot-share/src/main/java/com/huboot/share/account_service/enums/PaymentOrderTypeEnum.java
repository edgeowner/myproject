package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PaymentOrderTypeEnum implements BaseEnum {


    trade_pay("交易支付"), //(三方账户 -> 余额账户)
    trade_refund("交易退款"), //(余额账户 -> 三方账户)
    transfer("转账"),
    withdraw("提现"),
    reward("奖励"),
    writeoff("核销"),
    correct("冲正"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
