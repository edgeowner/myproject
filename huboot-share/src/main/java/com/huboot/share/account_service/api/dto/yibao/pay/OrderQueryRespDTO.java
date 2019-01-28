package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderQueryRespDTO extends PaySuccessRespDTO implements Serializable {

    private String haveAccounted;
    private String orderAmount;
    private String requestDate;
    private String residualDivideAmount;
    private String status;
    private String token;
    private String uniqueOrderNo;
}
