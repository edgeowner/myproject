package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细内管查询类", description = "账户中心-子账户明细内管查询类")
public class SubPayAnotherDetailAdminQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("公司名称,操作符'contains'")
    @QueryConfig(colname = "applyAccountBase.shop_name", supportOps = {QueryOperatorEnum.contains})
    private String companyName;
    @ApiModelProperty("申请流水号,操作符'eq'")
    @QueryConfig(colname = "applyAccountDetail.sn", supportOps = {QueryOperatorEnum.eq})
    private String applySn;
    @ApiModelProperty("代付流水号,操作符'eq'")
    @QueryConfig(colname = "subAccountDetail.sn", supportOps = {QueryOperatorEnum.eq})
    private String payAnotherSn;
    @ApiModelProperty("代付状态,操作符'eq'")
    @QueryConfig(colname = "subAccountDetail.status", supportOps = {QueryOperatorEnum.eq})
    private Integer payAnotherStatus;
    @ApiModelProperty("提现申请开始时间,操作符'gte'")
    @QueryConfig(colname = "applyAccountDetail.create_time", supportOps = {QueryOperatorEnum.gte})
    private Date applyStartTime;
    @ApiModelProperty("提现申请结束时间,操作符'lte'")
    @QueryConfig(colname = "applyAccountDetail.create_time", supportOps = {QueryOperatorEnum.lte})
    private Date applyEndTime;
    @ApiModelProperty("代付开始时间,操作符'gte'")
    @QueryConfig(colname = "subAccountDetail.payment_date", supportOps = {QueryOperatorEnum.gte})
    private Date payAnotherStartTime;
    @ApiModelProperty("代付结束时间,操作符'lte'")
    @QueryConfig(colname = "subAccountDetail.payment_date", supportOps = {QueryOperatorEnum.lte})
    private Date payAnotherEndTime;
    @ApiModelProperty("代付方式,操作符'eq'")
    @QueryConfig(colname = "subAccountDetailPayment.type", supportOps = {QueryOperatorEnum.eq})
    private Integer paymentType;

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getApplySn() {
        return applySn;
    }

    public void setApplySn(String applySn) {
        this.applySn = applySn;
    }

    public String getPayAnotherSn() {
        return payAnotherSn;
    }

    public void setPayAnotherSn(String payAnotherSn) {
        this.payAnotherSn = payAnotherSn;
    }

    public Integer getPayAnotherStatus() {
        return payAnotherStatus;
    }

    public void setPayAnotherStatus(Integer payAnotherStatus) {
        this.payAnotherStatus = payAnotherStatus;
    }

    public Date getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(Date applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Date getPayAnotherStartTime() {
        return payAnotherStartTime;
    }

    public void setPayAnotherStartTime(Date payAnotherStartTime) {
        this.payAnotherStartTime = payAnotherStartTime;
    }

    public Date getPayAnotherEndTime() {
        return payAnotherEndTime;
    }

    public void setPayAnotherEndTime(Date payAnotherEndTime) {
        this.payAnotherEndTime = payAnotherEndTime;
    }
}

