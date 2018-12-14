package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户基础信息内管查询类", description = "账户中心-子账户基础信息内管查询类")
public class SubRedPacketAccountAdminQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("账户ID,操作符'eq'")
    @QueryConfig(colname = "subAccountBase.sub_account_id", supportOps = {QueryOperatorEnum.eq})
    private Long subAccountId;
    @ApiModelProperty("公司名称,操作符'contains'")
    @QueryConfig(colname = "accountBase.shop_name", supportOps = {QueryOperatorEnum.contains})
    private String companyName;
    @ApiModelProperty("所属省份,操作符'contains'")
    @QueryConfig(colname = "a.path", supportOps = {QueryOperatorEnum.contains})
    private Long provinceId;
    @ApiModelProperty("所属市,操作符'contains'")
    @QueryConfig(colname = "a.path", supportOps = {QueryOperatorEnum.contains})
    private Long cityId;
    @ApiModelProperty("余额账户状态,操作符'eq'")
    @QueryConfig(colname = "subAccountBase.status", supportOps = {QueryOperatorEnum.eq})
    private Integer status;
    @ApiModelProperty("修改开始时间,操作符'gte'")
    @QueryConfig(colname = "subAccountBase.modify_time", supportOps = {QueryOperatorEnum.gte})
    private Date startTime;
    @ApiModelProperty("修改开始时间,操作符'lte'")
    @QueryConfig(colname = "subAccountBase.modify_time", supportOps = {QueryOperatorEnum.lte})
    private Date endTime;

    public Long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

