package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*账户中心-子账户明细支付基础信息
*/
public class SubAccountDetailPaymentBaseDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //子账户明细支付类型,code=SUB_ACCOUNT_DETAIL_PAYMENT_TYPE
    private Integer type ;
    //子账户明细支付平台,code=SUB_ACCOUNT_DETAIL_PAYMENT_PLATFORM
    private Integer platform ;
    //备注
    private String remark ;
    //子账户明细支付方式,code=SUB_ACCOUNT_DETAIL_PAYMENT_METHOD
    private Integer method ;
                
    public Integer getType(){
        return this.type;
    }

    public void setType(Integer  type){
        this.type = type;
    }
        
    public Integer getPlatform(){
        return this.platform;
    }

    public void setPlatform(Integer  platform){
        this.platform = platform;
    }
        
    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String  remark){
        this.remark = remark;
    }
        
    public Integer getMethod(){
        return this.method;
    }

    public void setMethod(Integer  method){
        this.method = method;
    }
                
}

