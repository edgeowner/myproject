package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.AccountDomain;
import com.huboot.business.base_model.pay.dto.account.AccountAddDTO;
import com.huboot.business.base_model.pay.dto.account.AccountDTO;
import com.huboot.business.mybatis.IBaseService;

/**
 * 账户中心-账户Service
 */
public interface IAccountService extends IBaseService<AccountDomain, Long> {

    AccountDTO createAccount(AccountAddDTO accountAddDTO);

    AccountDTO findByShopId(Long shopId);
}
