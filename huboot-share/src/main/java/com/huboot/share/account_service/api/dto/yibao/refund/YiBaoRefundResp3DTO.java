package com.huboot.share.account_service.api.dto.yibao.refund;

import lombok.Data;

import java.io.Serializable;

@Data
public class YiBaoRefundResp3DTO extends YiBaoRefundResp2DTO implements Serializable {


    private String orderAmount;// 订单金额

    private String errorMessage;//退款失败原因


}
