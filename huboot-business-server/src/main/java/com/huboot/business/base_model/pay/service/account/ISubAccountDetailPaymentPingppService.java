package com.huboot.business.base_model.pay.service.account;

import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentPingppDomain;
import com.huboot.business.base_model.pay.dto.account.PaymentPingppForDetailDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailPaymentPingppDTO;
import com.huboot.business.base_model.pay.enums.CustomerPaymentPingppOperateTypeEnum;
import com.huboot.business.base_model.pay.service.account.impl.SubAccountDetailPaymentPingppServiceImpl;
import com.huboot.business.mybatis.IBaseService;
import com.pingplusplus.model.Refund;
import com.pingplusplus.model.Transfer;

import java.math.BigDecimal;

/**
 * 账户中心-子账户明细支付PINGPPService
 */
public interface ISubAccountDetailPaymentPingppService extends IBaseService<SubAccountDetailPaymentPingppDomain, Long> {
    /**
     * pingpp支付
     *
     * @return
     */
    String createCharge(SubAccountDetailPaymentPingppDTO subAccountDetailPaymentPingppDTO, SubAccountDetailPaymentPingppServiceImpl.PayFrom type);

    /**
     * pingpp支付-代付
     *
     * @return
     */
    Transfer transfer(PaymentPingppForDetailDTO paymentPingppForDetailDTO);

    /**
     *
     */
    Transfer getTransferByPaymentId(Long paymentId);

    /**
     *
     * @param paymengId
     * @return
     */
    SubAccountDetailPaymentPingppDomain findByPaymentId(Long paymengId);
    SubAccountDetailPaymentPingppDomain findByPingppIdAndOperateType(String pingppId, CustomerPaymentPingppOperateTypeEnum typeEnum);

    /**
     * pingpp退款
     *
     * @return
     */
    Refund createRefund(Long paymentId, String paymentSn, String chargeId, String description, BigDecimal amount);
}
