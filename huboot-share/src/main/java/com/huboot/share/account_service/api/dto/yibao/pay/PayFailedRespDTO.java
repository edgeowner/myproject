package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayFailedRespDTO implements Serializable {

    private String code;

    private String message;

}
