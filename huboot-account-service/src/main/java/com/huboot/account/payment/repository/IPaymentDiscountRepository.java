package com.huboot.account.payment.repository;

import com.huboot.account.payment.entity.PaymentDiscountEntity;
import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
*支付优惠信息控制表Repository
*/
@Repository("paymentDiscountRepository")
public interface IPaymentDiscountRepository extends IBaseRepository<PaymentDiscountEntity> {

}
