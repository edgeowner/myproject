package com.huboot.account.payment.service.impl;

import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.payment.entity.PaymentOrderItemEntity;
import com.huboot.account.payment.repository.IPaymentOrderItemRepository;
import com.huboot.account.payment.service.IPaymentOrderCreateService;
import com.huboot.account.support.yibao.YiBaoService;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.repository.IPaymentOrderRepository;
import com.xiehua.commons.component.exception.BizException;
import com.xiehua.commons.utils.BigDecimalUtil;
import com.xiehua.commons.utils.SnGenerator;
import com.xiehua.share.account_service.api.dto.CreateOrderPaymentRespDTO;
import com.xiehua.share.account_service.api.dto.RefundReqDTO;
import com.xiehua.share.account_service.api.dto.RewardPaymentCreateReqDTO;
import com.xiehua.share.account_service.api.dto.WriteoffPaymentCreateReqDTO;
import com.xiehua.share.account_service.api.dto.order_payment.CreateOPReqDTO;
import com.xiehua.share.account_service.enums.PaymentOrderSourceEnum;
import com.xiehua.share.account_service.enums.PaymentOrderStatusEnum;
import com.xiehua.share.account_service.enums.PaymentOrderTypeEnum;
import com.xiehua.share.account_service.enums.SubAccountTypeEnum;
import com.xiehua.share.common.enums.YesOrNoEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
@Service("paymentOrderCreateServiceImpl")
public class PaymentOrderCreateServiceImpl implements IPaymentOrderCreateService {

    @Autowired
    private IPaymentOrderRepository paymentOrderRepository;
    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private IPaymentOrderItemRepository orderItemRepository;

    /**
     * 创建奖励订单
     * @param reqDTO
     * @return
     */
    @Override
    public PaymentOrderEntity createRewardOrder(RewardPaymentCreateReqDTO reqDTO) {
        //使用付款方三方账户
        SubAccountEntity fromSubAccount = subAccountService.getEnableSubAccount(reqDTO.getFromId(), SubAccountTypeEnum.tripartite);
        SubAccountEntity toSubAccount = subAccountService.getEnableSubAccount(reqDTO.getToId(), reqDTO.getToSubAccountType());

        PaymentOrderEntity orderEntity = new PaymentOrderEntity();
        orderEntity.setSn(SnGenerator.generatorPaymentOrder());
        orderEntity.setType(PaymentOrderTypeEnum.reward);
        orderEntity.setFromSubAccount(fromSubAccount.getId());
        orderEntity.setToSubAccount(toSubAccount.getId());
        orderEntity.setSource(reqDTO.getSource());
        orderEntity.setAmount(reqDTO.getAmount());
        orderEntity.setStatus(PaymentOrderStatusEnum.submit);
        orderEntity.setFreezeAfterPaid(YesOrNoEnum.no);
        paymentOrderRepository.create(orderEntity);
        return orderEntity;
    }

    /**
     *
     * @param reqDTO
     * @return
     */
    @Override
    public PaymentOrderEntity createWriteoffOrder(WriteoffPaymentCreateReqDTO reqDTO) {
        SubAccountEntity fromSubAccount = subAccountService.getEnableSubAccount(reqDTO.getRelaId(), reqDTO.getSubAccountType());
        //核销的钱去他自己的三方账户
        SubAccountEntity toSubAccount = subAccountService.getEnableSubAccount(reqDTO.getRelaId(), SubAccountTypeEnum.tripartite);

        if(BigDecimalUtil.gt(reqDTO.getAmount(), fromSubAccount.getUsableBalance())) {
            throw new BizException("核销金额不能大于账户可用余额");
        }

        PaymentOrderEntity orderEntity = new PaymentOrderEntity();
        orderEntity.setSn(SnGenerator.generatorPaymentOrder());
        orderEntity.setType(PaymentOrderTypeEnum.writeoff);
        orderEntity.setFromSubAccount(fromSubAccount.getId());
        orderEntity.setToSubAccount(toSubAccount.getId());
        orderEntity.setSource(reqDTO.getSource());
        orderEntity.setAmount(reqDTO.getAmount());
        orderEntity.setStatus(PaymentOrderStatusEnum.submit);
        orderEntity.setFreezeAfterPaid(YesOrNoEnum.no);
        paymentOrderRepository.create(orderEntity);
        return orderEntity;
    }

    /***
     * 创建支付单
     * @return 支付单号
     * @param createOPReqDTO **/
    @Transactional
    @Override
    public CreateOrderPaymentRespDTO createOrderPayment(CreateOPReqDTO createOPReqDTO) throws IOException, InvocationTargetException, IllegalAccessException {
        //if (createOPReqDTO.getItems().stream().map(s -> orderItemRepository.findOneByTradeOrderSnAndTradeOrderType(s.getTradeOrderSn(), s.getTradeOrderType())).filter(Objects::nonNull).count() > 0) throw new BizException("不能重复创建支付单");
        //check buyer account
        //Tuple2<AccountEntity, SubAccountEntity> buyerAccount = checkAccount(createOPReqDTO.getUserId(), SubAccountTypeEnum.tripartite,false);
        //Tuple2<AccountEntity, SubAccountEntity> sellerAccount = checkAccount(createOPReqDTO.getCompanyId(), SubAccountTypeEnum.balance,false);

        SubAccountEntity buyerAccount = subAccountService.getEnableSubAccount(createOPReqDTO.getUserId(), SubAccountTypeEnum.tripartite);
        //核销的钱去他自己的三方账户
        SubAccountEntity sellerAccount = subAccountService.getEnableSubAccount(createOPReqDTO.getCompanyId(), SubAccountTypeEnum.balance);

        //检查收款方易宝账户可用性
        //YibaoRegStatusRespDTO regStatusRespDTO = yiBaoService.reqInfoQuery(sellerAccount._1.getYibaoMerchantNo());
        //if(STATUS_SUCCESS.equals(regStatusRespDTO.getMerNetInOutStatus())) throw new BizException("账户不可用");

        String sn = SnGenerator.generatorPaymentOrder();
        BigDecimal amount = createOPReqDTO.getItems().stream().map(s -> s.getTradeOrderAmount()).reduce(BigDecimal.ZERO, (x, y) -> x.add(y));
        //crate payment order
        PaymentOrderEntity entity = new PaymentOrderEntity();
        BeanUtils.copyProperties(createOPReqDTO, entity);
        entity.setType(createOPReqDTO.getPayType());
        entity.setFromSubAccount(buyerAccount.getId());
        entity.setToSubAccount(sellerAccount.getId());
        entity.setSn(sn);
        entity.setAmount(amount);
        entity.setStatus(PaymentOrderStatusEnum.submit);
        entity.setExpireTime(LocalDateTime.now().plusMinutes(YiBaoService.YIBAO_ORDER_TIMEOUT));
        paymentOrderRepository.create(entity);
        //crate payment order item
        createOPReqDTO.getItems().forEach(s -> {
            PaymentOrderItemEntity i = new PaymentOrderItemEntity();
            BeanUtils.copyProperties(s, i);
            i.setOrderId(entity.getId());
            orderItemRepository.create(i);
        });

        CreateOrderPaymentRespDTO createOrderPaymentRespDTO = new CreateOrderPaymentRespDTO();
        createOrderPaymentRespDTO.setPaymentOrderId(entity.getId());
        createOrderPaymentRespDTO.setPaymentOrderSn(entity.getSn());
        return createOrderPaymentRespDTO;
    }

    @Override
    public PaymentOrderEntity createRefundOrder(PaymentOrderEntity paymentOrder, BigDecimal actualAmount) {
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setSn(SnGenerator.generatorPaymentOrder());
        entity.setType(PaymentOrderTypeEnum.trade_refund);
        entity.setFromSubAccount(paymentOrder.getToSubAccount());
        entity.setToSubAccount(paymentOrder.getFromSubAccount());
        entity.setSource(paymentOrder.getSource());
        entity.setAmount(actualAmount);
        entity.setStatus(PaymentOrderStatusEnum.submit);
        entity.setRefundRelaPaySn(paymentOrder.getSn());
        entity.setFreezeAfterPaid(YesOrNoEnum.no);
        return paymentOrderRepository.create(entity);
    }
}
