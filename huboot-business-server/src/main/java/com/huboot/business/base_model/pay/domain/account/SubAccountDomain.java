package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*账户中心-子账户
*/
public class SubAccountDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //账户id
    private Long accountId ;
                
    public Long getAccountId(){
        return this.accountId;
    }

    public void setAccountId(Long  accountId){
        this.accountId = accountId;
    }
                
}

