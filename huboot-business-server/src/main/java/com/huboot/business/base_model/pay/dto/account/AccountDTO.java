package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;

import java.io.Serializable;

//账户中心-账户
public class AccountDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //唯一标识
    private AccountBaseDomain accountBaseDomain;

    public AccountBaseDomain getAccountBaseDomain() {
        return accountBaseDomain;
    }

    public void setAccountBaseDomain(AccountBaseDomain accountBaseDomain) {
        this.accountBaseDomain = accountBaseDomain;
    }
}

