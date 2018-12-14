package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentBaseDomain;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.IBaseService;

/**
 * 账户中心-子账户明细支付基础信息Service
 */
public interface ISubAccountDetailPaymentBaseService extends IBaseService<SubAccountDetailPaymentBaseDomain, Long> {
    /**
     * 根据会员支付类型
     *
     * @param payType 会员支付类型
     * @throws ApiException
     */
    public SubAccountDetailPaymentBaseDomain findByPayType(Integer payType) throws ApiException;

    /**
     * 根据会员支付类型
     *
     * @return payMethod 会员支付平台
     * @throws ApiException
     */
    public SubAccountDetailPaymentBaseDomain findByPlatform(Integer platform) throws ApiException;
}
