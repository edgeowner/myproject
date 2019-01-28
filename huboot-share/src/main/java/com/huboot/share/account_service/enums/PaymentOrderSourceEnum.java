package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PaymentOrderSourceEnum implements BaseEnum {

    //交易
    zkrent_rent("直客租金"),
    zkrent_deposit("直客租车押金"),
    zkrent_violation_deposit("违章押金"),
    thdcrent("同行调车"),
    zkmall("直客商城"),
    zkshuttle("直客接送机"),
    risk("风控查询"),

    //奖励
    thaddcar("同行添加车辆奖励"),
    thorder("同行下单奖励"),
    wyctdriver("佣金收入"), //网约车推荐司机奖励
    thlogin("同行登陆返现"),
    thlottery("同行抽奖返现"),

    //提现
    thmerchant_cash("同行商户提现"),

    //核销
    wycdriver_writeoff("佣金提现"), //网约车司佣金核销
    ;
    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
