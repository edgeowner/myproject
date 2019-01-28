package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PaymentOrderStatusEnum implements BaseEnum {

    submit("已提交"),
  //  paid("已支付"), //资金冻结的时候或有其他操作的时候
    finish("已完成"),
    colse("已关闭"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
