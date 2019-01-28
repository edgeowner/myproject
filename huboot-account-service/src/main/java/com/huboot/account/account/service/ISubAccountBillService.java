package com.huboot.account.account.service;


import com.huboot.account.account.dto.SubAccountAmountChangeResultDTO;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;

/**
 *Service
 */
public interface ISubAccountBillService {

    /**
     * 创建收付款双方成功账单
     * @param orderEntity
     * @param sequenceEntity
     */
    void createRewardBillForAll(PaymentOrderEntity orderEntity, PaymentSequenceEntity sequenceEntity,
                                SubAccountAmountChangeResultDTO resultDTO, String remark);

    /**
     *
     * @param orderEntity
     * @param sequenceEntity
     * @param resultDTO
     */
    void createWriteoffBillForAll(PaymentOrderEntity orderEntity, PaymentSequenceEntity sequenceEntity,
                                  SubAccountAmountChangeResultDTO resultDTO, String remark);


}
