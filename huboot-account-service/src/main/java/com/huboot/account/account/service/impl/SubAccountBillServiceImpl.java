package com.huboot.account.account.service.impl;

import com.huboot.account.account.entity.SubAccountBillEntity;
import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.account.account.repository.ISubAccountBillRepository;
import com.huboot.account.account.repository.ISubAccountRepository;
import com.huboot.account.account.dto.SubAccountAmountChangeResultDTO;
import com.huboot.account.account.service.ISubAccountBillService;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.commons.utils.AppAssert;
import com.huboot.share.account_service.enums.SubAccountStatusEnum;
import com.huboot.share.common.constant.SignConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 *ServiceImpl
 */
@Service("subAccountBillServiceImpl")
public class SubAccountBillServiceImpl implements ISubAccountBillService {

    private Logger logger = LoggerFactory.getLogger(SubAccountBillServiceImpl.class);

    @Autowired
    private ISubAccountBillRepository subAccountBillRepository;
    @Autowired
    private ISubAccountRepository subAccountRepository;

    /**
     * 奖励账单
     * @param orderEntity
     * @param sequenceEntity
     * @param resultDTO
     */
    @Override
    public void createRewardBillForAll(PaymentOrderEntity orderEntity, PaymentSequenceEntity sequenceEntity,
                                       SubAccountAmountChangeResultDTO resultDTO, String remark) {
        SubAccountEntity fromSubAccount = subAccountRepository.find(orderEntity.getFromSubAccount());
        AppAssert.notNull(fromSubAccount, "付款方子账户不存在");
        SubAccountBillEntity fromBill = new SubAccountBillEntity();
        fromBill.setAccountId(fromSubAccount.getAccountId());
        fromBill.setSubAccountId(fromSubAccount.getId());
        fromBill.setSubAccounType(fromSubAccount.getType());
        fromBill.setStatus(SubAccountStatusEnum.has_pay);
        fromBill.setOrderSource(orderEntity.getSource());
        fromBill.setSign(SignConstant.NEGATIVE);
        fromBill.setPreAmount(BigDecimal.ZERO);
        fromBill.setAmount(sequenceEntity.getActualPayAmount());
        fromBill.setAfterAmount(BigDecimal.ZERO);
        fromBill.setOrderId(orderEntity.getId());
        fromBill.setPaymentSeqId(sequenceEntity.getId());
        fromBill.setRemark(remark);
        subAccountBillRepository.create(fromBill);

        SubAccountEntity toSubAccount = subAccountRepository.find(orderEntity.getToSubAccount());
        AppAssert.notNull(toSubAccount, "收款方子账户不存在");
        SubAccountBillEntity toBill = new SubAccountBillEntity();
        toBill.setAccountId(toSubAccount.getAccountId());
        toBill.setSubAccountId(toSubAccount.getId());
        toBill.setSubAccounType(toSubAccount.getType());
        toBill.setStatus(SubAccountStatusEnum.has_received);
        toBill.setOrderSource(orderEntity.getSource());
        toBill.setSign(SignConstant.POSITIVE);
        toBill.setPreAmount(resultDTO.getPreAmount());
        toBill.setAmount(orderEntity.getAmount());
        toBill.setAfterAmount(resultDTO.getAfterAmount());
        toBill.setOrderId(orderEntity.getId());
        toBill.setPaymentSeqId(sequenceEntity.getId());
        toBill.setRemark(remark);
        subAccountBillRepository.create(toBill);

    }

    /**
     * 核销账单
     * @param orderEntity
     * @param sequenceEntity
     * @param resultDTO
     * @param remark
     */
    @Override
    public void createWriteoffBillForAll(PaymentOrderEntity orderEntity, PaymentSequenceEntity sequenceEntity,
                                         SubAccountAmountChangeResultDTO resultDTO, String remark) {
        SubAccountEntity fromSubAccount = subAccountRepository.find(orderEntity.getFromSubAccount());
        AppAssert.notNull(fromSubAccount, "核销方子账户不存在");
        SubAccountBillEntity fromBill = new SubAccountBillEntity();
        fromBill.setAccountId(fromSubAccount.getAccountId());
        fromBill.setSubAccountId(fromSubAccount.getId());
        fromBill.setSubAccounType(fromSubAccount.getType());
        fromBill.setStatus(SubAccountStatusEnum.has_writeoff);
        fromBill.setOrderSource(orderEntity.getSource());
        fromBill.setSign(SignConstant.NEGATIVE);
        fromBill.setPreAmount(resultDTO.getPreAmount());
        fromBill.setAmount(orderEntity.getAmount());
        fromBill.setAfterAmount(resultDTO.getAfterAmount());
        fromBill.setOrderId(orderEntity.getId());
        fromBill.setPaymentSeqId(sequenceEntity.getId());
        fromBill.setRemark(remark);
        subAccountBillRepository.create(fromBill);

        SubAccountEntity toSubAccount = subAccountRepository.find(orderEntity.getToSubAccount());
        AppAssert.notNull(toSubAccount, "核销方子账户不存在");
        SubAccountBillEntity toBill = new SubAccountBillEntity();
        toBill.setAccountId(toSubAccount.getAccountId());
        toBill.setSubAccountId(toSubAccount.getId());
        toBill.setSubAccounType(toSubAccount.getType());
        toBill.setStatus(SubAccountStatusEnum.has_received);
        toBill.setOrderSource(orderEntity.getSource());
        toBill.setSign(SignConstant.POSITIVE);
        toBill.setPreAmount(BigDecimal.ZERO);
        toBill.setAmount(sequenceEntity.getActualPayAmount());
        toBill.setAfterAmount(BigDecimal.ZERO);
        toBill.setOrderId(orderEntity.getId());
        toBill.setPaymentSeqId(sequenceEntity.getId());
        toBill.setRemark(remark);
        subAccountBillRepository.create(toBill);
    }

}
