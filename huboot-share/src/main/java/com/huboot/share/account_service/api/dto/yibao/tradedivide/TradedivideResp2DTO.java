package com.huboot.share.account_service.api.dto.yibao.tradedivide;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradedivideResp2DTO implements Serializable {

    private String parentMerchantNo;//	主商户编号

    private String merchantNo;//	子商户编号

    private String orderId;//	商户订单号

    private String uniqueOrderNo;//易宝统一订单号

    private String divideRequestId;//商户分账请求号

    private String status;//	status	STRING

    private String divideDetail;//	分账详情

}
