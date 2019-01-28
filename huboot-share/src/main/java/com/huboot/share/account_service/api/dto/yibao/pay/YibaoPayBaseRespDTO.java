package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class YibaoPayBaseRespDTO implements Serializable {
    private String state;//	处理状态	String	api调用处理状态	SUCCESS/FAILURE
    private String requestId;
    private String result;//	响应内容	String	业务方的响应结果（可能是密文）	-
    private String sign;//	fasdfasf	String	服务器端对响应结果做的签名	1aa16f98189af52d55afb516b5d1e4eef3e18448
    private String ts;//	时间戳	Long	服务器端响应的时间戳	1497944313850
    private String error;//	错误码	String	错误码	99001008
    private Boolean validSign;
    private String code;
    private String message;
    private String parentMerchantNo;
    private String parentMerchantName;
    private String merchantNo;
    private String orderId;
    private String goodsParamExt;
    private String fundProcessType;
    private String uniqueOrderNo;
    private String token;
    private String merchantName;

}
