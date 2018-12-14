package com.huboot.business.base_model.pay.dto.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
@Data
public class PaymentItemDTO implements Serializable {

    @ApiModelProperty("交易金额")
    private BigDecimal amount;
    @ApiModelProperty("交易金额")
    private String tradeSn;
    @ApiModelProperty("订单类型")
    private Integer orderType;
    @ApiModelProperty("说明")
    private String remark;
}
