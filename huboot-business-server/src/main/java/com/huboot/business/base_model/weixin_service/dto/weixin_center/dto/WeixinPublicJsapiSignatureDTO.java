package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * jspai signature
 */
@Data
public class WeixinPublicJsapiSignatureDTO implements Serializable {

    //公众号appid
    private String appId;
    //生成签名的随机串
    private String nonceStr;
    //生成签名的时间戳
    private long timestamp;
    //签名
    private String signature;
}
