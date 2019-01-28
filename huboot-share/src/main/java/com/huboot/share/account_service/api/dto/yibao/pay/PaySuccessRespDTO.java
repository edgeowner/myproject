package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaySuccessRespDTO implements Serializable {

    private String code;//	返回码	STRING

    private String message;//		返回信息描述

    private String parentMerchantNo;//		平台商商户号	STRING

    private String merchantName;//商户名字

    private String merchantNo;//		子商户商户号	STRING

    private String orderId;//		商户订单号	STRING

    private String uniqueOrderNo;//		易宝统一订单号	易宝内部生成唯一订单流水号， 请妥善保存， 后续查询订单及订单退款都会用到

    private String goodsParamExt;//		商品拓展信息	STRING

    private String memo;//		自定义对账备注	STRING

    private String token;//		STRING订单 token， 用来以参数方式传给收银台来完成支付，具体用法请参考标准收银台或API收银台

    private String  fundProcessTyp;//		资金处理类型	STRING
}
