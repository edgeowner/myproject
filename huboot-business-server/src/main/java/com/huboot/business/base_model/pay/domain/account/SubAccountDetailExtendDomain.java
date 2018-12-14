package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*账户中心-子账户明细扩展
*/
public class SubAccountDetailExtendDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //主账户id
    private Long accountId ;
    //子账户id
    private Long subAccountId ;
    //子账户id
    private Long subAccountDetailId ;
    //提现类型的扩展
    private String cashExtend ;
    //支付控制的扩展
    private String paymentExtend ;
    //支付商品说明（[{"item":"xxx","amount":"xxx"}]）
    private String payItemsExtend ;
    //更新版本号
    private Integer version ;
                
    public Long getAccountId(){
        return this.accountId;
    }

    public void setAccountId(Long  accountId){
        this.accountId = accountId;
    }
        
    public Long getSubAccountId(){
        return this.subAccountId;
    }

    public void setSubAccountId(Long  subAccountId){
        this.subAccountId = subAccountId;
    }
        
    public Long getSubAccountDetailId(){
        return this.subAccountDetailId;
    }

    public void setSubAccountDetailId(Long  subAccountDetailId){
        this.subAccountDetailId = subAccountDetailId;
    }
        
    public String getCashExtend(){
        return this.cashExtend;
    }

    public void setCashExtend(String  cashExtend){
        this.cashExtend = cashExtend;
    }
        
    public String getPaymentExtend(){
        return this.paymentExtend;
    }

    public void setPaymentExtend(String  paymentExtend){
        this.paymentExtend = paymentExtend;
    }
        
    public String getPayItemsExtend(){
        return this.payItemsExtend;
    }

    public void setPayItemsExtend(String  payItemsExtend){
        this.payItemsExtend = payItemsExtend;
    }
        
    public Integer getVersion(){
        return this.version;
    }

    public void setVersion(Integer  version){
        this.version = version;
    }
                
}

