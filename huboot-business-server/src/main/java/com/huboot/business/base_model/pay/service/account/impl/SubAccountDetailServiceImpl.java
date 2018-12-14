package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailDao;
import com.huboot.business.base_model.pay.domain.account.*;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailCashExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPayItemsExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPaymentExtendDomain;
import com.huboot.business.base_model.pay.dto.account.*;
import com.huboot.business.base_model.pay.enums.*;
import com.huboot.business.base_model.pay.service.account.*;
import com.huboot.business.common.jpa.PrimaryKeyGenerator;
import com.huboot.business.common.utils.BigDecimalUtil;
import com.huboot.business.common.utils.DateUtil;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.mybatis.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 账户中心-子账户明细ServiceImpl
 */
@Service("subAccountDetailServiceImpl")
public class SubAccountDetailServiceImpl extends AbstractBaseService<SubAccountDetailDomain, Long> implements ISubAccountDetailService {

    @Autowired
    private ISubAccountDetailDao subAccountDetailDao;

    @Autowired
    public SubAccountDetailServiceImpl(ISubAccountDetailDao subAccountDetailDao) {
        super(subAccountDetailDao);
    }

    @Autowired
    private ISubAccountBaseService subAccountBaseService;
    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private IAccountBaseService accountBaseService;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ISubAccountDetailPaymentService subAccountDetailPaymentService;
    @Autowired
    private ISubAccountDetailExtendService subAccountDetailExtendService;

    protected Lock agentPayLock = new ReentrantLock();//代付
    protected Lock redPacketLock = new ReentrantLock();//红包
    protected Lock cashBalanceLock = new ReentrantLock();//提现
    protected Lock balanceAccountLock = new ReentrantLock();//余额
    protected Lock billAccountLock = new ReentrantLock();//账单

    @Autowired
    private IXieHuaPayCallBack xieHuaPayCallBack;
    @Autowired
    private ISubAccountDetailService subAccountDetailService;

    @Override
    @Transactional
    public void createDetail(SubAccountDetailAddDTO subAccountDetailAddDTO) throws ApiException {
        User user = SecurityContextHelper.getCurrentUser();
        //调整的金额
        BigDecimal amount = subAccountDetailAddDTO.getAmountPaid();
        if (BigDecimalUtil.eq(amount, BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
        }
        if (SubAccountDetailTypeEnum.ChargeBack.getValue().equals(subAccountDetailAddDTO.getType()) || SubAccountDetailTypeEnum.Refund.getValue().equals(subAccountDetailAddDTO.getType())) {
            if (BigDecimalUtil.gt(amount, BigDecimal.ZERO)) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "金额必须小于0");
            }
        } else if (SubAccountDetailTypeEnum.Recharge.getValue().equals(subAccountDetailAddDTO.getType())) {
            if (BigDecimalUtil.lt(amount, BigDecimal.ZERO)) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "金额必须大于0");
            }
        }
        AssertUtil.notNull(subAccountDetailAddDTO.getSubAccountId());
        //查询子账户基础信息（押金账户）
        SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDetailAddDTO.getSubAccountId());
        /*if (AccountStatusEnum.Invalid.getValue().equals(subAccountBaseDomain.getStatus()) && !SubAccountDetailTypeEnum.Recharge.getValue().equals(subAccountDetailAddDTO.getType())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "账户无效");
        }*/

        //创建账户交易明细
        SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
        subAccountDetailDomain.setAccountId(subAccountBaseDomain.getAccountId());
        subAccountDetailDomain.setSubAccountId(subAccountDetailAddDTO.getSubAccountId());
        subAccountDetailDomain.setRemark(subAccountDetailAddDTO.getRemark());

        subAccountDetailDomain.setType(subAccountDetailAddDTO.getType());
        /*subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
        if (BigDecimalUtil.gt(amount, BigDecimal.ZERO)) {
            subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
            subAccountDetailDomain.setAmountPaid(subAccountDetailAddDTO.getAmountPaid());
        } else if (BigDecimalUtil.lt(amount, BigDecimal.ZERO)) {
            subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
            //变成正数
            subAccountDetailDomain.setAmountPaid(subAccountDetailAddDTO.getAmountPaid().multiply(new BigDecimal("-1")));
        } else {
            throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
        }*/
        subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.WaitForPay.getValue());
        subAccountDetailDomain.setTradeSn(subAccountDetailAddDTO.getTradeSn());
        subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
        this.create(subAccountDetailDomain);
        AccountBaseDomain accountBaseDomain = accountBaseService.findByAccountId(subAccountBaseDomain.getAccountId());
        //支付成功-非充值类型
        if (!SubAccountDetailTypeEnum.Recharge.getValue().equals(subAccountDetailDomain.getType())) {
            SubAccountDetailForPaidDTO subAccountDetailForPaidDTO = new SubAccountDetailForPaidDTO();
            subAccountDetailForPaidDTO.setDetailId(subAccountDetailDomain.getId());
            subAccountDetailForPaidDTO.setPaymentDate(new Date());
            try {
                subAccountDetailForPaidDTO.setOperator(user.getRealName());
            } catch (Exception e) {
                subAccountDetailForPaidDTO.setOperator("系统");
            }
            this.updateForPaid(subAccountDetailForPaidDTO);
        } else {
            //发送短信
            sendAfterRechargeNotice(accountBaseDomain.getShopId(), user.getLoginCustomer().getShopId(), subAccountDetailDomain.getAmountPaid());
        }

    }

    @Override
    public void cancelRecharge(Long id) throws ApiException {
        //取消收款
        SubAccountDetailDomain tage = new SubAccountDetailDomain();
        tage.setStatus(SubAccountDetailStatusEnum.Cancel.getValue());
        SubAccountDetailDomain sour = new SubAccountDetailDomain();
        sour.setId(id);
        sour.setStatus(SubAccountDetailStatusEnum.WaitForPay.getValue());
        int count = this.mergeByBeanProp(tage, sour);
        if (count == 0) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "收款明细发生变化");
        }
        //关闭支付交易？
//        subAccountDetailPaymentService.closeBySubAccountDetailId(id);
    }

    @Override
    public void cancelRecharge(String detailSn) throws ApiException {
        //取消收款
        SubAccountDetailDomain tage = new SubAccountDetailDomain();
        tage.setStatus(SubAccountDetailStatusEnum.Cancel.getValue());
        SubAccountDetailDomain sour = new SubAccountDetailDomain();
        sour.setSn(detailSn);
        sour.setStatus(SubAccountDetailStatusEnum.WaitForPay.getValue());
        int count = this.mergeByBeanProp(tage, sour);
        if (count == 0) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "收款明细发生变化");
        }
        //关闭支付交易？
//        subAccountDetailPaymentService.closeBySubAccountDetailId(id);
    }

    @Override
    public Boolean checkDetailPaidById(Long id) {
        SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
        subAccountDetailDomain.setId(id);
        subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
        subAccountDetailDomain = this.getSingleByBeanProp(subAccountDetailDomain);
        if (subAccountDetailDomain != null) {
            return true;
        }
        return false;
    }

    /**
     * @param accountId
     * @param subAccountId
     * @param status
     * @return
     * @throws ApiException
     */
    @Override
    public List<SubAccountDetailDomain> findByAccountIdsWithStatus(Long accountId, Long subAccountId, Integer status) throws ApiException {
        AssertUtil.notNull(accountId, "主账户id不能为空");
        AssertUtil.notNull(subAccountId, "子账户id不能为空");
        AssertUtil.notNull(status, "状态不能为空");
        SubAccountDetailDomain detail = new SubAccountDetailDomain();
        detail.setAccountId(accountId);
        detail.setSubAccountId(subAccountId);
        detail.setStatus(status);
        return this.findByBeanProp(detail);
    }

    @Override
    public Pager<SubAccountDetailDomain> findByAdminQueryForPager(QueryCondition queryCondition) {
        return subAccountDetailDao.findForPager(queryCondition, "findByAdminQueryForPager");
    }

    @Override
    @Transactional
    public void updateForPaid(SubAccountDetailForPaidDTO subAccountDetailForPaidDTO) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(subAccountDetailForPaidDTO.getDetailId());
        SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDetailDomain.getSubAccountId());
        if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.DepositAccount.getValue())) {
            this.updateDepositAccountForPaid(subAccountDetailForPaidDTO);
        } else if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.BalanceAccount.getValue())) {
            this.updateBalanceAccountForPaid(subAccountDetailForPaidDTO);
        } else if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.BillAccount.getValue())) {
            this.updateBillAccountForPaid(subAccountDetailForPaidDTO);
        }
    }


    @Override
    @Transactional
    public void updateForPayFail(SubAccountDetailForPayFailDTO subAccountDetailForPayFailDTO) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(subAccountDetailForPayFailDTO.getDetailId());
        SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDetailDomain.getSubAccountId());
        if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.DepositAccount.getValue())) {

        } else if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.BalanceAccount.getValue())) {
            this.updateBalanceAccountForPayFail(subAccountDetailForPayFailDTO);
        }
    }

    @Override
    public void updateForPaying(Long detailId) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(detailId);
        SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDetailDomain.getSubAccountId());
        if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.DepositAccount.getValue())) {

        } else if (subAccountBaseDomain.getType().equals(SubAccountTypeEnum.BalanceAccount.getValue())) {
            this.updateBalanceAccountForPaying(detailId);
        }
    }
    @Transactional
     void updateDepositAccountForPaid(SubAccountDetailForPaidDTO subAccountDetailForPaidDTO) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(subAccountDetailForPaidDTO.getDetailId());
        if (SubAccountDetailStatusEnum.Finished.getValue().equals(subAccountDetailDomain.getStatus())) {
            //判断重复支付
            logger.error("子账户明细已经支付，不能重复修改,detailId:{}", subAccountDetailForPaidDTO.getDetailId());
            throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经支付，不能重复修改状态");
        }
        //支付成功
        //调整的金额(正负)
        BigDecimal amount = null;
        /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
            amount = subAccountDetailDomain.getAmountPaid();
        } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
            amount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
        }*/
        SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
        newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
        //查询子账户基础信息（押金账户）
        SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDetailDomain.getSubAccountId());
        //首次支付成功-激活账户
        if (AccountStatusEnum.Invalid.getValue().equals(subAccountBaseDomain.getStatus())) {
            SubAccountDetailDomain subAccountDetailDomain1 = new SubAccountDetailDomain();
            subAccountDetailDomain1.setSubAccountId(subAccountBaseDomain.getSubAccountId());
            subAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
            List<SubAccountDetailDomain> subAccountDetailDomains = this.findByBeanProp(subAccountDetailDomain1);
            if (CollectionUtils.isEmpty(subAccountDetailDomains)) {
                subAccountBaseService.updateStatus(subAccountBaseDomain.getSubAccountId(), AccountStatusEnum.Valid.getValue());
            }
        }
        BigDecimal subAccountBalance = subAccountBaseDomain.getBalance();
        newSubAccountDetailDomain1.setOriginalAmount(subAccountBalance);
        newSubAccountDetailDomain1.setChangedAmount(subAccountBalance.add(amount));
        newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
        newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
        //检查变更后金额，不能小于0
        if (BigDecimalUtil.lt(subAccountDetailDomain.getChangedAmount(), BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "变更后的金额不能为0");
        }
        this.merge(newSubAccountDetailDomain1);
        //设置新的数据
        subAccountDetailDomain.setOriginalAmount(newSubAccountDetailDomain1.getOriginalAmount());
        subAccountDetailDomain.setChangedAmount(newSubAccountDetailDomain1.getChangedAmount());
        subAccountDetailDomain.setStatus(newSubAccountDetailDomain1.getStatus());
        subAccountDetailDomain.setPaymentDate(newSubAccountDetailDomain1.getPaymentDate());

        //更新账户金额，支付变化的金额一定是正数
        SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
        subAccountBaseDTO.setSubAccountId(subAccountBaseDomain.getSubAccountId());
        subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
        subAccountBaseDTO.setVersion(subAccountBaseDomain.getVersion());
        subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);


    }

    /**
     * @param buyerShopId
     * @param sellerShopId
     */
    public void sendAfterRechargeNotice(Long buyerShopId, Long sellerShopId, BigDecimal amount) {

    }

    @Override
    public Pager<SubAccountDetailDomain> findBalanceOrRedPacketAccountDetailForPager(QueryCondition queryCondition) throws ApiException {
        return subAccountDetailDao.findForPager(queryCondition, "findBalanceOrRedPacketAccountDetailForPager");
    }

    @Override
    public Pager<SubAccountDetailDomain> findBalancePayForAnotherForPager(QueryCondition queryCondition) throws ApiException {
        return subAccountDetailDao.findForPager(queryCondition, "findBalancePayForAnotherForPager");
    }

    //==================================================================余额账户 start ====================================
    @Override
    @Transactional
    public void cashBalanceAccount(SubAccountForCashDTO subAccountForCashDTO) {
        Lock lock = this.cashBalanceLock;
        try {
            lock.lock();
            SubAccountDTO subAccountDTO = subAccountService.findBalanceByAccountId(subAccountForCashDTO.getAccountId());
            if (subAccountDTO.getSubAccountBaseDomain().getStatus().equals(AccountStatusEnum.Invalid.getValue())) {
                throw new ApiException(ErrorCodeEnum.JsonError, "账户无效");
            }
            if (BigDecimalUtil.lt(subAccountDTO.getSubAccountBaseDomain().getUsableBalance(), subAccountForCashDTO.getAmountPaid())) {
                throw new ApiException(ErrorCodeEnum.JsonError, "账户余额不足");
            }
            Boolean exitCash = this.balanceAccountCanCash(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
            if (!exitCash) {
                throw new ApiException(ErrorCodeEnum.JsonError, "您已经超出今天提现的限制次数，请明天进行提现！");
            }
            //商户账户新增记录
            BalanceAccountDetailAddDTO subAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
            subAccountDetailAddDTO.setAccountId(subAccountForCashDTO.getAccountId());
            subAccountDetailAddDTO.setAmountPaid(subAccountForCashDTO.getAmountPaid());
            subAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.Cash.getValue());
            subAccountDetailAddDTO.setOperator(subAccountForCashDTO.getOperator());
            //subAccountDetailAddDTO.setOperatorType(OperatorTypeEnum.Business.getValue());
            subAccountDetailAddDTO.setOperatorPhone(subAccountForCashDTO.getOperatorPhone());
            subAccountDetailAddDTO.setOpenBankId(subAccountForCashDTO.getOpenBankId());
            subAccountDetailAddDTO.setUserName(subAccountForCashDTO.getUserName());
            subAccountDetailAddDTO.setCardNumber(subAccountForCashDTO.getCardNumber());
            subAccountDetailAddDTO.setRemark(subAccountForCashDTO.getOperator() + "提现申请");
            subAccountDetailAddDTO.setAccountType(subAccountForCashDTO.getAccountType());
            subAccountDetailAddDTO.setSubBank(subAccountForCashDTO.getSubBank());
            SubAccountDetailDomain subAccountDetailDomain = this.createBalanceAccountDetail(subAccountDetailAddDTO);
            //平台账户新增记录
            SubAccountDTO xiehuaSubAccountDTO = subAccountService.findBalanceByAccountId(1L);
            BalanceAccountDetailAddDTO xiehuaSubAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
            xiehuaSubAccountDetailAddDTO.setAccountId(xiehuaSubAccountDTO.getSubAccountBaseDomain().getAccountId());
            xiehuaSubAccountDetailAddDTO.setAmountPaid(subAccountForCashDTO.getAmountPaid());
            xiehuaSubAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.CashAudit.getValue());
            //xiehuaSubAccountDetailAddDTO.setOperator(subAccountForCashDTO.getOperator());
            xiehuaSubAccountDetailAddDTO.setTradeSn(subAccountDetailDomain.getSn());
            this.createBalanceAccountDetail(xiehuaSubAccountDetailAddDTO);
        } catch (Exception e) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "提现操作异常");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void refundBalanceAccount(SubAccountForRefundDTO subAccountForRefundDTO) {

        //记录操作日志
        User user = SecurityContextHelper.getCurrentUser();

        //商户账户新增记录
        BalanceAccountDetailAddDTO subAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
        subAccountDetailAddDTO.setAccountId(subAccountForRefundDTO.getSellerAccountId());
        subAccountDetailAddDTO.setAmountPaid(subAccountForRefundDTO.getAmountPaid());
        subAccountDetailAddDTO.setType(subAccountForRefundDTO.getType().getValue());
        subAccountDetailAddDTO.setOperator(subAccountForRefundDTO.getOperator());
        subAccountDetailAddDTO.setTradeSn(subAccountForRefundDTO.getTradeSn());
        /*if (SubAccountDetailTypeEnum.DepositRefund.getValue().equals(subAccountForRefundDTO.getType().getValue())) {
            subAccountDetailAddDTO.setRemark("订单号为" + subAccountForRefundDTO.getTradeSn() + "的押金退款");
            dto.setHandleContent("编辑了订单号："+subAccountForRefundDTO.getTradeSn()+"的车辆押金退款单");

        } else if (SubAccountDetailTypeEnum.RentRefund.getValue().equals(subAccountForRefundDTO.getType().getValue())) {
            subAccountDetailAddDTO.setRemark("订单号为" + subAccountForRefundDTO.getTradeSn() + "的租车费用退款");
            dto.setHandleContent("编辑了订单号："+subAccountForRefundDTO.getTradeSn()+"的租车费用退款单");

        }*/

        this.createBalanceAccountDetail(subAccountDetailAddDTO);

    }

    @Override
    public void incomeBalanceAccount(SubAccountForOrderDTO subAccountForOrderDTO) {
        //商户账户新增记录
        BalanceAccountDetailAddDTO subAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
        subAccountDetailAddDTO.setAccountId(subAccountForOrderDTO.getSellerAccountId());
        subAccountDetailAddDTO.setAmountPaid(subAccountForOrderDTO.getAmountPaid());
        subAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.TradeIncome.getValue());
        subAccountDetailAddDTO.setOperator(subAccountForOrderDTO.getOperator());
        subAccountDetailAddDTO.setTradeSn(subAccountForOrderDTO.getTradeSn());
        subAccountDetailAddDTO.setRemark("订单收入到账：订单号为" + subAccountForOrderDTO.getTradeSn() + "的订单收入到账");
        this.createBalanceAccountDetail(subAccountDetailAddDTO);
    }

    @Override
    public void incomeBalanceAccountForMarketLogin(SubAccountForLoginDTO subAccountForLoginDTO) {
        //商户账户新增记录
        BalanceAccountDetailAddDTO subAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
        subAccountDetailAddDTO.setAccountId(subAccountForLoginDTO.getSellerAccountId());
        subAccountDetailAddDTO.setAmountPaid(subAccountForLoginDTO.getAmountPaid());
        subAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.MarketLoginIncome.getValue());
        subAccountDetailAddDTO.setOperator(subAccountForLoginDTO.getOperator());
        subAccountDetailAddDTO.setRemark("登录返现已到账");
        this.createBalanceAccountDetail(subAccountDetailAddDTO);
    }

    @Override
    public void incomeBalanceAccountForMarketTrade(SubAccountForOrderDTO subAccountForOrderDTO) {
        //商户账户新增记录
        BalanceAccountDetailAddDTO subAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
        subAccountDetailAddDTO.setAccountId(subAccountForOrderDTO.getSellerAccountId());
        subAccountDetailAddDTO.setAmountPaid(subAccountForOrderDTO.getAmountPaid());
        subAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.MarketTradeIncome.getValue());
        subAccountDetailAddDTO.setOperator(subAccountForOrderDTO.getOperator());
        subAccountDetailAddDTO.setTradeSn(subAccountForOrderDTO.getTradeSn());
        subAccountDetailAddDTO.setRemark("交易抽奖返现到账：订单号" + subAccountForOrderDTO.getTradeSn() + "完成结算，返现金额已到账");
        this.createBalanceAccountDetail(subAccountDetailAddDTO);
    }

    @Override
    public SubAccountDetailDomain refundDepositBalanceAccount(SubAccountForRefundDepositDTO subAccountForRefundDepositDTO) {
        //买家账户新增记录
        BalanceAccountDetailAddDTO subAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
        subAccountDetailAddDTO.setAccountId(subAccountForRefundDepositDTO.getBuyerAccountId());
        subAccountDetailAddDTO.setAmountPaid(subAccountForRefundDepositDTO.getAmountPaid());
        subAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.DepositIn.getValue());
        subAccountDetailAddDTO.setOperator(subAccountForRefundDepositDTO.getOperator());
        subAccountDetailAddDTO.setTradeSn(subAccountForRefundDepositDTO.getTradeSn());
        subAccountDetailAddDTO.setRemark("押金转入：订单号为" + subAccountForRefundDepositDTO.getTradeSn() + "的押金退款");
        SubAccountDetailDomain subAccountDetailDomain = this.createBalanceAccountDetail(subAccountDetailAddDTO);

        //卖家账户新增记录
        BalanceAccountDetailAddDTO subAccountDetailAddDTO1 = new BalanceAccountDetailAddDTO();
        subAccountDetailAddDTO1.setAccountId(subAccountForRefundDepositDTO.getSellerAccountId());
        subAccountDetailAddDTO1.setAmountPaid(subAccountForRefundDepositDTO.getAmountPaid());
        subAccountDetailAddDTO1.setType(SubAccountDetailTypeEnum.DepositOut.getValue());
        subAccountDetailAddDTO1.setOperator(subAccountForRefundDepositDTO.getOperator());
        subAccountDetailAddDTO1.setTradeSn(subAccountForRefundDepositDTO.getTradeSn());
        subAccountDetailAddDTO1.setRemark("押金转出：订单号为" + subAccountForRefundDepositDTO.getTradeSn() + "的押金退款");
        this.createBalanceAccountDetail(subAccountDetailAddDTO1);

        return  subAccountDetailDomain;
    }

    @Override
    @Transactional
    public void cashBackFail(SubAccountCashBackFailDTO subAccountCashBackFailDTO) {
        BalanceAccountDetailAddDTO xiehuaSubAccountDetailAddDTO = new BalanceAccountDetailAddDTO();
        xiehuaSubAccountDetailAddDTO.setRemark(subAccountCashBackFailDTO.getRemark());
        xiehuaSubAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.CashFailBack.getValue());
        xiehuaSubAccountDetailAddDTO.setTradeSn(subAccountCashBackFailDTO.getTradeSn());
        xiehuaSubAccountDetailAddDTO.setAmountPaid(subAccountCashBackFailDTO.getAmountPaid());
        xiehuaSubAccountDetailAddDTO.setOriginalAmount(subAccountCashBackFailDTO.getOriginalAmount());
        xiehuaSubAccountDetailAddDTO.setChangedAmount(subAccountCashBackFailDTO.getChangedAmount());
        xiehuaSubAccountDetailAddDTO.setAccountId(subAccountCashBackFailDTO.getAccountId());
        xiehuaSubAccountDetailAddDTO.setOperator(subAccountCashBackFailDTO.getOperator());
        this.createBalanceAccountDetail(xiehuaSubAccountDetailAddDTO);
    }

    private SubAccountDetailDomain createBalanceAccountDetail(BalanceAccountDetailAddDTO balanceAccountDetailAddDTO) {
        AssertUtil.notNull(balanceAccountDetailAddDTO);
        AssertUtil.notNull(balanceAccountDetailAddDTO.getAccountId());
        AssertUtil.notNull(balanceAccountDetailAddDTO.getType());
        Lock lock = this.balanceAccountLock;
        try {
            lock.lock();
            //目前余额账户，业务规定只支持：提现，订单收入，提现返还失败
            if (!balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.Cash.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradeIncome.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.RentRefund.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.DepositRefund.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.CashAudit.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.CashFailBack.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.DepositOut.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.MarketLoginIncome.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.MarketTradeIncome.getValue())
                    && !balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.DepositIn.getValue())) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "业务类型不匹配");
            }
            BigDecimal amount = balanceAccountDetailAddDTO.getAmountPaid();
            if (BigDecimalUtil.eq(amount, BigDecimal.ZERO)) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
            }
            //查询子账户基础信息
            SubAccountDTO subAccountDTO = subAccountService.findBalanceByAccountId(balanceAccountDetailAddDTO.getAccountId());
        /*if (AccountStatusEnum.Invalid.getValue().equals(subAccountDTO.getSubAccountBaseDomain().getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "账户无效");
        }*/
            //创建账户交易明细
            SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
            if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.Cash.getValue())) {
                AssertUtil.notBlank(balanceAccountDetailAddDTO.getCardNumber());
                AssertUtil.notNull(balanceAccountDetailAddDTO.getOpenBankId());
                AssertUtil.notBlank(balanceAccountDetailAddDTO.getUserName());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                subAccountDetailDomain.setOperatorType(balanceAccountDetailAddDTO.getOperatorType());
                subAccountDetailDomain.setOperatorPhone(balanceAccountDetailAddDTO.getOperatorPhone());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.processing.getValue());
                BigDecimal tempAmount = null;
                /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                SubAccountBaseDomain newSubAccountBaseDomain = new SubAccountBaseDomain();
                newSubAccountBaseDomain.setId(subAccountBaseDomain.getId());
                newSubAccountBaseDomain.setBalance(subAccountDetailDomain.getChangedAmount());
                newSubAccountBaseDomain.setUsableBalance(subAccountDTO.getSubAccountBaseDomain().getUsableBalance().add(tempAmount));
                newSubAccountBaseDomain.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                Integer updateCount = subAccountBaseService.merge(newSubAccountBaseDomain);
                if (updateCount == 0) {
                    throw new ApiException(ErrorCodeEnum.ParamsError, "账户余额更新失败");
                }
                //subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
                //创建提现扩展
                SubAccountDetailExtendDomain subAccountDetailExtendDomain = new SubAccountDetailExtendDomain();
                //BankDomain bankDomain = bankService.find(balanceAccountDetailAddDTO.getOpenBankId());
                SubAccountDetailCashExtendDomain subAccountDetailCashExtendDomain = new SubAccountDetailCashExtendDomain();
                subAccountDetailCashExtendDomain.setUserName(balanceAccountDetailAddDTO.getUserName());
               // subAccountDetailCashExtendDomain.setOpenBankCode(bankDomain.getCode());
                subAccountDetailCashExtendDomain.setCardNumber(balanceAccountDetailAddDTO.getCardNumber());
                //subAccountDetailCashExtendDomain.setOpenBank(bankDomain.getName());
                //subAccountDetailCashExtendDomain.setOpenBankPingppCode(bankDomain.getPingppCode());
                subAccountDetailCashExtendDomain.setAccountType(balanceAccountDetailAddDTO.getAccountType().getValue());
                subAccountDetailCashExtendDomain.setSubBank(balanceAccountDetailAddDTO.getSubBank());
                String subAccountDetailCashExtendDomainString = JsonUtils.toJsonString(subAccountDetailCashExtendDomain);
                subAccountDetailExtendDomain.setAccountId(subAccountDetailDomain.getAccountId());
                subAccountDetailExtendDomain.setSubAccountId(subAccountDetailDomain.getSubAccountId());
                subAccountDetailExtendDomain.setSubAccountDetailId(subAccountDetailDomain.getId());
                subAccountDetailExtendDomain.setCashExtend(subAccountDetailCashExtendDomainString);
                subAccountDetailExtendService.create(subAccountDetailExtendDomain);
            } else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradeIncome.getValue())) {
                AssertUtil.notBlank(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.processing.getValue());
                this.create(subAccountDetailDomain);
            }else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.MarketLoginIncome.getValue())) {
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.processing.getValue());
                this.create(subAccountDetailDomain);
            }else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.MarketTradeIncome.getValue())) {
                AssertUtil.notBlank(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.processing.getValue());
                this.create(subAccountDetailDomain);
            } else if (SubAccountDetailTypeEnum.DepositRefund.getValue().equals(balanceAccountDetailAddDTO.getType()) || SubAccountDetailTypeEnum.RentRefund.getValue().equals(balanceAccountDetailAddDTO.getType())) {
                AssertUtil.notBlank(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.processing.getValue());
                this.create(subAccountDetailDomain);
            } else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.CashAudit.getValue())) {
                AssertUtil.notBlank(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.UnpayForAnother.getValue());
                this.create(subAccountDetailDomain);
            } else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.CashFailBack.getValue())) {
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
                //subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                //subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOriginalAmount(balanceAccountDetailAddDTO.getOriginalAmount());
                subAccountDetailDomain.setChangedAmount(balanceAccountDetailAddDTO.getChangedAmount());
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDetailDomain.getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            }else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.DepositIn.getValue())) {
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                subAccountDetailDomain.setOperatorType(balanceAccountDetailAddDTO.getOperatorType());
                subAccountDetailDomain.setOperatorPhone(balanceAccountDetailAddDTO.getOperatorPhone());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                BigDecimal tempAmount = null;
                /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额
                /*SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);*/
                SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                SubAccountBaseDomain newSubAccountBaseDomain = new SubAccountBaseDomain();
                newSubAccountBaseDomain.setId(subAccountBaseDomain.getId());
                newSubAccountBaseDomain.setBalance(subAccountDetailDomain.getChangedAmount());
                newSubAccountBaseDomain.setUsableBalance(subAccountBaseDomain.getUsableBalance().add(tempAmount));
                newSubAccountBaseDomain.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                Integer updateCount = subAccountBaseService.merge(newSubAccountBaseDomain);
                if (updateCount == 0) {
                    throw new ApiException(ErrorCodeEnum.ParamsError, "账户余额更新失败");
                }

            }else if (balanceAccountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.DepositOut.getValue())) {
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(balanceAccountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(balanceAccountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(balanceAccountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(balanceAccountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(balanceAccountDetailAddDTO.getOperator());
                subAccountDetailDomain.setOperatorType(balanceAccountDetailAddDTO.getOperatorType());
                subAccountDetailDomain.setOperatorPhone(balanceAccountDetailAddDTO.getOperatorPhone());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                BigDecimal tempAmount = null;
                /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额
                /*SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);*/
                SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                SubAccountBaseDomain newSubAccountBaseDomain = new SubAccountBaseDomain();
                newSubAccountBaseDomain.setId(subAccountBaseDomain.getId());
                newSubAccountBaseDomain.setBalance(subAccountDetailDomain.getChangedAmount());
                newSubAccountBaseDomain.setUsableBalance(subAccountBaseDomain.getUsableBalance().add(tempAmount));
                newSubAccountBaseDomain.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                Integer updateCount = subAccountBaseService.merge(newSubAccountBaseDomain);
                if (updateCount == 0) {
                    throw new ApiException(ErrorCodeEnum.ParamsError, "账户余额更新失败");
                }
            } else {
                throw new ApiException(ErrorCodeEnum.JsonError, "业务类型不匹配");
            }
//        AccountBaseDomain accountBaseDomain = accountBaseService.findByAccountId(subAccountBaseDomain.getAccountId());
            //支付成功-非充值类型
            if (SubAccountDetailTypeEnum.TradeIncome.getValue().equals(subAccountDetailDomain.getType())
                    || SubAccountDetailTypeEnum.MarketLoginIncome.getValue().equals(subAccountDetailDomain.getType())
                    || SubAccountDetailTypeEnum.MarketTradeIncome.getValue().equals(subAccountDetailDomain.getType())) {
                SubAccountDetailForPaidDTO subAccountDetailForPaidDTO = new SubAccountDetailForPaidDTO();
                subAccountDetailForPaidDTO.setDetailId(subAccountDetailDomain.getId());
                subAccountDetailForPaidDTO.setPaymentDate(new Date());
                this.updateBalanceAccountForPaid(subAccountDetailForPaidDTO);
            } else if (SubAccountDetailTypeEnum.DepositRefund.getValue().equals(subAccountDetailDomain.getType()) || SubAccountDetailTypeEnum.RentRefund.getValue().equals(subAccountDetailDomain.getType())) {
                SubAccountDetailForPaidDTO subAccountDetailForPaidDTO = new SubAccountDetailForPaidDTO();
                subAccountDetailForPaidDTO.setDetailId(subAccountDetailDomain.getId());
                subAccountDetailForPaidDTO.setPaymentDate(new Date());
                this.updateBalanceAccountForPaid(subAccountDetailForPaidDTO);
            } else {
                //发送短信
//            sendAfterRechargeNotice(accountBaseDomain.getShopId(), user.getLoginCustomer().getShopName(), subAccountDetailDomain.getAmountPaid());
            }
            return subAccountDetailDomain;
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }

    }

    @Transactional
    private void updateBalanceAccountForPaid(SubAccountDetailForPaidDTO subAccountDetailForPaidDTO) {
        Lock lock = this.balanceAccountLock;
        try {
            lock.lock();
            SubAccountDetailDomain subAccountDetailDomain = this.find(subAccountDetailForPaidDTO.getDetailId());
            //提现
            if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.Cash.getValue())) {
                if (SubAccountDetailStatusEnum.Received.getValue().equals(subAccountDetailDomain.getStatus())) {
                    //判断重复支付
                    logger.error("子账户明细已经支付，不能重复修改,detailId:{}", subAccountDetailForPaidDTO.getDetailId());
                    throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经支付，不能重复修改状态");
                }
                SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
                newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
                newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                this.merge(newSubAccountDetailDomain1);
            } else if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.TradeIncome.getValue())
                    ||subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.MarketLoginIncome.getValue())
                    || subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.MarketTradeIncome.getValue())) {//订单成功
                if (SubAccountDetailStatusEnum.Received.getValue().equals(subAccountDetailDomain.getStatus())) {
                    //判断重复支付
                    logger.error("子账户明细已经支付，不能重复修改,detailId:{}", subAccountDetailForPaidDTO.getDetailId());
                    throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经支付，不能重复修改状态");
                }
                //支付成功
                //调整的金额(正负)
                BigDecimal amount = null;
               /* if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    amount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    amount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
                newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
                //查询子账户基础信息（押金账户）
                SubAccountDTO subAccountDTO = subAccountService.findBalanceByAccountId(subAccountDetailDomain.getAccountId());
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                //可用余额
                BigDecimal usableBalance = subAccountDTO.getSubAccountBaseDomain().getUsableBalance();
                usableBalance = usableBalance.add(amount);

                newSubAccountDetailDomain1.setOriginalAmount(subAccountBalance);
                newSubAccountDetailDomain1.setChangedAmount(subAccountBalance.add(amount));
                newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                this.merge(newSubAccountDetailDomain1);
                //设置新的数据
                subAccountDetailDomain.setOriginalAmount(newSubAccountDetailDomain1.getOriginalAmount());
                subAccountDetailDomain.setChangedAmount(newSubAccountDetailDomain1.getChangedAmount());
                subAccountDetailDomain.setStatus(newSubAccountDetailDomain1.getStatus());
                subAccountDetailDomain.setPaymentDate(newSubAccountDetailDomain1.getPaymentDate());
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());

                SubAccountBaseDomain subAccountBaseDomain = subAccountBaseService.findBySubAccountId(subAccountBaseDTO.getSubAccountId());
                SubAccountBaseDomain newSubAccountBaseDomain = new SubAccountBaseDomain();
                newSubAccountBaseDomain.setId(subAccountBaseDomain.getId());
                newSubAccountBaseDomain.setBalance(subAccountBaseDTO.getBalance());
                newSubAccountBaseDomain.setUsableBalance(usableBalance);
                newSubAccountBaseDomain.setVersion(subAccountBaseDTO.getVersion());
                Integer updateCount = subAccountBaseService.merge(newSubAccountBaseDomain);
                if (updateCount == 0) {
                    throw new ApiException(ErrorCodeEnum.ParamsError, "账户余额更新失败");
                }
                //subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else if (SubAccountDetailTypeEnum.DepositRefund.getValue().equals(subAccountDetailDomain.getType()) || SubAccountDetailTypeEnum.RentRefund.getValue().equals(subAccountDetailDomain.getType())) {//自营退款
                if (SubAccountDetailStatusEnum.Received.getValue().equals(subAccountDetailDomain.getStatus())) {
                    //判断重复支付
                    logger.error("子账户明细已经支付，不能重复修改,detailId:{}", subAccountDetailForPaidDTO.getDetailId());
                    throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经支付，不能重复修改状态");
                }
                //支付成功
                //调整的金额(正负)
                BigDecimal amount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
                newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
                //查询子账户基础信息（余额账户）
                SubAccountDTO subAccountDTO = subAccountService.findBalanceByAccountId(subAccountDetailDomain.getAccountId());
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                newSubAccountDetailDomain1.setOriginalAmount(subAccountBalance);
                newSubAccountDetailDomain1.setChangedAmount(subAccountBalance.add(amount));
                newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                this.merge(newSubAccountDetailDomain1);
                //设置新的数据
                subAccountDetailDomain.setOriginalAmount(newSubAccountDetailDomain1.getOriginalAmount());
                subAccountDetailDomain.setChangedAmount(newSubAccountDetailDomain1.getChangedAmount());
                subAccountDetailDomain.setStatus(newSubAccountDetailDomain1.getStatus());
                subAccountDetailDomain.setPaymentDate(newSubAccountDetailDomain1.getPaymentDate());
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.CashAudit.getValue())) {//已代付-到账触发
                if (!subAccountDetailDomain.getStatus().equals(SubAccountDetailStatusEnum.PayedAnotherReceived.getValue())) {
                    SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
                    newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
                    //newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                    //查询子账户基础信息
                    newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.PayedAnotherReceived.getValue());
                    //此PaymentDate字段不能复制，在提交代付的时候，用来记录已提交代付的时间
                    //newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                    this.merge(newSubAccountDetailDomain1);
                }
                //触发修改用户提现的记录
                SubAccountDetailDTO subAccountDetailDTO = this.findByDetailSn(subAccountDetailDomain.getTradeSn());
                SubAccountDetailForPaidDTO subAccountDetailForPaidDTO1 = new SubAccountDetailForPaidDTO();
                subAccountDetailForPaidDTO1.setDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
                subAccountDetailForPaidDTO1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                this.updateBalanceAccountForPaid(subAccountDetailForPaidDTO1);
                //
                sendCashReceiveNotice(subAccountDetailDTO.getSubAccountDetailDomain());
            } else if(subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKRefundPay.getValue())
                    || subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKShuttleRefundPay.getValue())
                    || subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKMallRefundPay.getValue())) {
                //直客订单退款成功
                this.zkRefundSuccess(subAccountDetailDomain, subAccountDetailForPaidDTO);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    private void updateBalanceAccountForPaying(Long detailId) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(detailId);
        if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.Cash.getValue())) {//提现

        } else if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.TradeIncome.getValue())) {//订单

        } else if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.CashAudit.getValue())) {//提现审核

        }
    }

    @Transactional
    private void updateBalanceAccountForPayFail(SubAccountDetailForPayFailDTO subAccountDetailForPayFailDTO) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(subAccountDetailForPayFailDTO.getDetailId());
        //提现
        if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.Cash.getValue())) {

            SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
            newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
            newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.CashFail.getValue());
            newSubAccountDetailDomain1.setRemark(subAccountDetailForPayFailDTO.getRemark());
            this.merge(newSubAccountDetailDomain1);

        } else if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.TradeIncome.getValue())) {//订单失败

        } else if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.CashAudit.getValue())) {//代付失败
            if (!SubAccountDetailStatusEnum.BackPayedForAnother.getValue().equals(subAccountDetailDomain.getStatus())) {
                //判断重复支付
                /*logger.error("子账户明细已经支付，不能重复修改,detailId:{}", subAccountDetailForPayFailDTO.getDetailId());
                throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经支付，不能重复修改状态");*/
                //支付失败
                SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
                newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
                //newSubAccountDetailDomain1.setPaymentDate(new Date());
                //查询子账户基础信息
                newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.BackPayedForAnother.getValue());
                newSubAccountDetailDomain1.setRemark(subAccountDetailForPayFailDTO.getRemark());
                this.merge(newSubAccountDetailDomain1);
            }

        }

        //记录操作日志
        /*if (!SubAccountDetailTypeEnum.Recharge.getValue().equals(subAccountDetailDomain.getType())) {
            User user = SecurityContextHelper.getCurrentUser();
            AccountBaseDomain accountBaseDomain = accountBaseService.findByAccountId(subAccountDetailDomain.getAccountId());
            EsManageLogDTO dto = new EsManageLogDTO();
            dto.setId(String.valueOf(PrimaryKeyGenerator.SEQUENCE.next()));
            dto.setHandleModule(LogHandleModuleEnum.FbusinessManage.getShowName());
            dto.setHandlerName(user.getRealName());
            dto.setHandlerPhone(user.getPhone());
            dto.setHandleTime(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(accountBaseDomain.getShopName());
            sb.append("F模式押金金额从");
            sb.append(subAccountDetailDomain.getOriginalAmount());
            sb.append("元调整为");
            sb.append(subAccountDetailDomain.getChangedAmount());
            sb.append("元，调整类型为");
            sb.append(SubAccountDetailTypeEnum.valueOf(subAccountDetailDomain.getType()).getShowName());
            dto.setHandleContent(sb.toString());
            esManageLogService.saveLog(dto);
        }*/
    }


    /**
     * @return
     */
    public Boolean balanceAccountCanCash(Long balanceAccountId) {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.addConditionByColName("sub_account_id", QueryOperatorEnum.eq, balanceAccountId);
        Date today = DateUtil.getCurrentData();
        queryCondition.addConditionByColName("create_time", QueryOperatorEnum.gte, today);
        queryCondition.addConditionByColName("create_time", QueryOperatorEnum.lt, DateUtil.getDateAddDay(today, 1));
        queryCondition.addConditionByColName("type", QueryOperatorEnum.eq, SubAccountDetailTypeEnum.Cash.getValue());
//        queryCondition.addConditionByColName("status", QueryOperatorEnum.in, new Integer[]{SubAccountDetailStatusEnum.processing.getValue(), SubAccountDetailStatusEnum.Received.getValue()});
        Pager<SubAccountDetailDomain> subAccountDetailDomainPager = this.findForPager(queryCondition);
        int rows = subAccountDetailDomainPager.getRowsCount();
        /*if (rows >= AccountConstant.CASH_NUM) {
            return false;
        }*/
        return true;
    }

    @Override
    public SubAccountDetailDTO findByDetailSn(String sn) {
        SubAccountDetailDTO subAccountDetailDTO = new SubAccountDetailDTO();
        SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
        subAccountDetailDomain.setSn(sn);
        subAccountDetailDomain = this.getSingleByBeanProp(subAccountDetailDomain);
        //扩展信息
        SubAccountDetailExtendDTO subAccountDetailExtendDTO = subAccountDetailExtendService.findByDetailId(subAccountDetailDomain.getId());
        subAccountDetailDTO.setSubAccountDetailDomain(subAccountDetailDomain);
        subAccountDetailDTO.setSubAccountDetailExtendDTO(subAccountDetailExtendDTO);
        return subAccountDetailDTO;
    }

    @Override
    public SubAccountDetailDTO findByDetailId(Long id) {
        SubAccountDetailDTO subAccountDetailDTO = new SubAccountDetailDTO();
        SubAccountDetailDomain subAccountDetailDomain = this.find(id);
        //扩展信息
        SubAccountDetailExtendDTO subAccountDetailExtendDTO = subAccountDetailExtendService.findByDetailId(subAccountDetailDomain.getId());
        subAccountDetailDTO.setSubAccountDetailDomain(subAccountDetailDomain);
        subAccountDetailDTO.setSubAccountDetailExtendDTO(subAccountDetailExtendDTO);
        return subAccountDetailDTO;
    }

    @Override
    public List<SubAccountDetailDomain> findBySubAccountIdAndTradeSnAndType(Long subAccountId, String tradeSn, Integer type) {
        SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
        subAccountDetailDomain.setSubAccountId(subAccountId);
        subAccountDetailDomain.setTradeSn(tradeSn);
        subAccountDetailDomain.setType(type);
        List<SubAccountDetailDomain> subAccountDetailDomains = this.findByBeanProp(subAccountDetailDomain);
        return subAccountDetailDomains;
    }

    @Override
    public void payAnotherStatusTask(Long detailId) {
        SubAccountDetailDomain subAccountDetailDomain = this.find(detailId);
        if (subAccountDetailDomain.getStatus().equals(SubAccountDetailStatusEnum.CommitedPayForAnother.getValue())) {
            subAccountDetailPaymentService.getTransferByDetailId(subAccountDetailDomain.getId());
        }
    }

    @Override
    @Transactional
    public void submitsPayAnother(SubAccountDetailForPayAnotherDTO dto) throws ApiException {
        AssertUtil.notNull(dto);
        AssertUtil.notNull(dto.getId());
        AssertUtil.notNull(dto.getOperator());
        AssertUtil.notNull(dto.getPaymentType());
        if(!(dto.getPaymentType().intValue() == SubAccountDetailPaymentTypeEnum.OfflineTransfer.getValue().intValue() || dto.getPaymentType().intValue() == SubAccountDetailPaymentTypeEnum.UnionAgentPay.getValue().intValue())){
            throw new ApiException(ErrorCodeEnum.ParamsError,"支付类型异常");
        }
        if(dto.getPaymentType().intValue() == SubAccountDetailPaymentTypeEnum.OfflineTransfer.getValue().intValue() && org.apache.commons.lang.StringUtils.isEmpty(dto.getBankFlow())){
            throw new ApiException(ErrorCodeEnum.ParamsError,"线下转账银行流水不能为空");
        }
        Lock lock = this.agentPayLock;
        try {
            lock.lock();
            SubAccountDetailDomain subAccountDetailDomain = null;
            subAccountDetailDomain = this.find(dto.getId());
            if (!subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.CashAudit.getValue())) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "该操作必须为提现代付");
            }
            if (!subAccountDetailDomain.getStatus().equals(SubAccountDetailStatusEnum.UnpayForAnother.getValue()) && !subAccountDetailDomain.getStatus().equals(SubAccountDetailStatusEnum.BackPayedForAnother.getValue())) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "该操作状态必须为未代付");
            }

            SubAccountDetailDomain detailDomain = null;

            //修改为已提交代付
            detailDomain = new SubAccountDetailDomain();
            detailDomain.setId(subAccountDetailDomain.getId());
            detailDomain.setPaymentDate(new Date());
            detailDomain.setRemark(dto.getRemark());
            detailDomain.setOperator(dto.getOperator());
            detailDomain.setStatus(SubAccountDetailStatusEnum.CommitedPayForAnother.getValue());
            Date paymentDate = new Date();
            if(dto.getPaymentType().intValue() == SubAccountDetailPaymentTypeEnum.OfflineTransfer.getValue().intValue()){
                detailDomain.setStatus(SubAccountDetailStatusEnum.PayedAnotherReceived.getValue());
                detailDomain.setPaymentDate(paymentDate);
            }
            this.merge(detailDomain);

            //预支付记录
            SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO = new SubAccountDetailPrePaymentDTO();
            subAccountDetailPrePaymentDTO.setDetailId(dto.getId());

            SubAccountDetailPaymentDomain subAccountDetailPaymentDomain;
            if(dto.getPaymentType().intValue() == SubAccountDetailPaymentTypeEnum.OfflineTransfer.getValue().intValue()) {

                subAccountDetailPrePaymentDTO.setPaymentType(SubAccountDetailPaymentTypeEnum.OfflineTransfer.getValue());
                subAccountDetailPaymentDomain = subAccountDetailPaymentService.offlinePrePayForDetail(subAccountDetailPrePaymentDTO,dto);

                //处理subAccountDetail对应的提现记录为已处理
                SubAccountDetailDomain source = find(subAccountDetailDomain.getId());
                if(source == null){
                    throw new ApiException("提现异常");
                }
                SubAccountDetailDomain query = new SubAccountDetailDomain();
                query.setSn(source.getTradeSn());
                query.setStatus(SubAccountDetailStatusEnum.processing.getValue());
                List<SubAccountDetailDomain> re = findByBeanProp(query);
                if(re.isEmpty()){
                    throw new ApiException("提现异常");
                }
                SubAccountDetailDomain domain = re.get(0);
                SubAccountDetailDomain mergeDomain = new SubAccountDetailDomain();
                mergeDomain.setId(domain.getId());
                mergeDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                mergeDomain.setPaymentDate(paymentDate);
                this.merge(mergeDomain);

            }else{
                subAccountDetailPrePaymentDTO.setPaymentType(SubAccountDetailPaymentTypeEnum.UnionAgentPay.getValue());
                subAccountDetailPaymentDomain = subAccountDetailPaymentService.prePayForDetail(subAccountDetailPrePaymentDTO);
                SubAccountDetailPaymentTransferDTO subAccountDetailPaymentTransferDTO = new SubAccountDetailPaymentTransferDTO();
                subAccountDetailPaymentTransferDTO.setPaymentId(subAccountDetailPaymentDomain.getId());
                subAccountDetailPaymentService.transfer(subAccountDetailPaymentTransferDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            throw new ApiException(ErrorCodeEnum.ParamsError, "代付操作异常");
        } finally {
            lock.unlock();
        }

    }
    //==================================================================余额账户 end ====================================

    //==================================================================红包账户 start ====================================
    public SubAccountDetailDTO createRedPacketAccountDetail(RedPacketAccountDetailAddDTO accountDetailAddDTO) {
        AssertUtil.notNull(accountDetailAddDTO);
        AssertUtil.notNull(accountDetailAddDTO.getAccountId());
        AssertUtil.notNull(accountDetailAddDTO.getType());
        Lock lock = this.redPacketLock;
        try {
            lock.lock();
            //目前余额账户，业务规定只支持：提现，订单收入，提现返还失败
            if (!accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradeRewards.getValue())
                    && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.PayDeduction.getValue())
                    && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.ExpireInvalid.getValue())
                    && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.SystemProvide.getValue())
                    && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.AddShopCar.getValue())) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "业务类型不匹配");
            }
            BigDecimal amount = accountDetailAddDTO.getAmountPaid();
            if (BigDecimalUtil.eq(amount, BigDecimal.ZERO)) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
            }
            //查询子账户基础信息
            SubAccountDTO subAccountDTO = subAccountService.findRedPacketByAccountId(accountDetailAddDTO.getAccountId());
        /*if (AccountStatusEnum.Invalid.getValue().equals(subAccountDTO.getSubAccountBaseDomain().getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "账户无效");
        }*/
            //创建账户交易明细
            SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
            if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradeRewards.getValue())) {
                AssertUtil.notBlank(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(accountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(accountDetailAddDTO.getTradeSource());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
                subAccountDetailDomain.setOperatorType(accountDetailAddDTO.getOperatorType());
                subAccountDetailDomain.setOperatorPhone(accountDetailAddDTO.getOperatorPhone());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setPaymentDate(new Date());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                BigDecimal tempAmount = null;
               /* if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.PayDeduction.getValue())) {
                AssertUtil.notBlank(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(accountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(accountDetailAddDTO.getTradeSource());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setPaymentDate(new Date());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
                BigDecimal tempAmount = null;
                /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.ExpireInvalid.getValue())) {
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(accountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(accountDetailAddDTO.getTradeSource());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setPaymentDate(new Date());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
                BigDecimal tempAmount = null;
                /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.SystemProvide.getValue())) {
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(accountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(accountDetailAddDTO.getTradeSource());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setPaymentDate(new Date());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                BigDecimal tempAmount = null;
               /* if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.AddShopCar.getValue())) {
                AssertUtil.notBlank(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(accountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(accountDetailAddDTO.getTradeSource());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
                subAccountDetailDomain.setOperatorType(accountDetailAddDTO.getOperatorType());
                subAccountDetailDomain.setOperatorPhone(accountDetailAddDTO.getOperatorPhone());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
                subAccountDetailDomain.setPaymentDate(new Date());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
                BigDecimal tempAmount = null;
                /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid();
                } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                    tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
                }*/
                BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
                subAccountDetailDomain.setOriginalAmount(subAccountBalance);
                subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
                this.create(subAccountDetailDomain);
                //更新账户金额，支付变化的金额一定是正数
                SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
                subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
                subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
                subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
            } else {
                throw new ApiException(ErrorCodeEnum.JsonError, "业务类型不匹配");
            }
            SubAccountDetailDTO subAccountDetailDTO = new SubAccountDetailDTO();
            subAccountDetailDTO.setSubAccountDetailDomain(subAccountDetailDomain);
            return subAccountDetailDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.ParamsError, "创建红包明细失败");
        } finally {
            lock.unlock();
        }
    }
    //==================================================================红包账户 end ====================================

    /**
     * 发送买家取消订单通知消息
     *
     * @param subAccountDetailDomain
     */
    private void sendCashReceiveNotice(SubAccountDetailDomain subAccountDetailDomain) {
        AccountBaseDomain accountBaseDomain = accountBaseService.findByAccountId(subAccountDetailDomain.getAccountId());
        //UserDomain userDomain = shopContactService.findDefaultUserByShopId(accountBaseDomain.getShopId());
        //银行账户信息
        SubAccountDetailExtendDomain subAccountDetailExtendDomain = new SubAccountDetailExtendDomain();
        subAccountDetailExtendDomain.setSubAccountDetailId(subAccountDetailDomain.getId());
        SubAccountDetailExtendDomain subAccountDetailExtend = subAccountDetailExtendService.getSingleByBeanProp(subAccountDetailExtendDomain);
        String cashExtend = subAccountDetailExtend.getCashExtend();
        SubAccountDetailCashExtendDomain cashExtendDomain = JsonUtils.fromJson(cashExtend, SubAccountDetailCashExtendDomain.class);

        //发送短信
       /* if (!org.apache.commons.lang.StringUtils.isEmpty(userDomain.getPhone())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(subAccountDetailDomain.getCreateTime());
            SmsDTO smsDTO = new SmsDTO();
            List<String> mobiles = new ArrayList<>();
            mobiles.add(userDomain.getPhone());
            smsDTO.setMobiles(mobiles);
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("year", String.valueOf(calendar.get(Calendar.YEAR)));
            contentMap.put("month", String.valueOf(calendar.get(Calendar.MONTH) + 1));
            contentMap.put("day", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            contentMap.put("card", cashExtendDomain.getCardNumber().substring(cashExtendDomain.getCardNumber().length() - 4));
            contentMap.put("bank", cashExtendDomain.getOpenBank());
            smsDTO.setContentMap(contentMap);
            SendSmsQueueThread sendSmsQueueThread = new SendSmsQueueThread(SmsTemplateTypeEnum.CashReceiveNotice, smsDTO);
            taskExecutor.execute(sendSmsQueueThread);
        }*/
    }

    @Override
    public BigDecimal sumRedPacketSubAccount(String shopName, List<Integer> types) {
        Map<String, Object> map = new HashedMap();
        map.put("shopName", shopName);
        map.put("types", types);
        return subAccountDetailDao.sumRedPacketSubAccount(map);
    }

    //======================================================= 账单账户 start ===================================================
    public SubAccountDetailDTO createBillAccountDetail(BillAccountDetailAddDTO accountDetailAddDTO) {
        Lock lock = this.billAccountLock;
        try {
            lock.lock();
            AssertUtil.notNull(accountDetailAddDTO);
            AssertUtil.notNull(accountDetailAddDTO.getBuyerAccountId());
            AssertUtil.notNull(accountDetailAddDTO.getSellerAccountId());
            AssertUtil.notNull(accountDetailAddDTO.getType());
            AssertUtil.notNull(accountDetailAddDTO.getAmountPaid());
            //目前余额账户，业务规定只支持：提现，订单收入，提现返还失败
            if (!accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradePay.getValue())) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "业务类型不匹配");
            }
            BigDecimal amount = accountDetailAddDTO.getAmountPaid();
            if (BigDecimalUtil.eq(amount, BigDecimal.ZERO)) {
                throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
            }
            //查询子账户基础信息
            SubAccountDTO subAccountDTO = subAccountService.findBillByAccountId(accountDetailAddDTO.getBuyerAccountId());
            //创建账户交易明细
            SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
            if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradePay.getValue())) {
                AssertUtil.notBlank(accountDetailAddDTO.getTradeSn(), "交易单编号不能为空");
                subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
                subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
                subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
                subAccountDetailDomain.setType(accountDetailAddDTO.getType());
                subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
                subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
//                subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
                subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
                subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
//                subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Subtract.getValue());
                subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.WaitForPay.getValue());
                this.create(subAccountDetailDomain);
                //创建扩展信息
                SubAccountDetailExtendDomain subAccountDetailExtendDomain = new SubAccountDetailExtendDomain();
                subAccountDetailExtendDomain.setAccountId(subAccountDetailDomain.getAccountId());
                subAccountDetailExtendDomain.setSubAccountDetailId(subAccountDetailDomain.getId());
                subAccountDetailExtendDomain.setSubAccountId(subAccountDetailDomain.getSubAccountId());
                SubAccountDetailPaymentExtendDomain subAccountDetailPaymentExtendDomain = new SubAccountDetailPaymentExtendDomain();
                subAccountDetailPaymentExtendDomain.setMaxRedPacketAmount(accountDetailAddDTO.getMaxRedPacketAmount());
                subAccountDetailPaymentExtendDomain.setSellerAccountId(accountDetailAddDTO.getSellerAccountId());
                subAccountDetailExtendDomain.setPaymentExtend(JsonUtils.toJsonString(subAccountDetailPaymentExtendDomain));
                subAccountDetailExtendService.create(subAccountDetailExtendDomain);
            } else {
                throw new ApiException(ErrorCodeEnum.JsonError, "业务类型不匹配");
            }
            SubAccountDetailDTO subAccountDetailDTO = new SubAccountDetailDTO();
            subAccountDetailDTO.setSubAccountDetailDomain(subAccountDetailDomain);
            return subAccountDetailDTO;
        } catch (Exception e) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "账单明细操作异常");
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    private void updateBillAccountForPaid(SubAccountDetailForPaidDTO subAccountDetailForPaidDTO) {
        Lock lock = this.billAccountLock;
        try {
            lock.lock();
            SubAccountDetailDomain subAccountDetailDomain = this.find(subAccountDetailForPaidDTO.getDetailId());
            //支付成功
            if (SubAccountDetailStatusEnum.Finished.getValue().equals(subAccountDetailDomain.getStatus())) {
                //判断重复支付
                logger.error("子账户明细已经支付，不能重复修改,detailId:{}", subAccountDetailForPaidDTO.getDetailId());
                throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经支付，不能重复修改状态");
            }
            //订单支付
            if (subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.TradePay.getValue())) {
                //红包支付
                SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = subAccountDetailPaymentService.find(subAccountDetailForPaidDTO.getPaymentId());
                if (subAccountDetailPaymentDomain.getType().equals(SubAccountDetailPaymentTypeEnum.RedPacket.getValue())) {
                    RedPacketAccountDetailAddDTO accountDetailAddDTO = new RedPacketAccountDetailAddDTO();
                    accountDetailAddDTO.setAccountId(subAccountDetailDomain.getAccountId());
                    accountDetailAddDTO.setAmountPaid(subAccountDetailPaymentDomain.getAmount());
                    accountDetailAddDTO.setOperator(subAccountDetailForPaidDTO.getOperator());
                    accountDetailAddDTO.setOperatorPhone("");
                    //        accountDetailAddDTO.setOperatorType();
                    accountDetailAddDTO.setRemark("抵扣订单号为" + subAccountDetailDomain.getTradeSn() + "的支付金额");
                    accountDetailAddDTO.setTradeSn(subAccountDetailDomain.getTradeSn());
                    accountDetailAddDTO.setType(SubAccountDetailTypeEnum.PayDeduction.getValue());
                    accountDetailAddDTO.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
                    this.createRedPacketAccountDetail(accountDetailAddDTO);
                } else {
                    SubAccountDetailDomain newSubAccountDetailDomain1 = new SubAccountDetailDomain();
                    newSubAccountDetailDomain1.setId(subAccountDetailDomain.getId());
                    newSubAccountDetailDomain1.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
                    newSubAccountDetailDomain1.setPaymentDate(subAccountDetailForPaidDTO.getPaymentDate());
                    this.merge(newSubAccountDetailDomain1);
                    try {
                        //通知订单--支付成功
                        //orderPaymentService.orderPaidSuccessForWebhook(subAccountDetailDomain.getTradeSn(), subAccountDetailDomain.getSn(), subAccountDetailPaymentDomain.getType(), subAccountDetailForPaidDTO.getPaymentDate(), Constant.SYSTEM_USER_ID);
                    } catch (Exception e) {
                        logger.error("更新订单失败:" + e);
//                        throw e;
                        //throw new ApiException(ErrorCodeEnum.ParamsError, "更新订单失败:" + e.toString());
                    }
                    SubAccountDetailDTO subAccountDetailDTO = this.findByDetailId(subAccountDetailDomain.getId());
                    //订单收入--卖家
                    SubAccountForOrderDTO subAccountForOrderDTO = new SubAccountForOrderDTO();
                    subAccountForOrderDTO.setSellerAccountId(subAccountDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailPaymentExtendDomain().getSellerAccountId());
                    subAccountForOrderDTO.setAmountPaid(subAccountDetailPaymentDomain.getAmount());
                    subAccountForOrderDTO.setOperator(subAccountDetailForPaidDTO.getOperator());
                    subAccountForOrderDTO.setPaymentDate(subAccountDetailDomain.getPaymentDate());
                    subAccountForOrderDTO.setTradeSn(subAccountDetailDomain.getTradeSn());
                    this.incomeBalanceAccount(subAccountForOrderDTO);
                }
            } else if(subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKOrderPay.getValue())
                    || subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKShuttleOrderPay.getValue())
                    || subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKMallOrderPay.getValue())) {
                //直客订单支付成功
                this.zkPaySuccess(subAccountDetailDomain, subAccountDetailForPaidDTO);
            } else if(subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.MiniRiskIncome.getValue())){
                //小程序订单支付成功
                miniRiskSuccess(subAccountDetailForPaidDTO);
            } else {
                throw new ApiException(ErrorCodeEnum.JsonError, "业务类型不匹配");
            }
        } catch (Exception e) {
            logger.error("账单明细操作异常:" + e);
            throw e;
            //throw new ApiException(ErrorCodeEnum.ParamsError, "账单明细操作异常");
        } finally {
            lock.unlock();
        }

    }

    private void miniRiskSuccess(SubAccountDetailForPaidDTO subAccountDetailForPaidDTO){

        SubAccountDetailDomain subAccountDetailDomain = subAccountDetailService.find(subAccountDetailForPaidDTO.getDetailId());
        subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
        subAccountDetailDomain.setPaymentDate(new Date());
        subAccountDetailService.update(subAccountDetailDomain);

    }

    /**
     * 1.更新买家支付单据明细状态
     * 2.为卖家创建收入单据
     * 3.更新卖家余额账户金额
     * 4.通知直客系统支付成功
     * @throws ApiException
     */
    private void zkPaySuccess(SubAccountDetailDomain buyerDetail, SubAccountDetailForPaidDTO paidDTO) throws ApiException {
        logger.info("直客订单回写", JsonUtils.toJsonString(paidDTO));
        //1.更新买家支付单据明细状态
        SubAccountDetailDomain updateBuyerDetail = new SubAccountDetailDomain();
        updateBuyerDetail.setId(buyerDetail.getId());
        updateBuyerDetail.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
        updateBuyerDetail.setPaymentDate(paidDTO.getPaymentDate());
        this.merge(updateBuyerDetail);

        //查询卖家账户
        SubAccountDetailDTO buyerDetailDTO = this.findByDetailId(buyerDetail.getId());
        SubAccountDetailPaymentExtendDomain buyerDetailExtend = buyerDetailDTO.getSubAccountDetailExtendDTO().getSubAccountDetailPaymentExtendDomain();
        if(buyerDetailExtend == null) {
            throw new ApiException("支付扩展信息不存在");
        }
        SubAccountBaseDomain sellerAccount = subAccountBaseService.findAccountByAccountIdWithType(buyerDetailExtend.getSellerAccountId(), SubAccountTypeEnum.BalanceAccount.getValue());
        if(sellerAccount == null) {
            throw new ApiException("卖家账户不存在");
        }

        //2.为卖家创建收入单据
        BigDecimal originalAmount = sellerAccount.getBalance();//原始金额
        BigDecimal changedAmount = originalAmount.add(buyerDetail.getAmountPaid());//变更后金额
        SubAccountDetailDomain sellerDetail = new SubAccountDetailDomain();
        sellerDetail.setAccountId(sellerAccount.getAccountId());
        sellerDetail.setSubAccountId(sellerAccount.getSubAccountId());
        sellerDetail.setRemark("订单收入到账：订单号为" + buyerDetail.getTradeSn() + "的订单收入到账");
        if(buyerDetail.getType().equals(SubAccountDetailTypeEnum.ZKOrderPay.getValue())){
            sellerDetail.setType(SubAccountDetailTypeEnum.ZKOrderIncome.getValue());
        }else if(buyerDetail.getType().equals(SubAccountDetailTypeEnum.ZKShuttleOrderPay.getValue())){
            sellerDetail.setType(SubAccountDetailTypeEnum.ZKShuttleOrderIncome.getValue());
        }else if(buyerDetail.getType().equals(SubAccountDetailTypeEnum.ZKMallOrderPay.getValue())){
            sellerDetail.setType(SubAccountDetailTypeEnum.ZKMallOrderIncome.getValue());
        }
        sellerDetail.setTradeSn(buyerDetail.getTradeSn());
        sellerDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
//        sellerDetail.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
        sellerDetail.setAmountPaid(buyerDetail.getAmountPaid());
        sellerDetail.setOperator(paidDTO.getOperator());
//        sellerDetail.setSign(CommOperatorSymbolEnum.Add.getValue());
        if(1 == buyerDetailExtend.getNeedFreezeAfterSuccess()) {
            sellerDetail.setStatus(SubAccountDetailStatusEnum.Freeze.getValue());
        } else {
            sellerDetail.setStatus(SubAccountDetailStatusEnum.Received.getValue());
        }
        sellerDetail.setPaymentDate(paidDTO.getPaymentDate());
        sellerDetail.setOriginalAmount(originalAmount);
        sellerDetail.setChangedAmount(changedAmount);
        sellerDetail.setRelaDetailId(buyerDetail.getId());
        this.create(sellerDetail);

        //3.更新卖家余额账户金额
        SubAccountBaseDomain newSellerAccount = new SubAccountBaseDomain();
        newSellerAccount.setId(sellerAccount.getId());
        newSellerAccount.setBalance(changedAmount);
        if(1 == buyerDetailExtend.getNeedFreezeAfterSuccess()) {
            BigDecimal unUsableBalance = sellerAccount.getUnusableBalance().add(buyerDetail.getAmountPaid());//不可用金额
            newSellerAccount.setUnusableBalance(unUsableBalance);
        } else {
            BigDecimal usableBalance = sellerAccount.getUsableBalance().add(buyerDetail.getAmountPaid());
            newSellerAccount.setUsableBalance(usableBalance);
        }
        subAccountBaseService.merge(newSellerAccount);

        //4.通知直客系统支付成功
        TradePaySuccessDTO successDTO = new TradePaySuccessDTO();
        successDTO.setPaymentTime(DateUtil.format(DateUtil.YYYY_MM_DD_HH_MM_SS, paidDTO.getPaymentDate()));
        successDTO.setPaymentType(paidDTO.getPaymentType());
        successDTO.setPaidAmount(buyerDetail.getAmountPaid());
        successDTO.setSubAccountDetailSn(buyerDetail.getSn());
        successDTO.setSellerSubAccountDetailSn(sellerDetail.getSn());
        successDTO.setSequenceSn(paidDTO.getSn());
        successDTO.setIsSharePay(paidDTO.getIsSharePay());
        List<PaymentItemDTO> itemList = new ArrayList<>();
        List<SubAccountDetailPayItemsExtendDomain> payItemList = buyerDetailDTO.getSubAccountDetailExtendDTO().getPayItemsExtendDomainList();
        for(SubAccountDetailPayItemsExtendDomain payItem : payItemList) {
            PaymentItemDTO itemDTO = new PaymentItemDTO();
            itemDTO.setAmount(payItem.getAmount());
            itemDTO.setOrderType(payItem.getOrderType());
            itemDTO.setTradeSn(payItem.getTradeSn());
            itemDTO.setRemark(payItem.getRemark());
            itemList.add(itemDTO);
        }
        successDTO.setItemList(itemList);

        try {
            xieHuaPayCallBack.xiehuaPaySuccess(successDTO);
            logger.info("直客订单回写账户回写成功");
        } catch (Exception e) {
            logger.error("直客系统订单支付成功回写异常", e);
        }
    }

    /**
     *  1.更新卖家退款单状态
     *  2.更新卖家不可用余额，金额判断，可能需要解冻部分金额
     *  3.创建买家退款收入账单
     *  4.通知直客系统退款成功
     * @param serllerRefundDetail
     * @param paidDTO
     */
    private void zkRefundSuccess(SubAccountDetailDomain serllerRefundDetail, SubAccountDetailForPaidDTO paidDTO) {
        logger.info("直客退款回写账户");
        if (SubAccountDetailStatusEnum.RefundSuccess.getValue().equals(serllerRefundDetail.getStatus())) {
            //判断重复支付
            logger.error("子账户明细已经退款，不能重复修改,detailId:{}", serllerRefundDetail.getId());
            throw new ApiException(ErrorCodeEnum.ParamsError, "子账户明细已经退款，不能重复修改状态");
        }
        //1.更新买家支付单据明细状态
        SubAccountDetailDomain updateSellerRefundDetail = new SubAccountDetailDomain();
        updateSellerRefundDetail.setId(serllerRefundDetail.getId());
        updateSellerRefundDetail.setStatus(SubAccountDetailStatusEnum.RefundSuccess.getValue());
        updateSellerRefundDetail.setPaymentDate(paidDTO.getPaymentDate());
        this.merge(updateSellerRefundDetail);

        //2.更新卖家不可用余额，金额判断，可能需要解冻部分金额
        SubAccountBaseDomain sellerAccount = subAccountBaseService.findAccountByAccountIdWithType(serllerRefundDetail.getAccountId(), SubAccountTypeEnum.BalanceAccount.getValue());
        if(sellerAccount == null) {
            throw new ApiException("卖家账户不存在");
        }
        SubAccountDetailDomain sellerIncomDetail = this.find(serllerRefundDetail.getRelaDetailId());
        if(sellerIncomDetail == null) {
            throw new ApiException("卖家收入账单不存在");
        }
        //卖家收入账单由已冻结变为已到账
        SubAccountDetailDomain sellerIncomDetailDomain = new SubAccountDetailDomain();
        sellerIncomDetailDomain.setId(sellerIncomDetail.getId());
        sellerIncomDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
        this.merge(sellerIncomDetailDomain);

        //收入金额
        BigDecimal incomeAmount = sellerIncomDetail.getAmountPaid();
        //实退金额
        BigDecimal refundAmount = serllerRefundDetail.getAmountPaid();

        SubAccountBaseDomain newSellerAccount = new SubAccountBaseDomain();
        newSellerAccount.setId(sellerAccount.getId());
        newSellerAccount.setUnusableBalance(sellerAccount.getUnusableBalance().subtract(incomeAmount));
        BigDecimal difference = incomeAmount.subtract(refundAmount);
        //退的金额比当时付的金额少，需要把这部分差额算入可用余额
        if(BigDecimalUtil.gt(difference, BigDecimal.ZERO)) {
            newSellerAccount.setUsableBalance(sellerAccount.getUsableBalance().add(difference));
        }
        newSellerAccount.setBalance(sellerAccount.getBalance().subtract(refundAmount));
        subAccountBaseService.merge(newSellerAccount);
        //账单变动金额
        updateSellerRefundDetail = new SubAccountDetailDomain();
        updateSellerRefundDetail.setId(serllerRefundDetail.getId());
        updateSellerRefundDetail.setOriginalAmount(sellerAccount.getBalance());
        updateSellerRefundDetail.setChangedAmount(newSellerAccount.getBalance());
        this.merge(updateSellerRefundDetail);

        //3.创建买家退款收入账单

        SubAccountDetailExtendDTO extendDTO = subAccountDetailExtendService.findByDetailId(serllerRefundDetail.getId());
        SubAccountDetailPaymentExtendDomain paymentExtend = extendDTO.getSubAccountDetailPaymentExtendDomain();

        SubAccountBaseDomain buyerAccount = subAccountBaseService.findAccountByAccountIdWithType(paymentExtend.getBuyerAccountId(), SubAccountTypeEnum.BillAccount.getValue());
        SubAccountDetailDomain buyerIncomeDetail = new SubAccountDetailDomain();
        buyerIncomeDetail.setAccountId(buyerAccount.getAccountId());
        buyerIncomeDetail.setSubAccountId(buyerAccount.getSubAccountId());
        buyerIncomeDetail.setRemark("退款收入到账：订单号为" + sellerIncomDetail.getTradeSn() + "的退款收入到账");

        if(SubAccountDetailTypeEnum.ZKRefundPay.getValue().equals(serllerRefundDetail.getType())) {
            buyerIncomeDetail.setType(SubAccountDetailTypeEnum.ZKRefundIncome.getValue());
        } else if(SubAccountDetailTypeEnum.ZKAddServiceRefundPay.getValue().equals(serllerRefundDetail.getType())) {
            buyerIncomeDetail.setType(SubAccountDetailTypeEnum.ZKAddServiceRefundIncome.getValue());
        } else if(SubAccountDetailTypeEnum.ZKShuttleRefundPay.getValue().equals(serllerRefundDetail.getType())) {
            buyerIncomeDetail.setType(SubAccountDetailTypeEnum.ZKShuttleOrderIncome.getValue());
        } else if(SubAccountDetailTypeEnum.ZKMallRefundPay.getValue().equals(serllerRefundDetail.getType())) {
            buyerIncomeDetail.setType(SubAccountDetailTypeEnum.ZKMallRefundIncome.getValue());
        }else {
            throw new ApiException("直客订单退款类型错误");
        }
        buyerIncomeDetail.setTradeSn(sellerIncomDetail.getTradeSn());
        buyerIncomeDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeRefund.getValue());
//        buyerIncomeDetail.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
        buyerIncomeDetail.setAmountPaid(serllerRefundDetail.getAmountPaid());
        buyerIncomeDetail.setOperator(paidDTO.getOperator());
//        buyerIncomeDetail.setSign(CommOperatorSymbolEnum.Add.getValue());
        buyerIncomeDetail.setStatus(SubAccountDetailStatusEnum.Received.getValue());
        buyerIncomeDetail.setPaymentDate(paidDTO.getPaymentDate());
        buyerIncomeDetail.setOriginalAmount(new BigDecimal(0));
        buyerIncomeDetail.setChangedAmount(new BigDecimal(0));
        this.create(buyerIncomeDetail);

        //4.通知直客系统退款成功
        TradePaySuccessDTO successDTO = new TradePaySuccessDTO();
        successDTO.setPaymentTime(DateUtil.format(DateUtil.YYYY_MM_DD_HH_MM_SS, paidDTO.getPaymentDate()));
        successDTO.setPaymentType(paidDTO.getPaymentType());
        successDTO.setPaidAmount(serllerRefundDetail.getAmountPaid());
        successDTO.setSubAccountDetailSn(serllerRefundDetail.getSn());
        successDTO.setSequenceSn(paidDTO.getSn());
        List<PaymentItemDTO> itemList = new ArrayList<>();
        List<SubAccountDetailPayItemsExtendDomain> payItemList = extendDTO.getPayItemsExtendDomainList();
        for(SubAccountDetailPayItemsExtendDomain payItem : payItemList) {
            PaymentItemDTO itemDTO = new PaymentItemDTO();
            itemDTO.setAmount(payItem.getAmount());
            itemDTO.setOrderType(payItem.getOrderType());
            itemDTO.setTradeSn(payItem.getTradeSn());
            itemList.add(itemDTO);
        }
        successDTO.setItemList(itemList);
        try {
            xieHuaPayCallBack.xiehuaPaySuccess(successDTO);
        } catch (Exception e) {
            logger.error("直客系统退款支付成功回写异常", e);
        }


        logger.info("直客退款回写账户成功");
    }

    //======================================================= 账单账户 end ===================================================

    //==================================================================第三方支付账户 start ====================================
    public SubAccountDetailDTO createAccountDetail(RedPacketAccountDetailAddDTO accountDetailAddDTO) {
        AssertUtil.notNull(accountDetailAddDTO);
        AssertUtil.notNull(accountDetailAddDTO.getAccountId());
        AssertUtil.notNull(accountDetailAddDTO.getType());
        //目前余额账户，业务规定只支持：提现，订单收入，提现返还失败
        if (!accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradeRewards.getValue())
                && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.PayDeduction.getValue())
                && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.ExpireInvalid.getValue())
                && !accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.SystemProvide.getValue())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "业务类型不匹配");
        }
        BigDecimal amount = accountDetailAddDTO.getAmountPaid();
        if (BigDecimalUtil.eq(amount, BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
        }
        //查询子账户基础信息
        SubAccountDTO subAccountDTO = subAccountService.findRedPacketByAccountId(accountDetailAddDTO.getAccountId());
        /*if (AccountStatusEnum.Invalid.getValue().equals(subAccountDTO.getSubAccountBaseDomain().getStatus())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "账户无效");
        }*/
        //创建账户交易明细
        SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
        if (accountDetailAddDTO.getType().equals(SubAccountDetailTypeEnum.TradeRewards.getValue())) {
            AssertUtil.notBlank(accountDetailAddDTO.getTradeSn());
            subAccountDetailDomain.setAccountId(subAccountDTO.getSubAccountBaseDomain().getAccountId());
            subAccountDetailDomain.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
            subAccountDetailDomain.setRemark(accountDetailAddDTO.getRemark());
            subAccountDetailDomain.setType(accountDetailAddDTO.getType());
            subAccountDetailDomain.setTradeSn(accountDetailAddDTO.getTradeSn());
            subAccountDetailDomain.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
//            subAccountDetailDomain.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
            subAccountDetailDomain.setAmountPaid(accountDetailAddDTO.getAmountPaid());
            subAccountDetailDomain.setOperator(accountDetailAddDTO.getOperator());
            subAccountDetailDomain.setOperatorType(accountDetailAddDTO.getOperatorType());
            subAccountDetailDomain.setOperatorPhone(accountDetailAddDTO.getOperatorPhone());
//            subAccountDetailDomain.setSign(CommOperatorSymbolEnum.Add.getValue());
            subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Received.getValue());
            BigDecimal tempAmount = null;
            /*if (CommOperatorSymbolEnum.Add.getValue().equals(subAccountDetailDomain.getSign())) {
                tempAmount = subAccountDetailDomain.getAmountPaid();
            } else if (CommOperatorSymbolEnum.Subtract.getValue().equals(subAccountDetailDomain.getSign())) {
                tempAmount = subAccountDetailDomain.getAmountPaid().multiply(new BigDecimal("-1"));
            }*/
            BigDecimal subAccountBalance = subAccountDTO.getSubAccountBaseDomain().getBalance();
            subAccountDetailDomain.setOriginalAmount(subAccountBalance);
            subAccountDetailDomain.setChangedAmount(subAccountBalance.add(tempAmount));
            this.create(subAccountDetailDomain);
            //更新账户金额，支付变化的金额一定是正数
            SubAccountBaseDTO subAccountBaseDTO = new SubAccountBaseDTO();
            subAccountBaseDTO.setSubAccountId(subAccountDTO.getSubAccountBaseDomain().getSubAccountId());
            subAccountBaseDTO.setBalance(subAccountDetailDomain.getChangedAmount());
            subAccountBaseDTO.setVersion(subAccountDTO.getSubAccountBaseDomain().getVersion());
            subAccountBaseService.updateBalanceForDetail(subAccountBaseDTO);
        } else {
            throw new ApiException(ErrorCodeEnum.JsonError, "业务类型不匹配");
        }
        SubAccountDetailDTO subAccountDetailDTO = new SubAccountDetailDTO();
        subAccountDetailDTO.setSubAccountDetailDomain(subAccountDetailDomain);
        return subAccountDetailDTO;
    }
    //==================================================================第三方支付账户 end ====================================


    //======================================================= 交易系统接口 start ===================================================



    @Override
    public SubAccountDetailDTO createBillDetailForOrder(SubAccountDetailAddForOrderDTO subAccountDetailAddForOrderDTO) {
        BillAccountDetailAddDTO billAccountDetailAddDTO = new BillAccountDetailAddDTO();
        billAccountDetailAddDTO.setBuyerAccountId(subAccountDetailAddForOrderDTO.getBuyerAccountId());
        billAccountDetailAddDTO.setSellerAccountId(subAccountDetailAddForOrderDTO.getSellerAccountId());
        billAccountDetailAddDTO.setAmountPaid(subAccountDetailAddForOrderDTO.getAmountPaid());
        billAccountDetailAddDTO.setOperator(subAccountDetailAddForOrderDTO.getOperator());
        billAccountDetailAddDTO.setRemark(subAccountDetailAddForOrderDTO.getRemark());
        billAccountDetailAddDTO.setTradeSn(subAccountDetailAddForOrderDTO.getTradeSn());
        billAccountDetailAddDTO.setType(SubAccountDetailTypeEnum.TradePay.getValue());
        billAccountDetailAddDTO.setMaxRedPacketAmount(subAccountDetailAddForOrderDTO.getMaxRedPacketAmount());
        SubAccountDetailDTO subAccountDetailDTO = this.createBillAccountDetail(billAccountDetailAddDTO);
        return subAccountDetailDTO;
    }

    @Override
    public void createRedPacketDetailForOrder(RedPacketAccountDetailAddForOrderDTO accountDetailAddForOrderDTO) {
        RedPacketAccountDetailAddDTO accountDetailAddDTO = new RedPacketAccountDetailAddDTO();
        accountDetailAddDTO.setAccountId(accountDetailAddForOrderDTO.getAccountId());
        accountDetailAddDTO.setAmountPaid(accountDetailAddForOrderDTO.getAmountPaid());
        accountDetailAddDTO.setOperator(accountDetailAddForOrderDTO.getOperator());
        accountDetailAddDTO.setOperatorPhone("");
//        accountDetailAddDTO.setOperatorType(accountDetailAddForOrderDTO.getOperator());
        accountDetailAddDTO.setRemark(accountDetailAddForOrderDTO.getRemark());
        accountDetailAddDTO.setTradeSn(accountDetailAddForOrderDTO.getTradeSn());
        accountDetailAddDTO.setType(SubAccountDetailTypeEnum.TradeRewards.getValue());
        accountDetailAddDTO.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
        this.createRedPacketAccountDetail(accountDetailAddDTO);
    }

    @Override
    public void createRedPacketDetailForShopCar(RedPacketAccountDetailAddForShopCarDTO dto) {
        RedPacketAccountDetailAddDTO accountDetailAddDTO = new RedPacketAccountDetailAddDTO();
        accountDetailAddDTO.setAccountId(dto.getAccountId());
        accountDetailAddDTO.setAmountPaid(dto.getAmountPaid());
        accountDetailAddDTO.setOperator(dto.getOperator());
        accountDetailAddDTO.setOperatorPhone("");
//        accountDetailAddDTO.setOperatorType(accountDetailAddForOrderDTO.getOperator());
        accountDetailAddDTO.setRemark(dto.getRemark());
        accountDetailAddDTO.setType(SubAccountDetailTypeEnum.AddShopCar.getValue());
        accountDetailAddDTO.setTradeSn(dto.getTradeSn());
        accountDetailAddDTO.setTradeSource(SubAccountDetailTradeSourceEnum.ProductShopCar.getValue());
        this.createRedPacketAccountDetail(accountDetailAddDTO);
    }

    @Override
    public void createRedPacketDetailForExpire(RedPacketAccountDetailAddForOrderDTO redPacketAccountDetailAddForOrderDTO) {
        redPacketAccountDetailAddForOrderDTO.getAmountPaid();
        RedPacketAccountDetailAddDTO accountDetailAddDTO = new RedPacketAccountDetailAddDTO();
        accountDetailAddDTO.setAccountId(redPacketAccountDetailAddForOrderDTO.getAccountId());
        accountDetailAddDTO.setAmountPaid(redPacketAccountDetailAddForOrderDTO.getAmountPaid());
        accountDetailAddDTO.setOperator(redPacketAccountDetailAddForOrderDTO.getOperator());
        accountDetailAddDTO.setOperatorPhone("");
//        accountDetailAddDTO.setOperatorType();
        accountDetailAddDTO.setRemark(redPacketAccountDetailAddForOrderDTO.getRemark());
        accountDetailAddDTO.setTradeSn(redPacketAccountDetailAddForOrderDTO.getTradeSn());
        accountDetailAddDTO.setType(SubAccountDetailTypeEnum.ExpireInvalid.getValue());
        accountDetailAddDTO.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
        this.createRedPacketAccountDetail(accountDetailAddDTO);
    }

    @Override
    public void createRedPacketDetailForSystemProvide(RedPacketAccountDetailAddForOrderDTO redPacketAccountDetailAddForOrderDTO) {
        redPacketAccountDetailAddForOrderDTO.getAmountPaid();
        RedPacketAccountDetailAddDTO accountDetailAddDTO = new RedPacketAccountDetailAddDTO();
        accountDetailAddDTO.setAccountId(redPacketAccountDetailAddForOrderDTO.getAccountId());
        accountDetailAddDTO.setAmountPaid(redPacketAccountDetailAddForOrderDTO.getAmountPaid());
        accountDetailAddDTO.setOperator(redPacketAccountDetailAddForOrderDTO.getOperator());
        accountDetailAddDTO.setOperatorPhone("");
//        accountDetailAddDTO.setOperatorType();
        accountDetailAddDTO.setRemark(redPacketAccountDetailAddForOrderDTO.getRemark());
        accountDetailAddDTO.setTradeSn(redPacketAccountDetailAddForOrderDTO.getTradeSn());
        accountDetailAddDTO.setType(SubAccountDetailTypeEnum.SystemProvide.getValue());
        accountDetailAddDTO.setTradeSource(SubAccountDetailTradeSourceEnum.AccountDetail.getValue());
        this.createRedPacketAccountDetail(accountDetailAddDTO);
    }

    //======================================================= 交易系统接口 end ==================================
    //==========================================================老系统需求=========
    @Override
    public Pager<SubAccountDetailDomain> findForOrderByAdminQueryForPager(QueryCondition queryCondition) throws ApiException {
        return subAccountDetailDao.findForPager(queryCondition, "findForOrderByAdminQueryForPager");
    }


    //==========================================老系统需求=====================


    //=======================================F模式押金收入明细
    @Override
    public Pager<SubAccountDetailDomain> findForDepositByAdminQueryForPager(QueryCondition queryCondition) {
        return subAccountDetailDao.findForPager(queryCondition, "findForDepositByAdminQueryForPager");
    }


    @Override
    public BigDecimal getZKYesterdayTransactionAmount(Long shopId) {
        AssertUtil.notNull(shopId, "shopid不能为空");
        SubAccountBaseDomain accountBaseDomain = subAccountBaseService.findAccountByShopIdWithType(shopId, SubAccountTypeEnum.BalanceAccount.getValue());
        AssertUtil.notNull(accountBaseDomain, "买家账户不存在");

        Date endDate = DateUtil.getCurrentData();
        Date startDate = DateUtil.getDateAddDay(endDate, -1);

        QueryCondition condition = new QueryCondition();
        condition.addConditionByColName("account_id", QueryOperatorEnum.eq, accountBaseDomain.getAccountId());
        condition.addConditionByColName("sub_account_id", QueryOperatorEnum.eq, accountBaseDomain.getSubAccountId());
        condition.addConditionByColName("status", QueryOperatorEnum.in, new Object[]{
                SubAccountDetailStatusEnum.Received.getValue(),
                SubAccountDetailStatusEnum.Freeze.getValue()
        });
        condition.addConditionByColName("type", QueryOperatorEnum.eq, SubAccountDetailTypeEnum.ZKOrderIncome.getValue());
        condition.addConditionByColName("create_time", QueryOperatorEnum.gte, startDate);
        condition.addConditionByColName("create_time", QueryOperatorEnum.lt, endDate);
        condition.setPer_page(10000);
        Pager<SubAccountDetailDomain> pager = this.findForPager(condition);
        List<SubAccountDetailDomain> list = pager.getPageItems();
        if(CollectionUtils.isEmpty(list)) {
            return BigDecimal.ZERO;
        }
        return list.stream().map(SubAccountDetailDomain::getAmountPaid).reduce(BigDecimal.ZERO, (x, y) -> {return x.add(y);});
    }

    @Override
    public List<ZKLedgerSyncDTO> syncZKLedgerData(Map<String, Object> map) {
        return subAccountDetailDao.syncZKLedgerData(map);
    }
}
