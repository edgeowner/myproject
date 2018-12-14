package com.huboot.business.base_model.pay.vo.account;

import com.huboot.business.base_model.pay.dto.account.PaymentItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
@Data
public class SubAccountDetailAddForZKOrderVO implements Serializable {

    @ApiModelProperty("卖家公司uid")
    private Long sellerAccountId;
    @ApiModelProperty("买家账户id")
    private Long buyerAccountId;
    @ApiModelProperty("交易金额")
    private BigDecimal amountPaid;
    @ApiModelProperty("交易主单号")
    private String tradeSn;
    @ApiModelProperty("操作人")
    private String operator;
    @ApiModelProperty("支付成功后是否需要冻结资金，1-是,2-否")
    private Integer needFreezeAfterSuccess;
    @ApiModelProperty("支付详情")
    private List<PaymentItemDTO> itemList;
    @ApiModelProperty("直客订单类型")
    private Integer zKOrderType;

}

