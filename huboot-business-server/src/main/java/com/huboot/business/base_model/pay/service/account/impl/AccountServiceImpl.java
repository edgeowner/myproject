package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.dao.account.IAccountDao;
import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.base_model.pay.domain.account.AccountDomain;
import com.huboot.business.base_model.pay.dto.account.AccountAddDTO;
import com.huboot.business.base_model.pay.dto.account.AccountDTO;
import com.huboot.business.base_model.pay.enums.AccountStatusEnum;
import com.huboot.business.base_model.pay.service.account.IAccountBaseService;
import com.huboot.business.base_model.pay.service.account.IAccountService;
import com.huboot.business.mybatis.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 账户中心-账户ServiceImpl
 */
@Service("accountServiceImpl")
public class AccountServiceImpl extends AbstractBaseService<AccountDomain, Long> implements IAccountService {

    @Autowired
    public AccountServiceImpl(IAccountDao accountDao) {
        super(accountDao);
    }

    @Autowired
    private IAccountBaseService accountBaseService;

    @Override
    @Transactional
    public AccountDTO createAccount(AccountAddDTO accountAddDTO) {
        AccountDTO accountDTO = new AccountDTO();
        AccountBaseDomain accountBaseDomain = accountBaseService.findByShopId(accountAddDTO.getShopId());
        if (accountBaseDomain == null) {
            accountBaseDomain = new AccountBaseDomain();
            AccountDomain accountDomain = new AccountDomain();
            this.create(accountDomain);
            accountBaseDomain.setAccountId(accountDomain.getId());
            accountBaseDomain.setShopId(accountAddDTO.getShopId());
            accountBaseDomain.setShopName(accountAddDTO.getShopName());
            accountBaseDomain.setStatus(AccountStatusEnum.Valid.getValue());
            accountBaseDomain.setTotalAssets(BigDecimal.ZERO);
            accountBaseService.create(accountBaseDomain);
        }
        accountDTO.setAccountBaseDomain(accountBaseDomain);
        return accountDTO;
    }

    @Override
    public AccountDTO findByShopId(Long shopId) {
        AccountDTO accountDTO = new AccountDTO();
        AccountBaseDomain accountBaseDomain = new AccountBaseDomain();
        accountBaseDomain.setShopId(shopId);
        accountBaseDomain = accountBaseService.getSingleByBeanProp(accountBaseDomain);
        accountDTO.setAccountBaseDomain(accountBaseDomain);
        return accountDTO;
    }
}
