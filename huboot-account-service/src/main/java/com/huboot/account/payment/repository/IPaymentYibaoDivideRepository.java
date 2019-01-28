package com.huboot.account.payment.repository;

import com.huboot.account.payment.entity.PaymentYibaoDivideEntity;
import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 支付优惠信息控制表Repository
 */
@Repository("paymentYibaoDivideRepository")
public interface IPaymentYibaoDivideRepository extends IBaseRepository<PaymentYibaoDivideEntity> {

    List<PaymentYibaoDivideEntity> findByPaymentOrderSnAndStatusIn(String orderSn, List<String> status);

    PaymentYibaoDivideEntity findOneByReqId(String reqId);

    PaymentYibaoDivideEntity findOneByPaymentOrderSn(String paymentOrderSn);
}
