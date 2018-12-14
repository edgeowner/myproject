package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细内管查询类", description = "账户中心-子账户明细内管查询类")
public class SubAccountDepositDetailAdminQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("所属省份,操作符'contains'")
    @QueryConfig(colname = "a.path", supportOps = {QueryOperatorEnum.contains})
    private Long provinceAreaId;

    @ApiModelProperty("所属市,操作符'contains'")
    @QueryConfig(colname = "a.path", supportOps = {QueryOperatorEnum.contains})
    private Long provinceCityId;

    @ApiModelProperty("买家名称,操作符'contains'")
    @QueryConfig(colname = "accountBase.shop_name", supportOps = {QueryOperatorEnum.contains})
    private String buyerShopName;

    @ApiModelProperty("卖家名称,操作符'contains'")
    @QueryConfig(colname = "subAccountDeposit.seller_shop_name", supportOps = {QueryOperatorEnum.contains})
    private String sellerShopName;

    @ApiModelProperty("创建开始时间,操作符'gte'")
    @QueryConfig(colname = "zacSubAccountDetailPayment.create_time", supportOps = {QueryOperatorEnum.gte})
    private Date createStartTime;

    @ApiModelProperty("创建结束时间,操作符'lte'")
    @QueryConfig(colname = "zacSubAccountDetailPayment.create_time", supportOps = {QueryOperatorEnum.lte})
    private Date createEndTime;

    @ApiModelProperty("支付类型,操作符'eq'")
    @QueryConfig(colname = "zacSubAccountDetailPayment.platform", supportOps = {QueryOperatorEnum.eq})
    private Integer platform;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProvinceAreaId() {
        return provinceAreaId;
    }

    public void setProvinceAreaId(Long provinceAreaId) {
        this.provinceAreaId = provinceAreaId;
    }

    public Long getProvinceCityId() {
        return provinceCityId;
    }

    public void setProvinceCityId(Long provinceCityId) {
        this.provinceCityId = provinceCityId;
    }

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

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

}

