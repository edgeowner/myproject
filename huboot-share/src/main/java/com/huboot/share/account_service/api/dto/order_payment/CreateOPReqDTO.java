package com.huboot.share.account_service.api.dto.order_payment;


import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.PaymentOrderTypeEnum;
import com.huboot.share.common.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateOPReqDTO implements Serializable {

    @ApiModelProperty("支付单类型,默认为: 三方账户 -> 余额账户")
    private PaymentOrderTypeEnum payType = PaymentOrderTypeEnum.trade_pay;

    @ApiModelProperty("付钱方用户id")
    @NotBlank(message = "付钱方用户id不能为空")
    private String userId;

    @ApiModelProperty("收钱方子comapnayid")
    @NotBlank(message = "收钱方子comapnayid不能为空")
    private String companyId;

    @ApiModelProperty("支付单来源")
    @NotNull(message = "支付单来源不能为空")
    private PaymentOrderSourceEnum source = PaymentOrderSourceEnum.zkrent_rent;

    @ApiModelProperty("成功付款后是否需要冻结资金")
    @NotNull(message = "成功付款后是否需要冻结资金不能为空")
    private YesOrNoEnum freezeAfterPaid = YesOrNoEnum.no;

    @ApiModelProperty("支付订单条目")
    @NotNull(message = "支付订单条目")
    private List<CreateOPItemReqDTO> items = new ArrayList<>();

    public void addItem(CreateOPItemReqDTO item) {
        items.add(item);
    }
}
