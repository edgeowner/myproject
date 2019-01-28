package com.huboot.account.payment.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.huboot.account.payment.entity.PaymentSequenceEntity;

import java.util.List;

/**
*支付流水表Repository
*/
@Repository("paymentSequenceRepository")
public interface IPaymentSequenceRepository extends IBaseRepository<PaymentSequenceEntity> {

    List<PaymentSequenceEntity> findByTripartiteSeq(String tripartiteSeq);

    List<PaymentSequenceEntity> findByOrderId(Long orderId);

    @Query(value = "select * from ac_payment_sequence where weixin_openid = :openId and order_id = :orderId order by create_time desc limit 1",nativeQuery = true)
    PaymentSequenceEntity findOneByWeixinOpenidAndOrderId(@Param("openId")String openId,@Param("orderId") Long orderId);


    PaymentSequenceEntity findByTripartiteId(String tripartiteId);

}
