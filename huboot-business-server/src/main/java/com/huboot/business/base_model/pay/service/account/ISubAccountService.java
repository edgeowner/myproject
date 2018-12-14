package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDomain;
import com.huboot.business.base_model.pay.dto.account.SubAccountDTO;
import com.huboot.business.mybatis.IBaseService;

/**
 *账户中心-子账户Service
 */
public interface ISubAccountService extends IBaseService<SubAccountDomain, Long> {
    /**
     * 初始余额账户
     * @param accountId
     * @return
     */
    SubAccountDTO initBalanceAccount(Long accountId) ;
    /**
     * 初始红包账户
     * @param accountId
     * @return
     */
    SubAccountDTO initRedPacketAccount(Long accountId) ;
    /**
     * 初始第三方支付账户
     * @param accountId
     * @return
     */
    SubAccountDTO initThirdPartyAccount(Long accountId) ;
    /**
     * 初始第三方支付账户
     * @param accountId
     * @return
     */
    SubAccountDTO initBillAccount(Long accountId) ;
    /**
     * 查询余额账户
     * @param accountId
     * @return
     */
    SubAccountDTO findBalanceByAccountId(Long accountId) ;

    /**
     * 查询余额账户
     * @param shopId
     * @return
     */
    SubAccountDTO findBalanceByShopId(Long shopId) ;
    /**
     * 查询余额账户
     * @param accountId
     * @return
     */
    SubAccountDTO findRedPacketByAccountId(Long accountId) ;

    /**
     * 查询余额账户
     * @param shopId
     * @return
     */
    SubAccountDTO findRedPacketByShopId(Long shopId) ;
    /**
     * 查询余额账户
     * @param accountId
     * @return
     */

    SubAccountDTO findDepositByAccountId(Long accountId) ;

    /**
     * 查询余额账户
     * @param shopId
     * @return
     */
    SubAccountDTO findDepositByShopId(Long shopId) ;
    /**
     * 查询临时账户
     * @param accountId
     * @return
     */

    SubAccountDTO findBillByAccountId(Long accountId) ;

    /**
     * 查询临时账户
     * @param shopId
     * @return
     */
    SubAccountDTO findBillByShopId(Long shopId) ;
}
