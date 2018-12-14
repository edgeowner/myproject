package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.dao.account.IAccountBaseDao;
import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.base_model.pay.service.account.IAccountBaseService;
import com.huboot.business.mybatis.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *账户中心-账户基础信息ServiceImpl
 */
@Service("accountBaseServiceImpl")
public class AccountBaseServiceImpl extends AbstractBaseService<AccountBaseDomain, Long> implements IAccountBaseService {

    @Autowired
    public AccountBaseServiceImpl(IAccountBaseDao accountBaseDao ) {
        super(accountBaseDao);
    }

    @Override
    public AccountBaseDomain findByAccountId(Long accountId) {
        Assert.notNull(accountId);
        AccountBaseDomain query = new AccountBaseDomain();
        query.setAccountId(accountId);
        return this.getFirstByBeanProp(query);
    }

    @Override
    public AccountBaseDomain findByShopId(Long shopId) {
        Assert.notNull(shopId);
        AccountBaseDomain query = new AccountBaseDomain();
        query.setShopId(shopId);
        return this.getFirstByBeanProp(query);
    }
}
