package com.huboot.account.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.account.account.entity.AccountEntity;
import com.huboot.account.account.entity.SubAccountBillEntity;
import com.huboot.account.account.repository.ISubAccountBillRepository;
import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.payment.entity.PaymentOrderItemEntity;
import com.huboot.account.payment.entity.PaymentYibaoDivideEntity;
import com.huboot.account.payment.repository.IPaymentOrderItemRepository;
import com.huboot.account.payment.service.impl.PaymentOrderServiceImpl;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.SnGenerator;
import com.huboot.share.account_service.api.dto.*;
import com.huboot.share.account_service.api.dto.order_payment.CreateOPItemReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.PayReqDTO;
import com.huboot.share.account_service.api.dto.yibao.bakabce.YiBaoBalanceRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.CashierRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.PaySuccessRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.YiBaoPayRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp2DTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp3DTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundRespDTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideDetailsDTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideResp2DTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideRespDTO;
import com.huboot.share.account_service.enums.*;
import com.huboot.share.common.constant.SignConstant;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.account.account.dto.SubAccountAmountChangeResultDTO;
import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.account.account.repository.ISubAccountRepository;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentOrderRepository;
import com.huboot.account.payment.repository.IPaymentSequenceRepository;
import com.huboot.account.payment.repository.IPaymentYibaoDivideRepository;
import com.huboot.account.support.yibao.YiBaoService;

import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayService {

    @Autowired
    private ISubAccountRepository subAccountRepository;

    @Autowired
    private ISubAccountBillRepository subAccountBillRepository;

    @Autowired
    private IPaymentOrderRepository paymentOrderRepository;

    @Autowired
    private IPaymentSequenceRepository paymentSequenceRepository;

    @Autowired
    private IPaymentYibaoDivideRepository paymentYibaoDivideRepository;

    @Autowired
    private ISubAccountService subAccountService;

    @Autowired
    private PaymentOrderServiceImpl paymentOrderService;

    @Autowired
    private IPaymentOrderItemRepository orderItemRepository;

    @Autowired
    private YiBaoService yiBaoService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserCacheData userCacheData;

    /***
     * 支付唤起
     * @return String 易宝支付token
     * @param payReqDTO**/
    @Transactional
    public String pay(final PayReqDTO payReqDTO, String ip) throws Exception {
        //check user
//        UserDetailInfo userDetailInfo = userCacheData.getCurrentUser();
//        if(userDetailInfo == null) throw new BizException("用户信息不存在");
        //check payment order
        PaymentOrderEntity paymentOrderEntity = checkPaymentOrderBySn(payReqDTO.getPamentSn());
        AppAssert.notNull(paymentOrderEntity, "支付单不存在");

        if(!PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())) {
            throw new BizException("支付单不是待支付状态");
        }

        List<PaymentOrderItemEntity> itemList = orderItemRepository.findByOrderId(paymentOrderEntity.getId());

        PaymentSequenceEntity paymentSequenceEntity = new PaymentSequenceEntity();
        BeanUtils.copyProperties(payReqDTO,paymentSequenceEntity);
        paymentSequenceEntity.setPayStatus(PayStatusEnum.wait);
        paymentSequenceEntity.setOrderId(paymentOrderEntity.getId());
        paymentSequenceEntity.setWeixinOpenid(userCacheData.getCurrentUserZkUserThirdOpenId().getValue());
        paymentSequenceEntity.setPayableAmount(paymentOrderEntity.getAmount());
        paymentSequenceEntity.setDiscountAmount(BigDecimal.ZERO);
        paymentSequenceEntity.setActualPayAmount(paymentOrderEntity.getAmount());
        paymentSequenceRepository.create(paymentSequenceEntity);


        //易宝下单
        PaySuccessRespDTO paySuccessRespDTO = yiBaoService.doOrder(paymentSequenceEntity.getId().toString(),
                paymentSequenceEntity.getActualPayAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "",
                mapper.writeValueAsString(itemList.stream().map(s ->{
                    CreateOPItemReqDTO o = new CreateOPItemReqDTO();
                    BeanUtils.copyProperties(s,o);
                    return o;
                }).collect(Collectors.toList()).get(0)));

        paymentSequenceEntity.setTripartiteId(paySuccessRespDTO.getUniqueOrderNo());
        paymentSequenceEntity.setYibaoToken(paySuccessRespDTO.getToken());


        //请求易宝支付接口
        Tuple2<CashierRespDTO,Map<String,String>>  resp = yiBaoService.pay(paySuccessRespDTO.getToken(),
                userCacheData.getCurrentUser().getUser().getUserId().toString(),
                userCacheData.getCurrentUserZkUserThirdOpenId().getValue(),
                userCacheData.getCurrentUserZkUserThirdOpenId().getAppId(),
                ip );
//        Tuple2<CashierRespDTO,Map<String,String>>  resp = yiBaoService.pay(paymentOrderEntity.getYibaoPayToken(), "4078484618301538304","oLWgZ4_dS7jgS594xPj2sdaNQPg8", "wxd4ecc66a93e1b0aa",ip );

        //crate local payment sequence entity
        paymentSequenceEntity.setTripartiteRequest(mapper.writeValueAsString(resp._2));
        paymentSequenceEntity.setTripartiteResponse(mapper.writeValueAsString(resp._1));
        paymentSequenceRepository.modify(paymentSequenceEntity);

        //pay_platform tripartite_id  tripartite_seq
        return resp._1.getResultData();
    }


    /**check payment order**/
    private PaymentOrderEntity checkPaymentOrder(Long paymentOrderId){
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.find(paymentOrderId);
        if(paymentOrderEntity == null || !PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())){
            log.error("支付单异常:{}",paymentOrderEntity);
            throw new BizException("支付单异常");
        }

        //ZoneOffset zoneOffset = ZoneOffset.of("+8");
       // Long dSecond = LocalDateTime.now().toEpochSecond(zoneOffset) - paymentOrderEntity.getCreateTime().toEpochSecond(zoneOffset);
       // if(BigDecimal.valueOf(dSecond).divide(BigDecimal.valueOf(60)).compareTo(BigDecimal.valueOf(YIBAO_ORDER_TIMEOUT)) > 0) throw new BizException("支付单已失效");
        if(paymentOrderEntity.getExpireTime().isBefore(LocalDateTime.now())) throw new BizException("支付单已失效");
        return paymentOrderEntity;
    }

    /**check payment order**/
    private PaymentOrderEntity checkPaymentOrderBySn(String sn){
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(sn);
        if(paymentOrderEntity == null || !PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())){
            log.error("支付单异常:{}",paymentOrderEntity);
            throw new BizException("支付单异常");
        }

        //ZoneOffset zoneOffset = ZoneOffset.of("+8");
        // Long dSecond = LocalDateTime.now().toEpochSecond(zoneOffset) - paymentOrderEntity.getCreateTime().toEpochSecond(zoneOffset);
        // if(BigDecimal.valueOf(dSecond).divide(BigDecimal.valueOf(60)).compareTo(BigDecimal.valueOf(YIBAO_ORDER_TIMEOUT)) > 0) throw new BizException("支付单已失效");
        if(paymentOrderEntity.getExpireTime().isBefore(LocalDateTime.now())) throw new BizException("支付单已失效");
        return paymentOrderEntity;
    }



    /***
     * 单笔订单查询接口
     * 该接口提供所有易宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑
     * @return String
     * **/
    public String payQuery(String paymentOrderSn) throws IOException {
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(paymentOrderSn);
        if(paymentOrderEntity == null) throw new BizException("支付单不存在");
        if(PaymentOrderStatusEnum.colse.equals(paymentOrderEntity.getStatus())) throw new BizException("支付单已关闭");
//        if(PaymentOrderStatusEnum.finish.equals(paymentOrderEntity.getStatus())){
//            PaymentSequenceEntity sequenceEntity = paymentSequenceRepository.find(paymentOrderEntity.getSuccessPayseqId());
//            if(sequenceEntity == null) throw new BizException("系统异常");
//            return mapper.readValue(sequenceEntity.getYibaoPayResp(), YiBaoPayResp.class);
//        }
        //到易宝查询支付结果
        return  null;//yiBaoService.payResult(paymentOrderEntity.getSn(),paymentOrderEntity.getYibaoUniqueOrderNo());
    }


    /**
     * 支付回调,一次完整的支付结束
     * **/
    public void payFinish(YiBaoPayRespDTO yiBaoPayResp) throws JsonProcessingException {

        PaymentSequenceEntity entity = paymentSequenceRepository.find(Long.valueOf(yiBaoPayResp.getOrderId()));
        if(entity == null) {
            log.error("支付流水不存在,id=" + yiBaoPayResp.getOrderId());
            return;
        }
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.find(entity.getOrderId());
        if(paymentOrderEntity == null) {
            log.error("支付订单不存在,id=" + entity.getOrderId());
            return;
        }
        if(!PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())) {
            log.error("支付单不是待支付状态,id={},status={}", entity.getOrderId(), paymentOrderEntity.getStatus());
            return ;
        }

        //update ac_payment_sequence
        paymentSequenceRepository.findByOrderId(paymentOrderEntity.getId()).forEach(s ->{
            s.setPayStatus(PayStatusEnum.failure);
            paymentSequenceRepository.modify(s);
        });

        //pay_platform tripartite_id  tripartite_seq
        entity.setPayStatus(PayStatusEnum.success);
        entity.setPayTime(LocalDateTime.now());
        entity.setYibaoPayResp(mapper.writeValueAsString(yiBaoPayResp));
        paymentSequenceRepository.modify(entity);
        // update ac_payment_order
        paymentOrderEntity.setSuccessPayseqId(entity.getId());
        paymentOrderEntity.setStatus(PaymentOrderStatusEnum.finish);
        paymentOrderRepository.modify(paymentOrderEntity);
        //insert ac_sub_account_bill
        genPayBill(paymentOrderEntity,true);
        genPayBill(paymentOrderEntity,false);

        //创建待分账记录
        //创建分支记录
        if(YesOrNoEnum.no.equals(paymentOrderEntity.getFreezeAfterPaid())
                && PayPlatformEnum.weixin.equals(entity.getPayPlatform())) {
            paymentOrderService.crateYibaoDivideEntity(paymentOrderEntity.getSn(),paymentOrderEntity.getAmount());
        }


        //成功通知
        PaySuccessNotifyDTO paySuccessNotifyDTO = new PaySuccessNotifyDTO();
        paySuccessNotifyDTO.setItems(orderItemRepository.findByOrderId(paymentOrderEntity.getId()).stream().map(s ->{
            CreateOPItemReqDTO c = new CreateOPItemReqDTO();
            BeanUtils.copyProperties(s,c);
            return c;
        }).collect(Collectors.toList()));
        paySuccessNotifyDTO.setPaySn(paymentOrderEntity.getSn());
        paySuccessNotifyDTO.setPayTime(LocalDateTime.now());
        paySuccessNotifyDTO.setPayUserId(entity.getCreatorId());
    }

    /**
     * 生成支付账单
     * payAmount 实际支付金额
     * **/
    private void genPayBill(final PaymentOrderEntity paymentOrderEntity,Boolean isBuyer){
        Long accountId = isBuyer ? paymentOrderEntity.getFromSubAccount() : paymentOrderEntity.getToSubAccount();
        SubAccountEntity subAccount = subAccountRepository.find(accountId);
        SubAccountBillEntity billEntity = new SubAccountBillEntity();
        billEntity.setAccountId(accountId);
        billEntity.setSubAccountId(subAccount.getAccountId());
        billEntity.setSubAccounType(subAccount.getType());
        billEntity = genBillType(paymentOrderEntity,billEntity,isBuyer);
        billEntity.setAmount(paymentOrderEntity.getAmount());//变动金额
        //符号 SignConstant
        if(isBuyer){
            billEntity.setStatus(SubAccountStatusEnum.has_pay);
            billEntity.setSign(SignConstant.NEGATIVE);
            //支付单是否冻结
//            if(YesOrNoEnum.yes.equals(paymentOrderEntity.getFreezeAfterPaid())){
//                SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(paymentOrderEntity.getFromSubAccount(), paymentOrderEntity.getAmount(), SignConstant.NEGATIVE,false);
//                billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
//                billEntity.setAfterAmount(resultDTO.getPreAmount());//变动后金额
//            }else{
//                SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(paymentOrderEntity.getFromSubAccount(), paymentOrderEntity.getAmount(), SignConstant.NEGATIVE,false);
//                billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
//                billEntity.setAfterAmount(resultDTO.getPreAmount());//变动后金额
//            }
            billEntity.setPreAmount(BigDecimal.ZERO);
            billEntity.setAfterAmount(BigDecimal.ZERO);
        }else{
            billEntity.setSign(SignConstant.POSITIVE);
            //支付单是否冻结
            if(YesOrNoEnum.yes.equals(paymentOrderEntity.getFreezeAfterPaid())){
                billEntity.setStatus(SubAccountStatusEnum.has_freezed);
                SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(accountId, paymentOrderEntity.getAmount(), SignConstant.POSITIVE,true);
                billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
                billEntity.setAfterAmount(resultDTO.getAfterAmount());//变动后金额
            }else{
                billEntity.setStatus(SubAccountStatusEnum.has_received);
                SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(accountId, paymentOrderEntity.getAmount(), SignConstant.POSITIVE,false);
                billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
                billEntity.setAfterAmount(resultDTO.getAfterAmount());//变动后金额
            }
        }
        billEntity.setOrderId(paymentOrderEntity.getId());//订单id
        billEntity.setOrderSource(paymentOrderEntity.getSource());//订单来源
        billEntity.setPaymentSeqId(paymentOrderEntity.getSuccessPayseqId());//支付流水id
        subAccountBillRepository.create(billEntity);
    }

    private SubAccountBillEntity genBillType(final PaymentOrderEntity paymentOrderEntity,SubAccountBillEntity billEntity ,Boolean isBuyer){
        if(isBuyer){
            if(PaymentOrderSourceEnum.zkrent_rent.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.order_expenditure);//订单支出
            if(PaymentOrderSourceEnum.zkrent_deposit.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.car_rental_deposit_expenditure);//租车押金支出
            if(PaymentOrderSourceEnum.zkrent_violation_deposit.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.peccancy_deposit_expenditure);//违章押金支出
        }else{
            if(PaymentOrderSourceEnum.zkrent_rent.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.order_income);//订单收入
            if(PaymentOrderSourceEnum.zkrent_deposit.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.car_rental_deposit);//租车押金收取
            if(PaymentOrderSourceEnum.zkrent_violation_deposit.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.peccancy_deposit);//违章押金
        }
        if(billEntity.getType() == null) throw new BizException("未知的billtype");
        return billEntity;
    }

    private SubAccountBillEntity genBillType2(final PaymentOrderEntity paymentOrderEntity,SubAccountBillEntity billEntity ,Boolean isBuyer){
        if(isBuyer){
            billEntity.setType(BillTypeEnum.income_refund);//商家退款
        }else{
            if(PaymentOrderSourceEnum.zkrent_rent.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.rent_refund);//退租金
            if(PaymentOrderSourceEnum.zkrent_deposit.equals(paymentOrderEntity.getSource()))billEntity.setType(BillTypeEnum.car_rental_refund);//租车押金退还
            if(PaymentOrderSourceEnum.zkrent_violation_deposit.equals(paymentOrderEntity.getSource())) billEntity.setType(BillTypeEnum.peccancy_refund);//违章退还
        }
        if(billEntity.getType() == null) throw new BizException("未知的billtype");
        return billEntity;
    }

    /**
     * 生成退款账单
     * payAmount 实际退款金额
     * **/
    private void genRefundyBill(final PaymentOrderEntity paymentOrderEntity,BigDecimal refundAmount,Boolean isBuyer){
        Long accountId = isBuyer ? paymentOrderEntity.getFromSubAccount() : paymentOrderEntity.getToSubAccount();
        SubAccountEntity subAccount = subAccountRepository.find(accountId);
        SubAccountBillEntity billEntity = new SubAccountBillEntity();
        billEntity.setAccountId(accountId);
        billEntity.setSubAccountId(subAccount.getAccountId());
        billEntity.setSubAccounType(subAccount.getType());
        billEntity = genBillType2(paymentOrderEntity,billEntity,isBuyer);
        billEntity.setAmount(refundAmount);//变动金额
        //符号 SignConstant
        if(isBuyer){
            billEntity.setSign(SignConstant.POSITIVE);
            billEntity.setStatus(SubAccountStatusEnum.has_received);
//            SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(paymentOrderEntity.getFromSubAccount(), refundAmount, SignConstant.POSITIVE,false);
//            billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
//            billEntity.setAfterAmount(resultDTO.getPreAmount());//变动后金额
            billEntity.setPreAmount(BigDecimal.ZERO);
            billEntity.setAfterAmount(BigDecimal.ZERO);
        }else{
            billEntity.setSign(SignConstant.NEGATIVE);
            billEntity.setStatus(SubAccountStatusEnum.has_refund);
            SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(accountId, refundAmount, SignConstant.NEGATIVE,true);
            billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
            billEntity.setAfterAmount(resultDTO.getAfterAmount());//变动后金额
        }
        billEntity.setOrderId(paymentOrderEntity.getId());//订单id
        billEntity.setOrderSource(paymentOrderEntity.getSource());//订单来源
        billEntity.setPaymentSeqId(paymentOrderEntity.getSuccessPayseqId());//支付流水id
        subAccountBillRepository.create(billEntity);
    }

    /***
     * 支付关闭
     * **/
    public void payClose(CloseReqDTO closeReqDTO) throws IOException {
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(closeReqDTO.getPaymentSn());
        if(paymentOrderEntity == null) throw new BizException("支付单异常");
        if(PaymentOrderStatusEnum.finish.equals(paymentOrderEntity.getStatus())) throw new BizException("已完成订单不能关闭");
        if(PaymentOrderStatusEnum.colse.equals(paymentOrderEntity.getStatus())) return;
        //yiBaoService.payClose(paymentOrderEntity.getSn(),paymentOrderEntity.getYibaoUniqueOrderNo());
        paymentOrderEntity.setStatus(PaymentOrderStatusEnum.colse);
        paymentOrderRepository.modify(paymentOrderEntity);
    }

    /**
     * 退款
     *
     * @param
     *
     * @return 退款单号
     **/
    @Transactional
    public CreateOrderPaymentRespDTO refund(RefundReqDTO refundReqDTO) throws IOException {
        //check payment order
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(refundReqDTO.getPaymentSn());
        if(paymentOrderEntity == null) {
            log.error("支付单不存在, sn=" + refundReqDTO.getPaymentSn());
            throw new BizException("支付单不存在");
        }
        if(PaymentOrderStatusEnum.colse.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付已关闭");
        if(PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付还未完成");

        PaymentSequenceEntity  paySequenceEntity = paymentSequenceRepository.find(paymentOrderEntity.getSuccessPayseqId());

        //TODO check yibao balance
        //crate yibao refund order
        String sn = SnGenerator.generatorPaymentOrder();
        Tuple2<YiBaoRefundRespDTO,Map<String,String>> tuple2 = null;
        if(PayPlatformEnum.moni.equals(paySequenceEntity.getPayPlatform())) {
            tuple2 = testrefund(paySequenceEntity.getId().toString(),sn,paySequenceEntity.getTripartiteId(),refundReqDTO.getAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        } else {
            tuple2= yiBaoService.refund(paySequenceEntity.getId().toString(),sn,paySequenceEntity.getTripartiteId(),refundReqDTO.getAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        }

        //create local refund order
        PaymentOrderEntity refundEntity = createLocalRundOrder(sn,paymentOrderEntity.getToSubAccount(),paymentOrderEntity.getToSubAccount(),refundReqDTO.getAmount(),refundReqDTO.getPaymentSn(),refundReqDTO.getSource());
        //create local sequence
        PaymentSequenceEntity refundSequence = crateLocalRefundSequence(refundEntity.getId(),refundEntity.getAmount(), refundEntity.getAmount(),mapper.writeValueAsString(tuple2._2),mapper.writeValueAsString(tuple2._1), tuple2._1.getUniqueRefundNo());
        refundEntity.setSuccessPayseqId(refundSequence.getId());
        paymentOrderRepository.modify(refundEntity);

        if(PayPlatformEnum.moni.equals(paySequenceEntity.getPayPlatform())) {
            //注册退款成功回调延迟任务(延迟2秒)
           /* delayTaskRegister.register(DelayTaskData.bizName(MoniRefundSuccessTask.MONI_REFUND_SUCCESS)
                    .bizParameters(refundSequence.getId().toString()).executeTime(DateUtil.getDateAddSecond(new Date(), 5)));*/
        }

        //return
        CreateOrderPaymentRespDTO createOrderPaymentRespDTO = new CreateOrderPaymentRespDTO();
        createOrderPaymentRespDTO.setPaymentOrderSn(refundEntity.getSn());
        createOrderPaymentRespDTO.setPaymentOrderId(refundEntity.getId());
        return createOrderPaymentRespDTO;
    }



    /**
     * 创建退款单
     * **/
    private PaymentOrderEntity createLocalRundOrder(String sn,Long fromAccount,Long toAccount,BigDecimal amount, String paymentSn,PaymentOrderSourceEnum source){
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setType(PaymentOrderTypeEnum.trade_refund);
        entity.setSn(sn);
        entity.setFromSubAccount(fromAccount);
        entity.setToSubAccount(toAccount);
        entity.setSource(source);
        entity.setAmount(amount);
        entity.setStatus(PaymentOrderStatusEnum.submit);
        entity.setRefundRelaPaySn(paymentSn);
        return paymentOrderRepository.create(entity);
    }

    /***
     * 创建退款流水
     * @param paymentOrderId 退款单号
     * @param payableAmount 应退金额
     * @param actualPayAmount 实退金额
     * **/
    private PaymentSequenceEntity crateLocalRefundSequence(Long paymentOrderId,BigDecimal payableAmount,BigDecimal actualPayAmount,String req,String resp, String uniqueOrderNo){
        //crate  refund sequence
        PaymentSequenceEntity sequenceEntity = new PaymentSequenceEntity();
        sequenceEntity.setOrderId(paymentOrderId);
        sequenceEntity.setPayableAmount(payableAmount);
        sequenceEntity.setActualPayAmount(actualPayAmount);
        sequenceEntity.setPayStatus(PayStatusEnum.wait);
        sequenceEntity.setPayTime(LocalDateTime.now());
        sequenceEntity.setPayType(PayTypeEnum.wxpay_lite);
        sequenceEntity.setTripartiteRequest(req);
        sequenceEntity.setTripartiteResponse(resp);
        sequenceEntity.setTripartiteId(uniqueOrderNo);
        return paymentSequenceRepository.create(sequenceEntity);
    }

    /**
     * 退款查询接口
     * 提交退款申请后，通过调用该接口查询退款状态。
     **/
    public YiBaoRefundResp2DTO refundQuery(String refundSn) throws IOException {
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(refundSn);
        if(paymentOrderEntity == null) throw new BizException("退款单异常");
        return null;//yiBaoService.refundQuery(paymentOrderEntity.getRefundRelaPaySn(),paymentOrderEntity.getSn(),paymentOrderEntity.getYibaoUniqueOrderNo());
    }

    /**
     * 退款回调通知
     *
     * @param yiBaoRefundResp3DTO
     **/
    public void refundFinish(YiBaoRefundResp3DTO yiBaoRefundResp3DTO) throws JsonProcessingException {
        PaymentSequenceEntity refundSequence = paymentSequenceRepository.find(Long.valueOf(yiBaoRefundResp3DTO.getRefundRequestId()));
        if(refundSequence == null) {
            log.error("refundSequence不存在,id=" + yiBaoRefundResp3DTO.getRefundRequestId());
            return;
        }
        PaymentOrderEntity refundOrderEntity = paymentOrderRepository.find(refundSequence.getOrderId());
        if(refundOrderEntity == null) {
            log.error("退款单不存在,id=" + refundSequence.getOrderId());
            return;
        }
        if(!PaymentOrderStatusEnum.submit.equals(refundOrderEntity.getStatus())) {
            log.error("退款单状态错误,id=" + refundSequence.getOrderId());
            return ;
        }

        refundSequence.setPayStatus(PayStatusEnum.success);
        refundSequence.setYibaoPayResp(mapper.writeValueAsString(yiBaoRefundResp3DTO));
        paymentSequenceRepository.modify(refundSequence);
        //update payment order entity
        refundOrderEntity.setStatus(PaymentOrderStatusEnum.finish);
        refundOrderEntity.setSuccessPayseqId(refundSequence.getId());
        paymentOrderRepository.modify(refundOrderEntity);

        //原始payment order
        PaymentOrderEntity oldPaymentOrderEntity = paymentOrderRepository.findOneBySn(refundOrderEntity.getRefundRelaPaySn());
        if(oldPaymentOrderEntity == null) {
            log.error("原始支付单状态不存在,sn=" + refundOrderEntity.getRefundRelaPaySn());
            return;
        }

        //gen bill
        //退违章押金
        if(PaymentOrderSourceEnum.zkrent_violation_deposit.equals(oldPaymentOrderEntity.getSource())
                && refundOrderEntity.getAmount().compareTo(oldPaymentOrderEntity.getAmount()) < 0){ //退款金额小于原始金额
            genRefundyBill(refundOrderEntity, refundOrderEntity.getAmount(),true);//buery
            genRefundyBill(oldPaymentOrderEntity,oldPaymentOrderEntity.getAmount(),false);//seller
            //change account
            BigDecimal amount = oldPaymentOrderEntity.getAmount().subtract(refundOrderEntity.getAmount());//违章代收金额 = 原始金额 - 变动金额
            SubAccountAmountChangeResultDTO resultDTO = subAccountService.changeAmount(refundOrderEntity.getFromSubAccount(), amount, SignConstant.POSITIVE,false);
            //生成一条违章代扣账单
            SubAccountEntity subAccount = subAccountRepository.find(oldPaymentOrderEntity.getFromSubAccount());
            //build entity
            SubAccountBillEntity billEntity = new SubAccountBillEntity();
            billEntity.setAccountId(oldPaymentOrderEntity.getFromSubAccount());
            billEntity.setSubAccountId(subAccount.getAccountId());
            billEntity.setSubAccounType(subAccount.getType());
            billEntity.setType(BillTypeEnum.violation_of_commission);
            billEntity.setAmount(amount);//变动金额
            billEntity.setSign(SignConstant.POSITIVE);
            billEntity.setStatus(SubAccountStatusEnum.has_received);
            billEntity.setPreAmount(resultDTO.getPreAmount());//账户原金额
            billEntity.setAfterAmount(resultDTO.getPreAmount());//变动后金额

            billEntity.setOrderId(refundOrderEntity.getId());//订单id
            billEntity.setOrderSource(refundOrderEntity.getSource());//订单来源
            billEntity.setPaymentSeqId(refundOrderEntity.getSuccessPayseqId());//支付流水id
            subAccountBillRepository.create(billEntity);
            return ;
        }
        genRefundyBill(refundOrderEntity,refundOrderEntity.getAmount(),true);
        genRefundyBill(refundOrderEntity,refundOrderEntity.getAmount(),false);

        //退款通知
        RefundSuccessNotifyDTO refundSuccessNotifyDTO = new RefundSuccessNotifyDTO();
        refundSuccessNotifyDTO.setPaySn(refundOrderEntity.getSn());
        refundSuccessNotifyDTO.setPayTime(LocalDateTime.now());
        refundSuccessNotifyDTO.setItems(orderItemRepository.findByOrderId(refundOrderEntity.getId()).stream().map(s ->{
            CreateOPItemReqDTO c = new CreateOPItemReqDTO();
            BeanUtils.copyProperties(s,c);
            return c;
        }).collect(Collectors.toList()));
        //rentOrderSuccessNofityFeignClient.refundSuccess(refundSuccessNotifyDTO);
    }

    /**
     * 分账
     *
     * @param
     **/
    public TradedivideRespDTO tradedivide(String userId, String paymentSn, BigDecimal amount) throws IOException {
        //check payment order
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(paymentSn);
        if(paymentOrderEntity == null) throw new BizException("支付单异常");
        if(PaymentOrderStatusEnum.colse.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付已关闭");
        if(PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付还未完成");
        //check account
        Tuple2<AccountEntity, SubAccountEntity> account = paymentOrderService.checkAccount(userId,SubAccountTypeEnum.balance,true);
        //check divide
        String reqId = UUID.randomUUID().toString().replaceAll("-","");

        TradedivideDetailsDTO tradedivideDetailsDTO = new TradedivideDetailsDTO();
        tradedivideDetailsDTO.setAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
        tradedivideDetailsDTO.setLedgerNo(account._1.getYibaoMerchantNo());

        PaymentSequenceEntity sequenceEntity = paymentSequenceRepository.find(paymentOrderEntity.getSuccessPayseqId());

        TradedivideRespDTO tradedivideRespDTO = yiBaoService.tradedivide(paymentOrderEntity.getSn(),sequenceEntity.getTripartiteId(),reqId,mapper.writeValueAsString(Arrays.asList(tradedivideDetailsDTO)));

        //create local divide
        PaymentYibaoDivideEntity yibaoDivideEntity = new PaymentYibaoDivideEntity();
        yibaoDivideEntity.setPaymentOrderSn(paymentOrderEntity.getSn());
        yibaoDivideEntity.setMerchantName(paymentOrderEntity.getSn());
        yibaoDivideEntity.setAmount(amount);
        yibaoDivideEntity.setStatus(PaymentYibaoDivideEntity.STATUS_ACCEPT);
        yibaoDivideEntity.setReqId(reqId);
        paymentYibaoDivideRepository.create(yibaoDivideEntity);

        return tradedivideRespDTO;
    }

    /**
     * 分账查询
     * **/
    public TradedivideRespDTO tradedivideQuery(String divideRequestId) throws IOException {
        //check payment order
        PaymentYibaoDivideEntity paymentYibaoDivideEntity = paymentYibaoDivideRepository.findOneByReqId(divideRequestId);
        if(paymentYibaoDivideEntity == null) throw new BizException("分账单不存在");
        PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findOneBySn(paymentYibaoDivideEntity.getPaymentOrderSn());
        if(paymentOrderEntity == null) throw new BizException("支付单异常");
        if(PaymentOrderStatusEnum.colse.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付已关闭");
        if(PaymentOrderStatusEnum.submit.equals(paymentOrderEntity.getStatus())) throw new BizException("该笔支付还未完成");
        return null;//yiBaoService.tradedivideQuery(paymentYibaoDivideEntity.getPaymentOrderSn(),paymentOrderEntity.getYibaoUniqueOrderNo(),paymentYibaoDivideEntity.getReqId());
    }

    /**
     * 分账结束
     * **/
    public void tradedivideFinish(TradedivideResp2DTO tradedivideResp2DTO) throws JsonProcessingException {
        PaymentYibaoDivideEntity paymentYibaoDivideEntity = paymentYibaoDivideRepository.findOneByReqId(tradedivideResp2DTO.getDivideRequestId());
        if(paymentYibaoDivideEntity == null) return;
        if(!StringUtils.isEmpty(paymentYibaoDivideEntity.getResp())) return;
        paymentYibaoDivideEntity.setResp(mapper.writeValueAsString(tradedivideResp2DTO));
        paymentYibaoDivideEntity.setStatus(tradedivideResp2DTO.getStatus());
        paymentYibaoDivideRepository.modify(paymentYibaoDivideEntity);
    }











    /**
     * 余额查询
     * *
     *
     * @param userId
     */
    public YiBaoBalanceRespDTO queryBalance(String userId) throws IOException {
        Tuple2<AccountEntity, SubAccountEntity> account = paymentOrderService.checkAccount(userId,SubAccountTypeEnum.balance,true);
        return yiBaoService.queryBalance(account._1.getYibaoMerchantNo());
    }


    public Tuple2<YiBaoRefundRespDTO,Map<String,String>> testrefund(String orderId, String refundSn, String uniqueOrderNo, String refundAmount) {
        YiBaoRefundRespDTO yiBaoRefundRespDTO = new YiBaoRefundResp2DTO();
        yiBaoRefundRespDTO.setUniqueRefundNo(UUID.randomUUID().toString().replace("-", ""));
        return new Tuple2<>(yiBaoRefundRespDTO, new HashMap<String, String>());
    }
}
