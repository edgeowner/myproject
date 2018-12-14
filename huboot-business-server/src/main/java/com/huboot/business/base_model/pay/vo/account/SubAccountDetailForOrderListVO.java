package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@ApiModel(value = "营业收入明细列表", description = "营业收入明细列表")
public class SubAccountDetailForOrderListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("收入时间")
    private Date paymentDate;
    @ApiModelProperty("订单ID")
    private Long orderId;
    @ApiModelProperty("订单编号")
    private String orderSn;
    @ApiModelProperty("买家名称")
    private String buyerShopName;
    @ApiModelProperty("卖家名称")
    private String sellerShopName;
    @ApiModelProperty("营业总额")
    private BigDecimal amountPaid;
    @ApiModelProperty("押金金额")
    private BigDecimal depositAmount;
    @ApiModelProperty("租车金额")
    private BigDecimal amount;
    @ApiModelProperty("订单结算,1-已结算，2-未结算")
    private Integer orderSettledStatus;
    @ApiModelProperty("订单结算,1-已结算，2-未结算")
    private String orderSettledStatusValue;
    @ApiModelProperty("订单结算时间")
    private Date settleTime;
    @ApiModelProperty("订单还车时间")
    private Date returnCarTime;
/*    @ApiModelProperty("明细列表")
    //private List<OrderAdminDetailPayInfoVO> orderAdminDetailPayInfoVOList = new ArrayList<>();*/
    @ApiModelProperty("付款列表")
    private List<PaymentPlatformVO> paymentPlatformVOList = new ArrayList<>();

    public Date getReturnCarTime() {
        return returnCarTime;
    }

    public void setReturnCarTime(Date returnCarTime) {
        this.returnCarTime = returnCarTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public static class PaymentPlatformVO implements Serializable {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty("付款方式")
        private String platformName;
        @ApiModelProperty("支付流水号")
        private String transactionNo;
        @ApiModelProperty("营业总额")
        private BigDecimal amount;

        public String getPlatformName() {
            return platformName;
        }

        public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }

        public String getTransactionNo() {
            return transactionNo;
        }

        public void setTransactionNo(String transactionNo) {
            this.transactionNo = transactionNo;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

    public List<PaymentPlatformVO> getPaymentPlatformVOList() {
        return paymentPlatformVOList;
    }

    public void setPaymentPlatformVOList(List<PaymentPlatformVO> paymentPlatformVOList) {
        this.paymentPlatformVOList = paymentPlatformVOList;
    }

    public String getOrderSettledStatusValue() {
        return orderSettledStatusValue;
    }

    public void setOrderSettledStatusValue(String orderSettledStatusValue) {
        this.orderSettledStatusValue = orderSettledStatusValue;
    }

    /*public List<OrderAdminDetailPayInfoVO> getOrderAdminDetailPayInfoVOList() {
        return orderAdminDetailPayInfoVOList;
    }

    public void setOrderAdminDetailPayInfoVOList(List<OrderAdminDetailPayInfoVO> orderAdminDetailPayInfoVOList) {
        this.orderAdminDetailPayInfoVOList = orderAdminDetailPayInfoVOList;
    }*/

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getOrderSettledStatus() {
        return orderSettledStatus;
    }

    public void setOrderSettledStatus(Integer orderSettledStatus) {
        this.orderSettledStatus = orderSettledStatus;
    }

    public String getSellerShopName() {
        return sellerShopName;
    }

    public void setSellerShopName(String sellerShopName) {
        this.sellerShopName = sellerShopName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getBuyerShopName() {
        return buyerShopName;
    }

    public void setBuyerShopName(String buyerShopName) {
        this.buyerShopName = buyerShopName;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}

