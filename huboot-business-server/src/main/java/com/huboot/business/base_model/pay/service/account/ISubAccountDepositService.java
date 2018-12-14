package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDepositDomain;
import com.huboot.business.base_model.pay.dto.account.BuyerBaseDTO;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.IBaseService;
import com.huboot.business.mybatis.Pager;
import com.huboot.business.mybatis.QueryCondition;

/**
 *账户中心-押金账户扩展Service
 */
public interface ISubAccountDepositService extends IBaseService<SubAccountDepositDomain, Long> {
    /**
     * 创建押金账户
     *
     * @param vo
     * @return
     * @throws ApiException
     */
    //public Long create(SubAccountBaseSaveVO vo) throws ApiException;

    /**
     * 根据子账户id查询结果
     *
     * @param subAccountId
     * @return
     */
    public SubAccountDepositDomain findBySubAccountId(Long subAccountId);

    /**
     * 根据卖家查询买家押金账户或店铺ID查询此店铺的押金账户
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<BuyerBaseDTO> findBuyerDepositBySeller(QueryCondition queryCondition);
    /**
     * 检查买卖家账户关系
     * @param buyerSubAccountId
     * @param sellerShopId
     * @return true-有关系，false-无关系
     */
    Boolean checkBuyerAndSellerRela(Long buyerSubAccountId, Long sellerShopId);

    /**
     * 通过店铺id查询押金账户对应卖家信息
     * @param shopId
     * @return
     */
    SubAccountDepositDomain findByShopId(Long shopId);
}
