package com.huboot.account.payment.service.impl;

import com.huboot.account.account.entity.AccountEntity;
import com.huboot.account.payment.entity.PaymentYibaoDivideEntity;
import com.huboot.account.account.dto.SubAccountAmountChangeResultDTO;
import com.huboot.account.account.entity.SubAccountBillEntity;
import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.account.account.repository.IAccountRepository;
import com.huboot.account.account.repository.ISubAccountBillRepository;
import com.huboot.account.account.repository.ISubAccountRepository;
import com.huboot.account.account.service.ISubAccountBillService;
import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentOrderItemEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentOrderItemRepository;
import com.huboot.account.payment.repository.IPaymentOrderRepository;
import com.huboot.account.payment.repository.IPaymentSequenceRepository;
import com.huboot.account.payment.repository.IPaymentYibaoDivideRepository;
import com.huboot.account.payment.service.IPayGateway;
import com.huboot.account.payment.service.IPaymentOrderCreateService;
import com.huboot.account.payment.service.IPaymentOrderService;
import com.huboot.account.payment.service.IRefundGateway;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.BigDecimalUtil;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.account_service.api.dto.CloseReqDTO;
import com.huboot.share.account_service.api.dto.RefundReqDTO;
import com.huboot.share.account_service.api.dto.RewardPaymentCreateReqDTO;
import com.huboot.share.account_service.api.dto.WriteoffPaymentCreateReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.OPDetailsRespDTO;
import com.huboot.share.account_service.api.dto.order_payment.OPItemDetailsRespDTO;
import com.huboot.share.account_service.api.dto.order_payment.PayReqDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.CashierRespDTO;
import com.huboot.share.account_service.constant.PayConfigConstant;
import com.huboot.share.account_service.enums.*;
import com.huboot.share.common.constant.SignConstant;
import com.huboot.share.common.enums.AbleEnum;
import com.huboot.share.common.enums.YesOrNoEnum;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付订单表ServiceImpl
 */
@Service("paymentOrderServiceImpl")
@Slf4j
public class PaymentOrderServiceImpl implements IPaymentOrderService {

    @Autowired
    private IPaymentOrderRepository orderRepository;
    @Autowired
    private IPaymentOrderCreateService createService;
    @Autowired
    private IPayGateway payGateway;
    @Autowired
    private IRefundGateway refundGateway;
    @Autowired
    private ISubAccountBillService accountBillService;
    @Autowired
    private ISubAccountService subAccountService;

    @Autowired
    private IPaymentOrderItemRepository orderItemRepository;

    @Autowired
    private IPaymentYibaoDivideRepository paymentYibaoDivideRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ISubAccountRepository subAccountRepository;

    @Autowired
    private ISubAccountBillRepository subAccountBillRepository;
    @Autowired
    private IPaymentSequenceRepository sequenceRepository;


    /**
     * 奖励
     *
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public String reward(RewardPaymentCreateReqDTO reqDTO) {
        if (BigDecimalUtil.lte(reqDTO.getAmount(), BigDecimal.ZERO)) {
            throw new BizException("奖励金额不能小于0");
        }
        if (!PayTypeEnum.offline_person.equals(reqDTO.getPayType())
                && !PayTypeEnum.offline_system.equals(reqDTO.getPayType())) {
            throw new BizException("支付方式错误");
        }
        //创建订单
        PaymentOrderEntity orderEntity = createService.createRewardOrder(reqDTO);
        //创建支付
        PaymentSequenceEntity sequenceEntity = payGateway.pay(orderEntity, reqDTO.getPayType(), null);
        //更新收款方账户金额
        SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(orderEntity.getToSubAccount(), orderEntity.getAmount(), SignConstant.POSITIVE,false);
        //创建账单
        accountBillService.createRewardBillForAll(orderEntity, sequenceEntity, resultDTO, reqDTO.getRemark());
        //更新订单
        finish(orderEntity.getId(), sequenceEntity.getId());

        return orderEntity.getSn();
    }

    @Override
    public void finish(Long orderId, Long successPayseqId) {
        AppAssert.notNull(orderId, "orderId不能为空");
        AppAssert.notNull(successPayseqId, "successPayseqId不能为空");
        PaymentOrderEntity orderEntity = orderRepository.find(orderId);
        AppAssert.notNull(orderEntity, "支付订单不存在");
        orderEntity.setStatus(PaymentOrderStatusEnum.finish);
        orderEntity.setSuccessPayseqId(successPayseqId);
        orderRepository.modify(orderEntity);
    }

    /**
     * 核销
     *
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public String writeoff(WriteoffPaymentCreateReqDTO reqDTO) {
        if (BigDecimalUtil.lte(reqDTO.getAmount(), BigDecimal.ZERO)) {
            throw new BizException("核销金额不能小于0");
        }
        if (!PayTypeEnum.offline_person.equals(reqDTO.getPayType())
                && !PayTypeEnum.offline_system.equals(reqDTO.getPayType())) {
            throw new BizException("支付方式错误");
        }
        //创建订单
        PaymentOrderEntity orderEntity = createService.createWriteoffOrder(reqDTO);
        //创建支付
        Map<String, Object> extendMap = new HashMap<>();
        extendMap.put(PayConfigConstant.OFFLINESEQ, reqDTO.getOfflineSeq());
        PaymentSequenceEntity sequenceEntity = payGateway.pay(orderEntity, reqDTO.getPayType(), extendMap);
        //更新收款方账户金额
        SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(orderEntity.getFromSubAccount(), orderEntity.getAmount(), SignConstant.NEGATIVE,false);
        //创建账单
        accountBillService.createWriteoffBillForAll(orderEntity, sequenceEntity, resultDTO, reqDTO.getRemark());
        //更新订单
        finish(orderEntity.getId(), sequenceEntity.getId());

        return orderEntity.getSn();
    }

    /**
     *
     * @param refundReqDTO
     * @return
     */
    @Transactional
    @Override
    public String refund(RefundReqDTO refundReqDTO) {
        PaymentOrderEntity paymentOrderEntity = orderRepository.findOneBySn(refundReqDTO.getPaymentSn());
        if(paymentOrderEntity == null) {
            log.error("支付单不存在, sn=" + refundReqDTO.getPaymentSn());
            throw new BizException("支付单不存在");
        }
        if(!PaymentOrderStatusEnum.finish.equals(paymentOrderEntity.getStatus())) {
            throw new BizException("支付单没有完成，不能创建退款");
        }
        PaymentSequenceEntity successSequence = sequenceRepository.find(paymentOrderEntity.getSuccessPayseqId());
        AppAssert.notNull(successSequence, "支付成功流水不存在");
        //
        PaymentOrderEntity refundEntity = createService.createRefundOrder(paymentOrderEntity, refundReqDTO.getAmount());
        //
        Map<String, Object> extendMap = new HashMap<>();
        extendMap.put("paymentOrder", paymentOrderEntity);
        extendMap.put("successSequence", successSequence);
        refundGateway.refund(refundEntity, extendMap);

        return refundEntity.getSn();
    }

    @Override
    public String tradePay(PayReqDTO payReqDTO, String ip) {
        PaymentOrderEntity orderEntity = orderRepository.findOneBySn(payReqDTO.getPamentSn());
        Map<String, Object> extendMap = new HashMap<>();
        extendMap.put("payReqDTO", payReqDTO);
        extendMap.put("ip", ip);
        PaymentSequenceEntity sequenceEntity = payGateway.pay(orderEntity, payReqDTO.getPayType(), extendMap);
        String resp = sequenceEntity.getTripartiteResponse();
        if(StringUtils.isEmpty(resp)) {
            return null;
        }
        CashierRespDTO cashierRespDTO = JsonUtil.buildNormalMapper().fromJson(resp, CashierRespDTO.class);
        return cashierRespDTO.getResultData();
    }

    /**
     * check account
     **/
    public Tuple2<AccountEntity, SubAccountEntity> checkAccount(String id, SubAccountTypeEnum type, Boolean checkYibbaoAccount) {
        AccountEntity entity = accountRepository.findByRelaId(id);
        if (entity == null || AbleEnum.disable.equals(entity.getStatus())) {
            log.error("账户异常:{}", entity);
            throw new BizException("账户异常");
        }
        if(checkYibbaoAccount && StringUtils.isEmpty(entity.getYibaoMerchantNo())) throw new BizException("易宝账户没有开通");
        SubAccountEntity subAccountEntity = subAccountRepository.findByAccountIdAndType(entity.getId(), type);
        if (subAccountEntity == null || AbleEnum.disable.equals(subAccountEntity.getStatus())) {
            log.error("账户异常:{}", subAccountEntity);
            throw new BizException("账户异常");
        }
        return new Tuple2(entity, subAccountEntity);
    }

    /***
     * 查询支付单
     *
     * @param paymentSn
     **/
    @Override
    public OPDetailsRespDTO queryOrderPayment(String paymentSn) {
        PaymentOrderEntity orderEntity = orderRepository.findOneBySn(paymentSn);
        if (orderEntity == null) return null;
        OPDetailsRespDTO respDTO = new OPDetailsRespDTO();
        BeanUtils.copyProperties(orderEntity, respDTO);
        List<PaymentOrderItemEntity> items = orderItemRepository.findByOrderId(orderEntity.getId());
        if (items != null && items.size() > 0) respDTO.setItems(items.stream().map(s -> {
            OPItemDetailsRespDTO t = new OPItemDetailsRespDTO();
            BeanUtils.copyProperties(s, t);
            return t;
        }).collect(Collectors.toList()));
        return respDTO;
    }

    /***
     * 解冻账户金额
     * **/
    @Transactional
    public void thawAccountBalance( CloseReqDTO closeReqDTO){
        PaymentOrderEntity paymentOrderEntity = orderRepository.findOneBySn(closeReqDTO.getPaymentSn());
        if(paymentOrderEntity == null) throw new BizException("支付单异常");
        if(PaymentOrderStatusEnum.colse.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付已关闭");
        if(PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付还未完成");
        paymentOrderEntity.setFreezeAfterPaid(YesOrNoEnum.no);
        orderRepository.modify(paymentOrderEntity);
        //更新账单状态
        SubAccountBillEntity billEntity = subAccountBillRepository.findOneByOrderIdAndType(paymentOrderEntity.getId(), BillTypeEnum.order_income);
        if(billEntity == null) throw new BizException("账单不存在");
        billEntity.setStatus(SubAccountStatusEnum.has_received);
        subAccountBillRepository.modify(billEntity);
        //解冻账户余额
        subAccountService.changeAmount(paymentOrderEntity.getToSubAccount(), paymentOrderEntity.getAmount(), SignConstant.NEGATIVE,true);
        subAccountService.changeAmount(paymentOrderEntity.getToSubAccount(), paymentOrderEntity.getAmount(), SignConstant.POSITIVE,false);
        //创建可分账记录(直客租金)
        if(PaymentOrderSourceEnum.zkrent_rent.equals(paymentOrderEntity.getSource())) crateYibaoDivideEntity(paymentOrderEntity.getSn(),paymentOrderEntity.getAmount());
    }

    public void crateYibaoDivideEntity(String panymentOrderSn,BigDecimal amount){
        //create local divide
        PaymentYibaoDivideEntity yibaoDivideEntity = new PaymentYibaoDivideEntity();
        yibaoDivideEntity.setPaymentOrderSn(panymentOrderSn);
        //yibaoDivideEntity.setMerchantName(paymentOrderEntity.getSn());
       if(amount != null) yibaoDivideEntity.setAmount(amount);
        yibaoDivideEntity.setStatus(PaymentYibaoDivideEntity.STATUS_ACCEPT);
        paymentYibaoDivideRepository.create(yibaoDivideEntity);
    }
}