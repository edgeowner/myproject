package com.huboot.business.base_model.pay.service.account.impl;

import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailPaymentBaseDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentBaseDomain;
import com.huboot.business.base_model.pay.service.account.ISubAccountDetailPaymentBaseService;
import com.huboot.business.mybatis.AbstractBaseService;
import com.huboot.business.mybatis.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户中心-子账户明细支付基础信息ServiceImpl
 */
@Service("subAccountDetailPaymentBaseServiceImpl")
public class SubAccountDetailPaymentBaseServiceImpl extends AbstractBaseService<SubAccountDetailPaymentBaseDomain, Long> implements ISubAccountDetailPaymentBaseService {

    @Autowired
    public SubAccountDetailPaymentBaseServiceImpl(ISubAccountDetailPaymentBaseDao subAccountDetailPaymentBaseDao) {
        super(subAccountDetailPaymentBaseDao);
    }

    @Override
    public SubAccountDetailPaymentBaseDomain findByPayType(Integer payType) throws ApiException {
        SubAccountDetailPaymentBaseDomain domain = new SubAccountDetailPaymentBaseDomain();
        domain.setType(payType);
        return this.getSingleByBeanProp(domain);
    }

    @Override
    public SubAccountDetailPaymentBaseDomain findByPlatform(Integer platform) throws ApiException {
        SubAccountDetailPaymentBaseDomain domain = new SubAccountDetailPaymentBaseDomain();
        domain.setPlatform(platform);
        return this.getFirstByBeanProp(domain);
    }
}
