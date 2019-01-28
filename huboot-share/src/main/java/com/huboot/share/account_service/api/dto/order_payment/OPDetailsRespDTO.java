package com.huboot.share.account_service.api.dto.order_payment;

import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.PaymentOrderStatusEnum;
import com.huboot.share.account_service.enums.PaymentOrderTypeEnum;
import com.huboot.share.common.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OPDetailsRespDTO implements Serializable {

    @ApiModelProperty("支付单类型")
    private PaymentOrderTypeEnum type ;

    @ApiModelProperty("编号")
    private String sn ;

    @ApiModelProperty("付钱方子账户")
    private Long fromSubAccount ;

    @ApiModelProperty("收钱方子账户")
    private Long toSubAccount ;

    @ApiModelProperty("来源")
    private PaymentOrderSourceEnum source ;

    @ApiModelProperty("交易金额")
    private BigDecimal amount ;

    @ApiModelProperty("状态")
    private PaymentOrderStatusEnum status ;

    @ApiModelProperty("过期时间")
    private LocalDateTime expireTime ;

    @ApiModelProperty("成功付款后是否需要冻结资金 ")
    private YesOrNoEnum freezeAfterPaid ;

    @ApiModelProperty("退款关联支付单号 ")
    private String refundRelaPaySn ;

    @ApiModelProperty("成功支付流水id")
    private Long successPayseqId ;

    @ApiModelProperty("订单列表")
    private List<OPItemDetailsRespDTO> items;


}
