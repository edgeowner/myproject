package com.huboot.share.account_service.api.dto.order_payment;


import com.huboot.share.account_service.enums.PayPlatformEnum;
import com.huboot.share.account_service.enums.PayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PayReqDTO implements Serializable {

    @ApiModelProperty("支付单号")
    @NotNull(message = "支付单号不能为空")
    private String pamentSn;

    //支付平台
    @NotNull(message = "支付平台不能为空")
    private PayPlatformEnum payPlatform ;

//    @ApiModelProperty("优惠金额")
//    private BigDecimal discountAmount;

    @ApiModelProperty("支付方式")
    @NotNull(message = "支付方式不能为空")
    private PayTypeEnum payType ;

//    @ApiModelProperty("appId")
//    @NotBlank(message = "appId不能为空")
//    private String appId;
//
//    @ApiModelProperty("用户openId")
//    @NotBlank(message = "用户openId不能为空")
//    private String openId;


}
