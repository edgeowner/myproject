package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细内管查询类", description = "账户中心-子账户明细内管查询类")
public class SubRedPacketAccountDetailAdminQueryVO implements Serializable {
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
    @ApiModelProperty("操作开始时间,操作符'gte'")
    @QueryConfig(colname = "subAccountDetail.create_time", supportOps = {QueryOperatorEnum.gte})
    private Date startTime;
    @ApiModelProperty("操作结束时间,操作符'lte'")
    @QueryConfig(colname = "subAccountDetail.create_time", supportOps = {QueryOperatorEnum.lte})
    private Date endTime;

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

