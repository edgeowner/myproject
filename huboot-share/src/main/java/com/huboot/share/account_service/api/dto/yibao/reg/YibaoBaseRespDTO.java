package com.huboot.share.account_service.api.dto.yibao.reg;

import lombok.Data;

import java.io.Serializable;

@Data
public class YibaoBaseRespDTO implements Serializable {

    private String parentMerchantNo;//	代理商编号	STRING	返回接口调用方所在易宝的商户编号
    private String externalId;//	入网内部流水号	STRING	返回该条请求易宝的流水号
    private String requestNo;//	入网请求号	STRING	返回调用方所生成的流水号
    private String merchantNo;//	商户编号	STRING	该入网商户所在易宝的商户商编
    private String returnMsg;//	返回描述信息	STRING

    private String returnCode;//	返回码	STRING

}
