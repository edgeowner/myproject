package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "押金账户查询类", description = "押金账户查询类")
public class SubAccountDepositQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("卖家店铺ID,操作符'eq'")
    @QueryConfig(colname = "buyer_shop_id", supportOps = {QueryOperatorEnum.eq})
    private Long buyerShopId;

    @ApiModelProperty("账单状态,操作符'eq'")
    @QueryConfig(colname = "status", supportOps = {QueryOperatorEnum.eq})
    private Integer status;

    @ApiModelProperty("收款明细账单状态,操作符'eq'")
    @QueryConfig(colname = "status", supportOps = {QueryOperatorEnum.ne})
    private Integer statusForRecharge;

    @ApiModelProperty("账单类型,操作符'eq'")
    @QueryConfig(colname = "type", supportOps = {QueryOperatorEnum.eq})
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getBuyerShopId() {
        return buyerShopId;
    }

    public void setBuyerShopId(Long buyerShopId) {
        this.buyerShopId = buyerShopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusForRecharge() {
        return statusForRecharge;
    }

    public void setStatusForRecharge(Integer statusForRecharge) {
        this.statusForRecharge = statusForRecharge;
    }
}

