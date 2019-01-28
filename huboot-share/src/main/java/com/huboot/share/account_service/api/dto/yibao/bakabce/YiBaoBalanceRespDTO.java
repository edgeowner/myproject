package com.huboot.share.account_service.api.dto.yibao.bakabce;

import lombok.Data;

import java.io.Serializable;

@Data
public class YiBaoBalanceRespDTO implements Serializable {

    private String groupNumber;//	系统商商编	STRING
    private String customerNumber;//	商户编号	STRING
    private String errorCode;//	错误码	STRING
    private String errorMsg;//	错误信息	STRING
    private String d1ValidAmount;//	d1可用余额	NUMBER
    private String d0ValidAmount;//
}
