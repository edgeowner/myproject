package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "营业收入明细查询类", description = "营业收入明细查询类")
public class SubAccountDetailForOrderQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("卖家名称,操作符'eq'")
    @QueryConfig(colname = "o.seller_shop_name", supportOps = {QueryOperatorEnum.contains})
    private Long sellerShopName;
    @ApiModelProperty("买家名称,操作符'eq'")
    @QueryConfig(colname = "o.buyer_shop_name", supportOps = {QueryOperatorEnum.contains})
    private Long buyerShopName;
    @ApiModelProperty("公司类型,操作符'eq'")
    @QueryConfig(colname = "adp.platform", supportOps = {QueryOperatorEnum.eq})
    private Integer platform;
    @ApiModelProperty("收入开始时间,操作符'gte'")
    @QueryConfig(colname = "ad.payment_date", supportOps = {QueryOperatorEnum.gte})
    private Date startTime;
    @ApiModelProperty("收入结束时间,操作符'lte'")
    @QueryConfig(colname = "ad.payment_date", supportOps = {QueryOperatorEnum.lte})
    private Date endTime;
    @ApiModelProperty("收入开始时间,操作符'gte'")
    @QueryConfig(colname = "o.return_car_time", supportOps = {QueryOperatorEnum.gte})
    private Date returnStartTime;
    @ApiModelProperty("收入结束时间,操作符'lte'")
    @QueryConfig(colname = "o.return_car_time", supportOps = {QueryOperatorEnum.lte})
    private Date returnEndTime;
    @ApiModelProperty("所属省份,操作符'contains'")
    @QueryConfig(colname = "a.path", supportOps = {QueryOperatorEnum.contains})
    private Long provinceId;
    @ApiModelProperty("所属市,操作符'contains'")
    @QueryConfig(colname = "a.path", supportOps = {QueryOperatorEnum.contains})
    private Long cityId;
    @ApiModelProperty("订单结算:1-已结算，2-未结算,操作符'eq'")
    @QueryConfig(colname = "o.status", supportOps = {QueryOperatorEnum.eq})
    private Integer orderSettledStatus;

    public Date getReturnStartTime() {
        return returnStartTime;
    }

    public void setReturnStartTime(Date returnStartTime) {
        this.returnStartTime = returnStartTime;
    }

    public Date getReturnEndTime() {
        return returnEndTime;
    }

    public void setReturnEndTime(Date returnEndTime) {
        this.returnEndTime = returnEndTime;
    }

    public Integer getOrderSettledStatus() {
        return orderSettledStatus;
    }

    public void setOrderSettledStatus(Integer orderSettledStatus) {
        this.orderSettledStatus = orderSettledStatus;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getBuyerShopName() {
        return buyerShopName;
    }

    public void setBuyerShopName(Long buyerShopName) {
        this.buyerShopName = buyerShopName;
    }

    public Long getSellerShopName() {
        return sellerShopName;
    }

    public void setSellerShopName(Long sellerShopName) {
        this.sellerShopName = sellerShopName;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
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

