package com.huboot.share.account_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/9/11 0011.
 */
@Data
public class BrokerageDetail implements Serializable {

    @ApiModelProperty("累计佣金，单位精确到分")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ApiModelProperty("可提佣金，单位精确到分")
    private BigDecimal useableAmount = BigDecimal.ZERO ;

    @ApiModelProperty("已提佣金，单位精确到分")
    private BigDecimal usedAmount = BigDecimal.ZERO ;

}
