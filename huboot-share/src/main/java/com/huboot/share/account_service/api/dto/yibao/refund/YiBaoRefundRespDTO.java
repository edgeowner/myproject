package com.huboot.share.account_service.api.dto.yibao.refund;

import lombok.Data;

import java.io.Serializable;

@Data
public class YiBaoRefundRespDTO implements Serializable {

    private String code;//	返回码	STRING

    private String message;//	返回信息描述	STRING

    private String bizSystemNo;//	业务方标识	STRING

    private String parentMerchantNo;//	平台商商户号	STRING

    private String merchantNo;//	子商户商户号	STRING

    private String orderId;//	商户订单号	STRING

    private String refundRequestId;//	商户退款请求号	STRING

    private String uniqueRefundNo;//	易宝统一订单号	STRINuniqueOrderNoG

    private String status;//	退款状态	STRING

    private String refundAmount;//	退款金额	STRING

    private String residualAmount;//	剩余金额	STRING

    private String description;//	退款订单描述	STRING

    private String refundRequestDate;//	退款请求日期	STRING

    private String accountDivided;//


}
