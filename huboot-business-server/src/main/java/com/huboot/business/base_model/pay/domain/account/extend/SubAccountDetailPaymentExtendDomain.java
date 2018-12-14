package com.huboot.business.base_model.pay.domain.account.extend;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*账户中心-子账户明细扩展
*/
public class SubAccountDetailPaymentExtendDomain extends AbstractDomain<Long> implements Serializable {
    //使用红包的最大值
    private BigDecimal maxRedPacketAmount;
    //创建订单支付时，传入买家的accountid，就是sellerAccountId
    private Long sellerAccountId;
    //支付成功后是否需要冻结资金，1-是,2-否
    private Integer needFreezeAfterSuccess = 2;

    //退款时候用
    private Long buyerAccountId;
    //退款时候用，关联卖家收入明细sn
    private String relaSellerIncomeDetailSn;

    public String getRelaSellerIncomeDetailSn() {
        return relaSellerIncomeDetailSn;
    }

    public void setRelaSellerIncomeDetailSn(String relaSellerIncomeDetailSn) {
        this.relaSellerIncomeDetailSn = relaSellerIncomeDetailSn;
    }

    public Long getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(Long buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
    }

    public Integer getNeedFreezeAfterSuccess() {
        return needFreezeAfterSuccess;
    }

    public void setNeedFreezeAfterSuccess(Integer needFreezeAfterSuccess) {
        this.needFreezeAfterSuccess = needFreezeAfterSuccess;
    }

    public Long getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(Long sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public BigDecimal getMaxRedPacketAmount() {
        return maxRedPacketAmount;
    }

    public void setMaxRedPacketAmount(BigDecimal maxRedPacketAmount) {
        this.maxRedPacketAmount = maxRedPacketAmount;
    }
}

