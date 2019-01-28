package com.huboot.share.account_service.api.dto;

import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RefundReqDTO implements Serializable {

    @ApiModelProperty("支付单sn")
    @NotNull(message = "支付单sn不能为空")
    private String paymentSn;

    @ApiModelProperty("退款金额")
    @NotNull(message = "退款金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty("退款类型")
    @NotNull(message = "退款类型不能为空")
    private PaymentOrderSourceEnum source;
}
