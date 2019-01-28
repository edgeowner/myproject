package com.huboot.share.account_service.api.dto.yibao.refund;

import lombok.Data;

import java.io.Serializable;

@Data
public class YiBaoRefundResp2DTO extends YiBaoRefundRespDTO implements Serializable {


    private String uniqueOrderNo;// 原支付订单流水号

    private String returnMerchantFee;//返还商户手续费

    private String returnCustomerFee;//返回用户手续费

    private String refundSuccessDate;//退款成功时间

    private String realDeductAmount;//实扣金额

    private String realRefundAmount;//实退金额


}
