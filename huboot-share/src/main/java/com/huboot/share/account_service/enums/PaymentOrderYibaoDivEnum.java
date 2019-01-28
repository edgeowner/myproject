package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PaymentOrderYibaoDivEnum implements BaseEnum {


    need("需要进行易宝分账"), //需要进行易宝分账
    not_need("不需要进行易宝分账"), //不需要进行易宝分账
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
