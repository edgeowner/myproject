package com.huboot.account.account.service;

import com.huboot.account.account.dto.SubAccountAmountChangeResultDTO;
import com.huboot.account.account.dto.wycshop.BrokerageAccountDetailDTO;
import com.huboot.account.account.dto.wycshop.SubAccountPagerDTO;
import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.account_service.api.dto.BrokerageDetail;
import com.huboot.share.account_service.api.dto.SubAccountCreateReqDTO;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegStatusRespDTO;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 子账户Service
 */
public interface ISubAccountService {

    void exchengeRelaId();

    BrokerageDetail getBrokerageDetail(String relaId);

    /**
     * @param relaId
     * @param subAccountType
     * @return
     */
    SubAccountDTO getSubAccount(String relaId, SubAccountTypeEnum subAccountType);

    /**
     * @param reqDTO
     */
    Long createSubAccount(SubAccountCreateReqDTO reqDTO);


    /**
     * @param relaId
     * @param subAccountType
     * @return
     */
    SubAccountEntity getEnableSubAccount(String relaId, SubAccountTypeEnum subAccountType);

    /**
     * @param fromSubAccountId
     * @param toSubAccountId
     */
    void checkSubAccountEnableForAll(Long fromSubAccountId, Long toSubAccountId);

    /**
     * @param accountId
     * @param relaId
     */
    void checkAccount(Long accountId, String relaId);


    /**
     * 更新账户可用余额
     *
     * @param subAccountId
     * @param amount
     * @param sign
     * @return
     */
    SubAccountAmountChangeResultDTO changeAmount(Long subAccountId, BigDecimal amount, String sign, Boolean isFrozen);

    /**
     * @param name
     * @param phone
     * @param idcar
     * @param page
     * @param size
     * @return
     */
    ShowPageImpl<SubAccountPagerDTO> getWycBrokerageSubAccountPager(String name, String phone, String idcar, Integer page, Integer size);

    BrokerageAccountDetailDTO getBrokerageAccountDetail(Long userId);

    /**
     * 创建易宝资金账户（商户在易宝平台分润结算账户）
     *
     * @param reqDTO
     *
     * @return merchantNo
     */
    String createSettlementAccount(Map<String, String> reqDTO) throws IOException;

    /**
     * 入网状态查询
     *
     * @param accountId
     */
    YibaoRegStatusRespDTO querySettlementAccount(Long accountId) throws IOException;

}
