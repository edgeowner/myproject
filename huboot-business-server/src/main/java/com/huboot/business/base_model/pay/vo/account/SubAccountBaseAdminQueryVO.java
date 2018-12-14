package com.huboot.business.base_model.pay.vo.account;

import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户基础信息内管查询类", description = "账户中心-子账户基础信息内管查询类")
public class SubAccountBaseAdminQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("买家名称,操作符'contains'")
    @QueryConfig(colname = "accountBase.shop_name", supportOps = {QueryOperatorEnum.contains})
    private String buyerShopName;
    @ApiModelProperty("卖家名称,操作符'contains'")
    @QueryConfig(colname = "subAccountDeposit.seller_shop_name", supportOps = {QueryOperatorEnum.contains})
    private String sellerShopName;
    @ApiModelProperty("押金账户状态,操作符'eq'")
    @QueryConfig(colname = "subAccountBase.status", supportOps = {QueryOperatorEnum.eq})
    private Integer status;
    @ApiModelProperty("押金账户状态,操作符'eq'")
    @QueryConfig(colname = "totalAmountType", supportOps = {QueryOperatorEnum.eq})
    private Integer totalAmountType;
    @ApiModelProperty("修改开始时间,操作符'gte'")
    @QueryConfig(colname = "subAccountBase.modify_time", supportOps = {QueryOperatorEnum.gte})
    private Date startTime;
    @ApiModelProperty("修改开始时间,操作符'lte'")
    @QueryConfig(colname = "subAccountBase.modify_time", supportOps = {QueryOperatorEnum.lte})
    private Date endTime;

    public String getBuyerShopName() {
        return buyerShopName;
    }

    public void setBuyerShopName(String buyerShopName) {
        this.buyerShopName = buyerShopName;
    }

    public String getSellerShopName() {
        return sellerShopName;
    }

    public void setSellerShopName(String sellerShopName) {
        this.sellerShopName = sellerShopName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalAmountType() {
        return totalAmountType;
    }

    public void setTotalAmountType(Integer totalAmountType) {
        this.totalAmountType = totalAmountType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

