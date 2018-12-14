package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountBaseDomain;
import com.huboot.business.base_model.pay.dto.account.BuyerBaseDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountBaseDTO;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.IBaseService;
import com.huboot.business.mybatis.Pager;
import com.huboot.business.mybatis.QueryCondition;

/**
 *账户中心-子账户基础信息Service
 */
public interface ISubAccountBaseService extends IBaseService<SubAccountBaseDomain, Long> {
    /**
     * 通过子账户id查询结果
     * @param subAccountId
     * @return
     */
    SubAccountBaseDomain findBySubAccountId(Long subAccountId);
    /**
     * 分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<SubAccountBaseDomain> findByAdminQueryForPager(QueryCondition queryCondition);

    /**
     * 更新子账户余额--只有子账户明细发生变化，才能调用本方法
     *  adjustBalance区分正数负数
     * @param subAccountBaseDTO--更新的金额
     */
    void updateBalanceForDetail(SubAccountBaseDTO subAccountBaseDTO);

    /**
     * 根据主账户id和类型查询子账户信息
     * @return
     * @throws ApiException
     */
    SubAccountBaseDomain findAccountByAccountIdWithType(Long accountId, Integer type) throws ApiException;

    /**
     * 根据买家店铺id和类型查询子账户信息
     * @return
     * @throws ApiException
     */
    SubAccountBaseDomain findAccountByShopIdWithType(Long shopId, Integer type) throws ApiException;

    /**
     * 根据卖家查询买家押金账户或店铺ID查询此店铺的押金账户
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<BuyerBaseDTO> findSubAccountBaseByShopIdandType(QueryCondition queryCondition);

    /**
     * 余额账户OR红包账户分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<SubAccountBaseDomain> findBalanceOrRedPacketAccountForPager(QueryCondition queryCondition) throws ApiException;


    /**
     * 更新状态
     * @param subAccountId
     * @param status
     */
    void updateStatus(Long subAccountId, Integer status);
}
