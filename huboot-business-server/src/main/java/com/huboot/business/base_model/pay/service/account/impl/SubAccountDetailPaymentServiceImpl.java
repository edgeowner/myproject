package com.huboot.business.base_model.pay.service.account.impl;

import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailPaymentDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentDomain;
import com.huboot.business.base_model.pay.dto.account.*;
import com.huboot.business.base_model.pay.enums.*;
import com.huboot.business.base_model.pay.service.account.*;
import com.huboot.business.common.utils.BigDecimalUtil;
import com.huboot.business.common.utils.DateUtil;
import com.huboot.business.mybatis.*;
import com.pingplusplus.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 账户中心-子账户明细支付基础信息ServiceImpl
 */
@Service("subAccountDetailPaymentServiceImpl")
public class SubAccountDetailPaymentServiceImpl extends AbstractBaseService<SubAccountDetailPaymentDomain, Long> implements ISubAccountDetailPaymentService {

    @Autowired
    public SubAccountDetailPaymentServiceImpl(ISubAccountDetailPaymentDao subAccountDetailPaymentDao) {
        super(subAccountDetailPaymentDao);
    }

    @Autowired
    private ISubAccountDetailService subAccountDetailService;
    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private ISubAccountDetailPaymentBaseService subAccountDetailPaymentBaseService;
    @Autowired
    private ISubAccountDetailPaymentPingppService subAccountDetailPaymentPingppService;

    @Autowired
    private ISubAccountDetailPaymentDao subAccountDetailPaymentDao;

    @Override
    public List<SubAccountDetailPaymentDomain> findBySubAccountDetailIdAndStatus(Long subAccountDetailId, Integer status, Integer type) {
        SubAccountDetailPaymentDomain pay = new SubAccountDetailPaymentDomain();
        pay.setSubAccountDetailId(subAccountDetailId);
        pay.setStatus(status);
        pay.setType(type);
        return this.findByBeanProp(pay);
    }

    @Override
    public SubAccountDetailPaymentDomain findFirstBySubAccountDetailId(Long subAccountDetailId, Integer status) {
        SubAccountDetailPaymentDomain pay = new SubAccountDetailPaymentDomain();
        pay.setSubAccountDetailId(subAccountDetailId);
        pay.setStatus(status);
        return this.getFirstByBeanProp(pay);
    }

    @Override
    public List<SubAccountDetailPaymentDomain> findBySubAccountDetailSn(String subAccountDetailSn) {
        SubAccountDetailDTO subAccountDetailDTO = subAccountDetailService.findByDetailSn(subAccountDetailSn);
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
        subAccountDetailPaymentDomain.setSubAccountDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
        return this.findByBeanProp(subAccountDetailPaymentDomain);
    }

    @Override
    public SubAccountDetailPaymentDomain findBySubAccountDetailSnAndStatus(String subAccountDetailSn, SubAccountDetailPaymentStatusEnum status) {
        List<SubAccountDetailPaymentDomain> list = findBySubAccountDetailSn(subAccountDetailSn);
        Optional<SubAccountDetailPaymentDomain> optional = list.stream().filter(subAccountDetailPaymentDomain -> {
            return subAccountDetailPaymentDomain.getStatus().intValue() == status.getValue().intValue();
        }).findFirst();
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }


    @Override
    public List<SubAccountDetailPaymentDomain> findForOrderBySubAccountDetailSn(String subAccountDetailSn) {
        SubAccountDetailDTO subAccountDetailDTO = subAccountDetailService.findByDetailSn(subAccountDetailSn);
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
        subAccountDetailPaymentDomain.setSubAccountDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
        subAccountDetailPaymentDomain.setStatus(SubAccountDetailPaymentStatusEnum.Paid.getValue());
        return this.findByBeanProp(subAccountDetailPaymentDomain);
    }

    @Override
    public void closeBySubAccountDetailId(Long subAccountDetailId) {
        SubAccountDetailPaymentDomain sour = new SubAccountDetailPaymentDomain();
        sour.setSubAccountDetailId(subAccountDetailId);
        SubAccountDetailPaymentDomain tage = new SubAccountDetailPaymentDomain();
        tage.setStatus(SubAccountDetailPaymentStatusEnum.PayClose.getValue());
        this.mergeByBeanProp(tage, sour);
    }

    @Override
    @Transactional
    public SubAccountDetailPaymentDomain prePayForDetail(SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO) throws ApiException {
        //微信发起人与支付人（openid不同支付会失败-所以每次都会新增一条支付记录）
        //支付基础信息
        SubAccountDetailPaymentBaseDomain subAccountDetailPaymentBaseDomain = subAccountDetailPaymentBaseService.findByPayType(subAccountDetailPrePaymentDTO.getPaymentType());
        //账单账户信息
        SubAccountDetailDTO subAccountDetailDTO = subAccountDetailService.findByDetailId(subAccountDetailPrePaymentDTO.getDetailId());

        //检查是否支付成功
        if (subAccountDetailDTO.getSubAccountDetailDomain().getStatus().equals(SubAccountDetailStatusEnum.Finished.getValue())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "已支付，但状态异常，请联系客服");
        }
        //需要支付的金额
        BigDecimal needPayAmount = subAccountDetailDTO.getSubAccountDetailDomain().getAmountPaid();
        BigDecimal redPacketPayAmount = BigDecimal.ZERO;
        //校验红包信息
        //是否已经扣过红包
        List<SubAccountDetailPaymentDomain> subAccountDetailPaymentDomains = this.findBySubAccountDetailIdAndStatus(subAccountDetailDTO.getSubAccountDetailDomain().getId(), SubAccountDetailPaymentStatusEnum.Paid.getValue(), SubAccountDetailPaymentTypeEnum.RedPacket.getValue());
        if (CollectionUtils.isEmpty(subAccountDetailPaymentDomains)) {
            if (subAccountDetailPrePaymentDTO.getRedPacketAmount() != null && BigDecimalUtil.gt(subAccountDetailPrePaymentDTO.getRedPacketAmount(), BigDecimal.ZERO)) {
                SubAccountDTO subAccountDTO = subAccountService.findRedPacketByAccountId(subAccountDetailDTO.getSubAccountDetailDomain().getAccountId());
                BigDecimal maxRedPacketAmount = subAccountDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailPaymentExtendDomain().getMaxRedPacketAmount();
                //计算可以用红包的金额
                BigDecimal usableBalance = maxRedPacketAmount;
                if (BigDecimalUtil.lt(subAccountDTO.getSubAccountBaseDomain().getBalance(), maxRedPacketAmount)) {
                    usableBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                }
                if (BigDecimalUtil.neq(subAccountDetailPrePaymentDTO.getRedPacketAmount(), usableBalance)) {
                    throw new ApiException("可用红包金额已变化，请刷新页面");
                }
                if (!subAccountDTO.getSubAccountBaseDomain().getStatus().equals(AccountStatusEnum.Valid.getValue())) {
                    throw new ApiException("可用红包金额已变化，请刷新页面");
                }
                if (BigDecimalUtil.gt(usableBalance, BigDecimal.ZERO)) {
                    //红包支付
                    //支付基础信息
                    SubAccountDetailPaymentBaseDomain redPacketPaymentBaseDomain = subAccountDetailPaymentBaseService.findByPayType(SubAccountDetailPaymentTypeEnum.RedPacket.getValue());
                    String sn = "sn";
                    SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
                    subAccountDetailPaymentDomain.setSubAccountId(subAccountDetailDTO.getSubAccountDetailDomain().getSubAccountId());
                    subAccountDetailPaymentDomain.setSubAccountDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
                    subAccountDetailPaymentDomain.setType(redPacketPaymentBaseDomain.getType());
                    subAccountDetailPaymentDomain.setPlatform(redPacketPaymentBaseDomain.getPlatform());
                    subAccountDetailPaymentDomain.setMethod(redPacketPaymentBaseDomain.getMethod());
                    subAccountDetailPaymentDomain.setSn(sn);
                    subAccountDetailPaymentDomain.setAmount(usableBalance);
                    subAccountDetailPaymentDomain.setStatus(SubAccountDetailPaymentStatusEnum.Unpaid.getValue());
                    this.create(subAccountDetailPaymentDomain);
                    this.paidForDetail(new Date(), subAccountDetailPaymentDomain.getId(), subAccountDetailPaymentDomain.getSn(), redPacketPaymentBaseDomain.getType());
                    redPacketPayAmount = usableBalance;
                }
            }
        } else {
            redPacketPayAmount = subAccountDetailPaymentDomains.get(0).getAmount();
        }
        subAccountDetailPrePaymentDTO.setRedPacketAmount(redPacketPayAmount);
        //计算第三方账户的支付的金额
        needPayAmount = needPayAmount.subtract(redPacketPayAmount);
        if (BigDecimalUtil.lte(needPayAmount, BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.JsonError, "实际支付金额必须大于0");
        }
        //第三方支付
        String sn = "sn";
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
        subAccountDetailPaymentDomain.setSubAccountId(subAccountDetailDTO.getSubAccountDetailDomain().getSubAccountId());
        subAccountDetailPaymentDomain.setSubAccountDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
        subAccountDetailPaymentDomain.setType(subAccountDetailPaymentBaseDomain.getType());
        subAccountDetailPaymentDomain.setPlatform(subAccountDetailPaymentBaseDomain.getPlatform());
        subAccountDetailPaymentDomain.setMethod(subAccountDetailPaymentBaseDomain.getMethod());
        subAccountDetailPaymentDomain.setSn(sn);
        subAccountDetailPaymentDomain.setAmount(needPayAmount);
        subAccountDetailPaymentDomain.setStatus(SubAccountDetailPaymentStatusEnum.Unpaid.getValue());
        if(subAccountDetailPrePaymentDTO.getIsSharePay() != null){
            subAccountDetailPaymentDomain.setIsSharePay(subAccountDetailPrePaymentDTO.getIsSharePay());
        }
        this.create(subAccountDetailPaymentDomain);
        return subAccountDetailPaymentDomain;
    }

    public SubAccountDetailPaymentDomain offlinePrePayForDetail(SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO,SubAccountDetailForPayAnotherDTO dto) throws ApiException {
        //微信发起人与支付人（openid不同支付会失败-所以每次都会新增一条支付记录）
        //支付基础信息
        SubAccountDetailPaymentBaseDomain subAccountDetailPaymentBaseDomain = subAccountDetailPaymentBaseService.findByPayType(subAccountDetailPrePaymentDTO.getPaymentType());
        //账单账户信息
        SubAccountDetailDTO subAccountDetailDTO = subAccountDetailService.findByDetailId(subAccountDetailPrePaymentDTO.getDetailId());

        //检查是否支付成功
        if (subAccountDetailDTO.getSubAccountDetailDomain().getStatus().equals(SubAccountDetailStatusEnum.Finished.getValue())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "已支付，但状态异常，请联系客服");
        }
        //需要支付的金额
        BigDecimal needPayAmount = subAccountDetailDTO.getSubAccountDetailDomain().getAmountPaid();
        //计算第三方账户的支付的金额
        if (BigDecimalUtil.lte(needPayAmount, BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.JsonError, "实际支付金额必须大于0");
        }
        //第三方支付
        String sn = "sn";
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
        subAccountDetailPaymentDomain.setSubAccountId(subAccountDetailDTO.getSubAccountDetailDomain().getSubAccountId());
        subAccountDetailPaymentDomain.setSubAccountDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
        subAccountDetailPaymentDomain.setSn(sn);
        subAccountDetailPaymentDomain.setType(subAccountDetailPaymentBaseDomain.getType());
        subAccountDetailPaymentDomain.setPlatform(subAccountDetailPaymentBaseDomain.getPlatform());
        subAccountDetailPaymentDomain.setMethod(subAccountDetailPaymentBaseDomain.getMethod());
        subAccountDetailPaymentDomain.setTransactionNo(dto.getBankFlow());
        subAccountDetailPaymentDomain.setPaymentDate(new Date());
        subAccountDetailPaymentDomain.setAmount(needPayAmount);
        subAccountDetailPaymentDomain.setRemark(dto.getRemark());
        subAccountDetailPaymentDomain.setStatus(SubAccountDetailPaymentStatusEnum.Paid.getValue());
        this.create(subAccountDetailPaymentDomain);
        return subAccountDetailPaymentDomain;
    }

    /**
     * 根据ID，查询支付成功，true:支付成功，false无支付成功信息
     *
     * @param sn
     * @return
     */
    /*private Boolean checkDetailPaidById(Long id) {
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
        subAccountDetailPaymentDomain.setSubAccountDetailId(id);
        subAccountDetailPaymentDomain.setStatus(SubAccountDetailPaymentStatusEnum.Paid.getValue());
        List<SubAccountDetailPaymentDomain> subAccountDetailPaymentDomains = this.findByBeanProp(subAccountDetailPaymentDomain);
        if (CollectionUtils.isEmpty(subAccountDetailPaymentDomains)) {
            return false;
        }
        return true;
    }*/
    @Override
    public SubAccountDetailPaymentDomain findByPaymentSn(String sn) throws ApiException {
        AssertUtil.notNull(sn, "订单号不能为空");
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
        subAccountDetailPaymentDomain.setSn(sn);
        return this.getFirstByBeanProp(subAccountDetailPaymentDomain);
    }

    //无需事务，能保证一项支付成功，方便追查原因
    @Override
    public void paidForDetail(Date paidDate, Long paymentId, String transactionNo, Integer payType) {
        AssertUtil.notNull(paidDate);
        AssertUtil.notNull(paymentId);
        AssertUtil.notNull(transactionNo);
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = this.find(paymentId);
        /*if (SubAccountDetailPaymentStatusEnum.Paid.getValue().equals(subAccountDetailPaymentDomain.getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "该支付单已经完成支付");
        }*/
        checkPaymentStatusOver(subAccountDetailPaymentDomain);
        SubAccountDetailPaymentDomain newDomain = new SubAccountDetailPaymentDomain();
        newDomain.setId(subAccountDetailPaymentDomain.getId());
        newDomain.setStatus(SubAccountDetailPaymentStatusEnum.Paid.getValue());
        newDomain.setPaymentDate(paidDate);
        newDomain.setTransactionNo(transactionNo);
        this.merge(newDomain);
        //更新子账户账单表detail
        SubAccountDetailForPaidDTO subAccountDetailPaidDTO = new SubAccountDetailForPaidDTO();
        subAccountDetailPaidDTO.setPaymentDate(newDomain.getPaymentDate());
        subAccountDetailPaidDTO.setDetailId(subAccountDetailPaymentDomain.getSubAccountDetailId());
        try {
            User user = SecurityContextHelper.getCurrentUser();
            subAccountDetailPaidDTO.setOperator(user.getRealName());
        } catch (Exception e) {
            subAccountDetailPaidDTO.setOperator("系统");
        }
        subAccountDetailPaidDTO.setPaymentId(newDomain.getId());
        subAccountDetailPaidDTO.setPaymentType(payType);
        subAccountDetailPaidDTO.setSn(subAccountDetailPaymentDomain.getSn());
        subAccountDetailPaidDTO.setPaidAmount(subAccountDetailPaymentDomain.getAmount());
        subAccountDetailPaidDTO.setIsSharePay(subAccountDetailPaymentDomain.getIsSharePay());
        subAccountDetailService.updateForPaid(subAccountDetailPaidDTO);
    }

    @Override
    public void payFailedForDetail(Long paymentId, String remark) {
        AssertUtil.notNull(paymentId);
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = this.find(paymentId);
        if (SubAccountDetailPaymentStatusEnum.Paid.getValue().equals(subAccountDetailPaymentDomain.getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "该支付单已经完成支付");
        }
        SubAccountDetailPaymentDomain newDomain = new SubAccountDetailPaymentDomain();
        newDomain.setId(subAccountDetailPaymentDomain.getId());
        newDomain.setStatus(SubAccountDetailPaymentStatusEnum.PayFail.getValue());
        newDomain.setRemark(remark);
        this.merge(newDomain);
        //更新子账户账单表detail
        SubAccountDetailForPayFailDTO subAccountDetailForPayFailDTO = new SubAccountDetailForPayFailDTO();
        subAccountDetailForPayFailDTO.setDetailId(subAccountDetailPaymentDomain.getSubAccountDetailId());
        subAccountDetailForPayFailDTO.setRemark(remark);
        subAccountDetailService.updateForPayFail(subAccountDetailForPayFailDTO);

    }

    @Override
    public void payingForDetail(Long paymentId, String transactionNo) {
        AssertUtil.notNull(paymentId);
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = this.find(paymentId);
        /*if (SubAccountDetailPaymentStatusEnum.Paid.getValue().equals(subAccountDetailPaymentDomain.getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "该支付单已经完成支付");
        }*/
        checkPaymentStatusOver(subAccountDetailPaymentDomain);
        SubAccountDetailPaymentDomain newDomain = new SubAccountDetailPaymentDomain();
        newDomain.setId(subAccountDetailPaymentDomain.getId());
        newDomain.setStatus(SubAccountDetailPaymentStatusEnum.Paying.getValue());
        newDomain.setTransactionNo(transactionNo);
        this.merge(newDomain);
        //更新子账户账单表detail
        subAccountDetailService.updateForPaying(subAccountDetailPaymentDomain.getSubAccountDetailId());
    }

    @Override
    public void transfer(SubAccountDetailPaymentTransferDTO dto) {
        Boolean flag = false;
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = this.find(dto.getPaymentId());
        SubAccountDetailDomain subAccountDetailDomain = subAccountDetailService.find(subAccountDetailPaymentDomain.getSubAccountDetailId());
        //查询申请提现记录+提现扩展信息
        SubAccountDetailDTO applySubAccountDetailDTO = subAccountDetailService.findByDetailSn(subAccountDetailDomain.getTradeSn());
        //组装ping++打款--目前只支持
        PaymentPingppForDetailDTO paymentPingppForDetailDTO = new PaymentPingppForDetailDTO();
        paymentPingppForDetailDTO.setAmount(subAccountDetailPaymentDomain.getAmount());
        paymentPingppForDetailDTO.setPaymentId(subAccountDetailPaymentDomain.getId());
        paymentPingppForDetailDTO.setPaymentSn(subAccountDetailPaymentDomain.getSn());
        paymentPingppForDetailDTO.setTypeEnum(CustomerPaymentTypeEnum.valueOf(subAccountDetailPaymentDomain.getType()));
        paymentPingppForDetailDTO.setUserName(applySubAccountDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailCashExtendDomain().getUserName());
        paymentPingppForDetailDTO.setBankCode(applySubAccountDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailCashExtendDomain().getOpenBankPingppCode());
        paymentPingppForDetailDTO.setCardNumber(applySubAccountDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailCashExtendDomain().getCardNumber());
        paymentPingppForDetailDTO.setAccountType(SubAccountDetailPaymentExtendCashAccountTypeEnum.valueOf(applySubAccountDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailCashExtendDomain().getAccountType()));
        //请求pingpp
        Transfer transfer = subAccountDetailPaymentPingppService.transfer(paymentPingppForDetailDTO);
        checkTransferResult(subAccountDetailPaymentDomain, transfer);
    }

    @Override
    public void getTransferByDetailId(Long detailId) {
        SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = this.findFirstBySubAccountDetailId(detailId, SubAccountDetailPaymentStatusEnum.Unpaid.getValue());
        if (subAccountDetailPaymentDomain == null) {
            return;
        }
        //请求pingpp
        Transfer transfer = subAccountDetailPaymentPingppService.getTransferByPaymentId(subAccountDetailPaymentDomain.getId());
        checkTransferResult(subAccountDetailPaymentDomain, transfer);
    }

    private void checkTransferResult(SubAccountDetailPaymentDomain subAccountDetailPaymentDomain, Transfer transfer) {
        try {
            //支付状态   https://www.pingxx.com/api#transfers-企业付款
            if (transfer.getStatus().equals("paid")) {//付款成功
                String paidDateStr = DateUtil.formatSecond(transfer.getTimeTransferred());
                Date paidDate = DateUtil.parse(paidDateStr, "yyyy-MM-dd HH:mm:ss");
//                this.payingForDetail(subAccountDetailPaymentDomain.getId(), transfer.getTransaction_no());
                this.paidForDetail(paidDate, subAccountDetailPaymentDomain.getId(), transfer.getTransaction_no(), subAccountDetailPaymentDomain.getType());
            } else if (transfer.getStatus().equals("failed")) {//付款失败
                this.payFailedForDetail(subAccountDetailPaymentDomain.getId(), transfer.getFailureMsg());
            }/* else if (transfer.getStatus().equals("pending")) {//处理中
                this.payingForDetail(subAccountDetailPaymentDomain.getId(), transfer.getTransaction_no());
            }*/ else if (transfer.getStatus().equals("pending") //处理中
                    || transfer.getStatus().equals("scheduled")) {//待发送，unionpay渠道的转账会在请求成功后延时5分钟发送，5分钟内处于待发送状态
                //还没发送-处理，后续有定时任务处理
                /*TimeoutRegisterDTO timeoutRegisterDTO = new TimeoutRegisterDTO();
                timeoutRegisterDTO.setBizId(subAccountDetailPaymentDomain.getSubAccountDetailId());
                timeoutRegisterDTO.setActionTime(DateUtil.getDateAddMin(new Date(), AccountConstant.PAY_ANOTHER_DETAIL_MINUTE));
                timeoutRegisterDTO.setTocTimeoutBizTypeEnum(TocTimeoutBizTypeEnum.GetTransferStatusPingpp);
                timeoutService.registerTimeoutTask(timeoutRegisterDTO);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("回写账单失败，{}", e.getMessage());
            throw new ApiException(ErrorCodeEnum.ParamsError, "回写账单失败：" + e.getMessage());
        }
    }

    private void checkPaymentStatusOver(SubAccountDetailPaymentDomain subAccountDetailPaymentDomain) {
        if (SubAccountDetailPaymentStatusEnum.Paid.getValue().equals(subAccountDetailPaymentDomain.getStatus())
                || SubAccountDetailPaymentStatusEnum.PayFail.getValue().equals(subAccountDetailPaymentDomain.getStatus())
                || SubAccountDetailPaymentStatusEnum.PayClose.getValue().equals(subAccountDetailPaymentDomain.getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "该支付单状态已经结束");
        }
    }




}
