package com.huboot.business.base_model.pay.vo.account;

import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细内管查询类", description = "账户中心-子账户明细内管查询类")
public class SubBalanceAccountDetailAdminQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("公司名称,操作符'contains'")
    @QueryConfig(colname = "accountBase.shop_name", supportOps = {QueryOperatorEnum.contains})
    private String companyName;
    @ApiModelProperty("流水号,操作符'eq'")
    @QueryConfig(colname = "subAccountDetail.sn", supportOps = {QueryOperatorEnum.eq})
    private String sn;
    @ApiModelProperty("操作类型,操作符'eq'")
    @QueryConfig(colname = "subAccountDetail.type", supportOps = {QueryOperatorEnum.eq})
    private Integer type;
    @ApiModelProperty("收支情况,操作符'eq'")
    @QueryConfig(colname = "subAccountDetail.sign", supportOps = {QueryOperatorEnum.eq})
    private Integer inOut;
    @ApiModelProperty("明细状态,操作符'eq'")
    @QueryConfig(colname = "subAccountDetail.status", supportOps = {QueryOperatorEnum.eq})
    private Integer status ;
    @ApiModelProperty("创建开始时间,操作符'gte'")
    @QueryConfig(colname = "subAccountDetail.create_time", supportOps = {QueryOperatorEnum.gte})
    private Date createStartTime;
    @ApiModelProperty("创建结束时间,操作符'lte'")
    @QueryConfig(colname = "subAccountDetail.create_time", supportOps = {QueryOperatorEnum.lte})
    private Date createEndTime;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInOut() {
        return inOut;
    }

    public void setInOut(Integer inOut) {
        this.inOut = inOut;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

