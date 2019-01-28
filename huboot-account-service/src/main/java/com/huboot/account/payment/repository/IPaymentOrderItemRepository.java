package com.huboot.account.payment.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.account.payment.entity.PaymentOrderItemEntity;

import java.util.List;

/**
*支付订单条目表Repository
*/
@Repository("paymentOrderItemRepository")
public interface IPaymentOrderItemRepository extends IBaseRepository<PaymentOrderItemEntity> {

    PaymentOrderItemEntity findOneByTradeOrderSnAndTradeOrderType(String tradeOrderSn, String tradeOrderType);

    List<PaymentOrderItemEntity> findByOrderId(Long orderId);
}
