package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细内管财务列表", description = "账户中心-子账户明细内管财务列表")
public class SubAccountDetailAdminFinanceListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("编号")
    private String sn;
    @ApiModelProperty("买家名称")
    private String buyerShopName;
    @ApiModelProperty("操作类型")
    private String typeName;
    @ApiModelProperty("原金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal originalAmount;
    @ApiModelProperty("交易金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amountPaid;
    @ApiModelProperty("变更后金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal changedAmount;
    @ApiModelProperty("卖家名称")
    private String sellerShopName;
    @ApiModelProperty("操作备注")
    private String remark;
    @ApiModelProperty("操作时间")
    private String paymentDate;
    @ApiModelProperty("操作人")
    private String modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBuyerShopName() {
        return buyerShopName;
    }

    public void setBuyerShopName(String buyerShopName) {
        this.buyerShopName = buyerShopName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getChangedAmount() {
        return changedAmount;
    }

    public void setChangedAmount(BigDecimal changedAmount) {
        this.changedAmount = changedAmount;
    }

    public String getSellerShopName() {
        return sellerShopName;
    }

    public void setSellerShopName(String sellerShopName) {
        this.sellerShopName = sellerShopName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}

