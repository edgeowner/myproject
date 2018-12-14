package com.huboot.business.base_model.pay.dto.account;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29 0029.
 */
public class TradePaySuccessDTO {

    private String subAccountDetailSn;

    private Integer paymentType;

    private String paymentTime;

    private BigDecimal paidAmount;

    private BigDecimal redPackageAmount;

    //卖家账单sn
    private String sellerSubAccountDetailSn;
    //支付流水号
    private String sequenceSn;
    //是否代付：1，是；2.否
    private Integer isSharePay ;
    //支付详情/
    private List<PaymentItemDTO> itemList;

    public List<PaymentItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<PaymentItemDTO> itemList) {
        this.itemList = itemList;
    }

    public String getSequenceSn() {
        return sequenceSn;
    }

    public void setSequenceSn(String sequenceSn) {
        this.sequenceSn = sequenceSn;
    }

    public String getSellerSubAccountDetailSn() {
        return sellerSubAccountDetailSn;
    }

    public void setSellerSubAccountDetailSn(String sellerSubAccountDetailSn) {
        this.sellerSubAccountDetailSn = sellerSubAccountDetailSn;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getRedPackageAmount() {
        return redPackageAmount;
    }

    public void setRedPackageAmount(BigDecimal redPackageAmount) {
        this.redPackageAmount = redPackageAmount;
    }

    public String getSubAccountDetailSn() {
        return subAccountDetailSn;
    }

    public void setSubAccountDetailSn(String subAccountDetailSn) {
        this.subAccountDetailSn = subAccountDetailSn;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getIsSharePay() {
        return isSharePay;
    }

    public void setIsSharePay(Integer isSharePay) {
        this.isSharePay = isSharePay;
    }
}
