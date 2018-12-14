package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class RedPacketAccountDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("明细编号")
    private String sn;
    @ApiModelProperty("交易系统-订单编号")
    private String tradeSn ;
    @ApiModelProperty("运算符号:1+,2-")
    private Integer sign ;
    @ApiModelProperty("符号（+，-）")
    private String signName;
    @ApiModelProperty("交易金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amountPaid;
    @ApiModelProperty("变更后金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal changedAmount ;
    @ApiModelProperty("支付成功日期")
    private Date paymentDate;
    @ApiModelProperty("子账户明细类型,9-订单奖励，10-订单收入，11-到期失效，12-系统发放")
    private Integer type;
    @ApiModelProperty("子账户明细类型名称")
    private String typeName;
    @ApiModelProperty("子账户明细类型标题：使用红包，获取红包，失效红包")
    private String typeTitle;
    @ApiModelProperty("子账户明细状态:8-已到账")
    private Integer status;
    @ApiModelProperty("子账户明细状态名称")
    private String statusName;
    @ApiModelProperty("备注信息")
    private String remark;

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

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

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

