package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SubAccountDetailAddForZKRefundVO implements Serializable {

    @ApiModelProperty("卖家账户id")
    private Long sellerAccountId;
    @ApiModelProperty("买家账户id")
    private Long buyerAccountId;
    @ApiModelProperty("买家账户明细sn")
    private String buyerSubAccountDetailSn;
    @ApiModelProperty("卖家账户明细sn")
    private String sellerSubAccountDetailSn;
    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;
    @ApiModelProperty("退款操作人名称")
    private String refunder;
    @ApiModelProperty("直客订单类型:")
    private Integer zKOrderType;
    //
    private List<SubAccountDetailAddForZKRefundItemVO> itemVOList;
}

