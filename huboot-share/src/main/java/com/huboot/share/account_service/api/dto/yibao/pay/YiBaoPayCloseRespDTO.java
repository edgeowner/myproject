package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class YiBaoPayCloseRespDTO implements Serializable {

    private String code;//	返回码	STRING结果码，OPR00000表示成功

    private String message;//	返回信息	STRING 信息描述，中文，对应code的中文信息

    private String bizSystemNo;//	业务方标识	STRING

    private String parentMerchantNo;//	平台商商户号	STRING

    private String merchantNo;//	子商户商户号	STRING

    private String orderId;//	商户订单号	STRING

    private String orderCloseRequestId;//	请求订单关闭号	STRING

}
