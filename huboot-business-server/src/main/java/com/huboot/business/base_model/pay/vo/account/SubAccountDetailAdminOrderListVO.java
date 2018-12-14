package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细内管订单列表", description = "账户中心-子账户明细内管订单列表")
public class SubAccountDetailAdminOrderListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("支付单号")
    private String sn;
    @ApiModelProperty("卖家名称")
    private String sellerShopName;
    @ApiModelProperty("买家名称")
    private String buyerShopName;
    @ApiModelProperty("交易金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amountPaid;
    @ApiModelProperty("订单状态")
    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSellerShopName() {
        return sellerShopName;
    }

    public void setSellerShopName(String sellerShopName) {
        this.sellerShopName = sellerShopName;
    }

    public String getBuyerShopName() {
        return buyerShopName;
    }

    public void setBuyerShopName(String buyerShopName) {
        this.buyerShopName = buyerShopName;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}

