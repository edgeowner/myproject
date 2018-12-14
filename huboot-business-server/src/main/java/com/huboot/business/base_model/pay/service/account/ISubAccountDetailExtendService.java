package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDetailExtendDomain;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailExtendDTO;
import com.huboot.business.mybatis.IBaseService;

/**
 *账户中心-子账户明细扩展Service
 */
public interface ISubAccountDetailExtendService extends IBaseService<SubAccountDetailExtendDomain, Long> {

    SubAccountDetailExtendDTO findByDetailId(Long detailId);
}
