package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailExtendDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailCashExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPayItemsExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPaymentExtendDomain;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailExtendDTO;
import com.huboot.business.base_model.pay.service.account.ISubAccountDetailExtendService;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.mybatis.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 账户中心-子账户明细扩展ServiceImpl
 */
@Service("subAccountDetailExtendServiceImpl")
public class SubAccountDetailExtendServiceImpl extends AbstractBaseService<SubAccountDetailExtendDomain, Long> implements ISubAccountDetailExtendService {

    @Autowired
    public SubAccountDetailExtendServiceImpl(ISubAccountDetailExtendDao subAccountDetailExtendDao) {
        super(subAccountDetailExtendDao);
    }

    @Override
    public SubAccountDetailExtendDTO findByDetailId(Long detailId) {
        SubAccountDetailExtendDTO subAccountDetailExtendDTO = new SubAccountDetailExtendDTO();
        SubAccountDetailExtendDomain subAccountDetailExtendDomain = new SubAccountDetailExtendDomain();
        subAccountDetailExtendDomain.setSubAccountDetailId(detailId);
        subAccountDetailExtendDomain = this.getSingleByBeanProp(subAccountDetailExtendDomain);
        if(subAccountDetailExtendDomain ==null){
            return subAccountDetailExtendDTO;
        }
        subAccountDetailExtendDTO.setSubAccountDetailExtendDomain(subAccountDetailExtendDomain);
        if (StringUtils.hasText(subAccountDetailExtendDomain.getCashExtend())) {
            //提现信息
            String cashExtendJsonString = subAccountDetailExtendDomain.getCashExtend();
            subAccountDetailExtendDTO.setSubAccountDetailCashExtendDomain(JsonUtils.fromJson(cashExtendJsonString, SubAccountDetailCashExtendDomain.class));
        }
        if (StringUtils.hasText(subAccountDetailExtendDomain.getPaymentExtend())) {
            //提现信息
            String paymentExtendJsonString = subAccountDetailExtendDomain.getPaymentExtend();
            subAccountDetailExtendDTO.setSubAccountDetailPaymentExtendDomain(JsonUtils.fromJson(paymentExtendJsonString, SubAccountDetailPaymentExtendDomain.class));
        }
        if(StringUtils.hasText(subAccountDetailExtendDomain.getPayItemsExtend())) {
            String json = subAccountDetailExtendDomain.getPayItemsExtend();
            subAccountDetailExtendDTO.setPayItemsExtendDomainList(JsonUtils.fromJsonToList(json, SubAccountDetailPayItemsExtendDomain.class));
        }
        return subAccountDetailExtendDTO;
    }
}
