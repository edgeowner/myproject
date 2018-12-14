package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class BalanceAccountDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("明细编号")
    private String sn;
    @ApiModelProperty("交易系统-订单编号")
    private String tradeSn ;
    @ApiModelProperty("交易系统-订单类型：1-自营；2-商家")
    private Integer tradeOrderBusinessType ;
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
    @ApiModelProperty("子账户明细类型,5-提现，6-订单收入")
    private Integer type;
    @ApiModelProperty("子账户明细类型名称")
    private String typeName;
    @ApiModelProperty("子账户明细状态,7-处理中，8-已到账")
    private Integer status;
    @ApiModelProperty("子账户明细状态名称")
    private String statusName;
    @ApiModelProperty("备注信息")
    private String remark;
    @ApiModelProperty("创建时间")
    private Timestamp createTime;
    @ApiModelProperty("订单来源")
    private Integer tradeOrderSource;

    public Integer getTradeOrderSource() {
        return tradeOrderSource;
    }

    public void setTradeOrderSource(Integer tradeOrderSource) {
        this.tradeOrderSource = tradeOrderSource;
    }

    public Integer getTradeOrderBusinessType() {
        return tradeOrderBusinessType;
    }

    public void setTradeOrderBusinessType(Integer tradeOrderBusinessType) {
        this.tradeOrderBusinessType = tradeOrderBusinessType;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}

