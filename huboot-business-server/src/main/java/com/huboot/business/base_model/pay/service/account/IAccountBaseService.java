package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.mybatis.IBaseService;

/**
 *账户中心-账户基础信息Service
 */
public interface IAccountBaseService extends IBaseService<AccountBaseDomain, Long> {
    /**
     * 根据主账户ID查询对应的主账户基础信息
     *
     * @param accountId
     * @return
     * @throws
     */
    AccountBaseDomain findByAccountId(Long accountId);

    /**
     * 根据店铺id查询对应的主账户基础信息
     *
     * @param shopId
     * @return
     * @throws
     */
    AccountBaseDomain findByShopId(Long shopId);
}
