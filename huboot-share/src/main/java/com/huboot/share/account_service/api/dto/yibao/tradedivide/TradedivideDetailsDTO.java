package com.huboot.share.account_service.api.dto.yibao.tradedivide;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradedivideDetailsDTO implements Serializable {

    private String ledgerNo;//分账方编号

    private String ledgerName;//分账方名称

    private String amount;//分账金额
}
