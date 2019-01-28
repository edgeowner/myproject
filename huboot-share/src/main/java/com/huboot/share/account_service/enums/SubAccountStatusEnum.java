package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum SubAccountStatusEnum implements BaseEnum {

//    wait_pay("待支付"),
    has_pay("已支付"),
//    wait_refund("待退款"),
    has_refund("已退款"),
//    wait_received("待到账"),
    has_freezed("已冻结"),
    has_received("已到账"),
//    wait_writeoff("待核销"),
    has_writeoff("已核销"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
