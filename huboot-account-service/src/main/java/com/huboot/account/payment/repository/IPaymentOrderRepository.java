package com.huboot.account.payment.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.account.payment.entity.PaymentOrderEntity;

/**
*支付订单表Repository
*/
@Repository("paymentOrderRepository")
public interface IPaymentOrderRepository extends IBaseRepository<PaymentOrderEntity> {

    PaymentOrderEntity findOneBySn(String sn);


}
