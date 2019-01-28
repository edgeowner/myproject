package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum BillTypeEnum implements BaseEnum {

    none("暂无"),
    order_income("订单收入"),
    order_expenditure("订单支出"),
    rent_refund("退租金"),
    car_rental_deposit("租车押金收取"),
    car_rental_deposit_expenditure("租车押金支出"),
    car_rental_refund("租车押金退还"),
    peccancy_deposit("违章押金收取"),
    peccancy_deposit_expenditure("违章押金支出"),
    peccancy_refund("违章押金退还"),
    cash_withdrawal("提现"),
    violation_of_commission("违章代扣"),
    income_refund("商家退款"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
