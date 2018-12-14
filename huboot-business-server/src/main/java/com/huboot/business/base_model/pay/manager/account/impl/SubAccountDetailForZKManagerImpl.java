package com.huboot.business.base_model.pay.manager.account.impl;

import com.huboot.business.base_model.pay.domain.account.*;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPayItemsExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPaymentExtendDomain;
import com.huboot.business.base_model.pay.dto.account.PaymentItemDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailDTO;
import com.huboot.business.base_model.pay.dto.account.ZKLedgerSyncDTO;
import com.huboot.business.base_model.pay.enums.*;
import com.huboot.business.base_model.pay.manager.account.ISubAccountDetailForZKManager;
import com.huboot.business.base_model.pay.service.account.*;
import com.huboot.business.base_model.pay.service.account.serial.ISerialNumberService;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailAddForZKOrderVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailAddForZKRefundItemVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailAddForZKRefundVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailForPayVO;
import com.huboot.business.common.utils.BigDecimalUtil;
import com.huboot.business.common.utils.DateUtil;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.AssertUtil;
import com.huboot.business.mybatis.ErrorCodeEnum;
import com.pingplusplus.model.Refund;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
@Service
public class SubAccountDetailForZKManagerImpl implements ISubAccountDetailForZKManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISubAccountDetailService subAccountDetailService;
    @Autowired
    private ISubAccountBaseService subAccountBaseService;
    @Autowired
    private ISerialNumberService serialNumberService;
    @Autowired
    private ISubAccountDetailExtendService subAccountDetailExtendService;

    @Autowired
    private IAccountBaseService accountBaseService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private ISubAccountDetailPaymentService subAccountDetailPaymentService;
    @Autowired
    private ISubAccountDetailPaymentPingppService subAccountDetailPaymentPingppService;


    private Lock zkPayLock = new ReentrantLock();//账单
    private Lock zkrefundlock = new ReentrantLock();
    private Lock unFreezeLock = new ReentrantLock();


    /**
     * 如果直客账户没有初始化成功，此时再创见一次
     * @param userGid
     * @param companyUid
     * @return
     * @throws ApiException
     */
    @Transactional
    @Override
    public Map<String, String> getAccountIdByCompanyUidForZK(String userGid, String companyUid) throws ApiException {
        //
        //ShopDomain sellerShop = shopService.findByCompanyUid(companyUid);
       /* if(sellerShop == null) {
            return null;
        }*/
        SubAccountBaseDomain sellerSubAccountBaseDomain = subAccountBaseService.findAccountByShopIdWithType(1L, SubAccountTypeEnum.BalanceAccount.getValue());
        if(sellerSubAccountBaseDomain == null) {
            return null;
        }

        AccountBaseDomain accountBaseDomain = new AccountBaseDomain();
        accountBaseDomain.setUserGid(userGid);
        AccountBaseDomain check = accountBaseService.getSingleByBeanProp(accountBaseDomain);
        if(check == null) {
            AccountDomain accountDomain = new AccountDomain();
            Long id = accountService.create(accountDomain);
            accountBaseDomain.setAccountId(id);
            accountBaseDomain.setStatus(AccountStatusEnum.Valid.getValue());
            accountBaseDomain.setTotalAssets(BigDecimal.ZERO);
            accountBaseService.create(accountBaseDomain);
        } else {
            accountBaseDomain = check;
        }

        SubAccountBaseDomain zkSubAccount = new SubAccountBaseDomain();
        zkSubAccount.setType(SubAccountTypeEnum.BillAccount.getValue());
        zkSubAccount.setAccountId(accountBaseDomain.getAccountId());
        zkSubAccount.setBelongToAccountId(sellerSubAccountBaseDomain.getAccountId());
        SubAccountBaseDomain scheck = subAccountBaseService.getSingleByBeanProp(zkSubAccount);
        if(scheck == null) {
            SubAccountDomain subAccountDomain = new SubAccountDomain();
            Long id = subAccountService.create(subAccountDomain);
            zkSubAccount.setSubAccountId(id);
            zkSubAccount.setStatus(AccountStatusEnum.Valid.getValue());
            subAccountBaseService.create(zkSubAccount);
        }

        Map<String, String> map = new HashMap<>();
        map.put(userGid, accountBaseDomain.getAccountId().toString());
        map.put(companyUid, sellerSubAccountBaseDomain.getAccountId().toString());
        return map;
    }



    /*
    使用：在确认订单后，会先创建账单createDetailForZKOrder，返回账单编号。
    之后在收银台支付的时候，再调payForZK，返回ping++的charge，前端拿到这个charge再调起输入密码进行支付的框，完成支付
*/



    /**
     * 创建直客支付
     * @param vo
     * @return
     * @throws ApiException
     */
    @Override
    @Transactional
    public String createDetailForZKOrder(SubAccountDetailAddForZKOrderVO vo) throws ApiException {
        AssertUtil.notNull(vo.getSellerAccountId());
        AssertUtil.notNull(vo.getBuyerAccountId());
        AssertUtil.notNull(vo.getTradeSn());
        AssertUtil.notNull(vo.getAmountPaid());

        BigDecimal amount = vo.getAmountPaid();
        if (BigDecimalUtil.eq(amount, BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "金额不能为0");
        }
        //查询买家子账户基础信息
        SubAccountBaseDomain buyerAccount = new SubAccountBaseDomain();
        buyerAccount.setType(SubAccountTypeEnum.BillAccount.getValue());
        buyerAccount.setAccountId(vo.getBuyerAccountId());
        buyerAccount.setBelongToAccountId(vo.getSellerAccountId());
        buyerAccount = subAccountBaseService.getSingleByBeanProp(buyerAccount);
        if(buyerAccount == null) {
            throw new ApiException("买家账户不存在");
        }
        zkPayLock.lock();
        try {
            SubAccountDetailDomain buyerDetail = new SubAccountDetailDomain();
            buyerDetail.setAccountId(buyerAccount.getAccountId());
            buyerDetail.setSubAccountId(buyerAccount.getSubAccountId());
           /* if(vo.getZKOrderType().equals(ZKTradeSubOrderTypeEnum.ZkRent.getValue())){
                buyerDetail.setType(SubAccountDetailTypeEnum.ZKOrderPay.getValue());
            }else if(vo.getZKOrderType().equals(ZKTradeSubOrderTypeEnum.ShuttleService.getValue())){
                buyerDetail.setType(SubAccountDetailTypeEnum.ZKShuttleOrderPay.getValue());
            }else if(vo.getZKOrderType().equals(ZKTradeSubOrderTypeEnum.ZKMall.getValue())){
                buyerDetail.setType(SubAccountDetailTypeEnum.ZKMallOrderPay.getValue());
            }*/
            buyerDetail.setTradeSn(vo.getTradeSn());
            buyerDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
            buyerDetail.setOperator(vo.getOperator());
            buyerDetail.setSign(CommOperatorSymbolEnum.Subtract.getValue());
            buyerDetail.setStatus(SubAccountDetailStatusEnum.WaitForPay.getValue());

            //先查询是否已经创建过交易明细
            SubAccountDetailDomain check = subAccountDetailService.getSingleByBeanProp(buyerDetail);
            if(check != null) {
                throw new ApiException("已存在待支付明细，不能再次创建待支付明细");
            }

            //创建账户交易明细
            buyerDetail.setRemark("订单金额");
            buyerDetail.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
            buyerDetail.setAmountPaid(vo.getAmountPaid());
            subAccountDetailService.create(buyerDetail);

            //创建扩展信息
            SubAccountDetailExtendDomain buyerDetailExtend = new SubAccountDetailExtendDomain();
            buyerDetailExtend.setSubAccountDetailId(buyerDetail.getId());
            buyerDetailExtend.setSubAccountId(buyerDetail.getSubAccountId());
            buyerDetailExtend.setAccountId(buyerDetail.getAccountId());
            //
            SubAccountDetailPaymentExtendDomain paymentExtend = new SubAccountDetailPaymentExtendDomain();
            paymentExtend.setSellerAccountId(vo.getSellerAccountId());
            paymentExtend.setNeedFreezeAfterSuccess(vo.getNeedFreezeAfterSuccess());
            buyerDetailExtend.setPaymentExtend(JsonUtils.toJsonString(paymentExtend));
            //
            if(!CollectionUtils.isEmpty(vo.getItemList())) {
                List<SubAccountDetailPayItemsExtendDomain> itemsExtendList = new ArrayList<>();
                for(PaymentItemDTO paymentItem : vo.getItemList()) {
                    SubAccountDetailPayItemsExtendDomain payItemsExtendDomain = new SubAccountDetailPayItemsExtendDomain();
                    payItemsExtendDomain.setAmount(paymentItem.getAmount());
                    payItemsExtendDomain.setOrderType(paymentItem.getOrderType());
                    payItemsExtendDomain.setTradeSn(paymentItem.getTradeSn());
                    payItemsExtendDomain.setRemark(paymentItem.getRemark());
                    itemsExtendList.add(payItemsExtendDomain);
                }
                buyerDetailExtend.setPayItemsExtend(JsonUtils.toJsonString(itemsExtendList));
            }

            subAccountDetailExtendService.create(buyerDetailExtend);
            return buyerDetail.getSn();
        } catch (Exception e) {
            logger.error("创建支付异常", e);
            throw new ApiException("创建支付异常");
        } finally {
            zkPayLock.unlock();
        }
    }

    @Override
    public SubAccountDetailForPayVO findForPayByDetailSnForZK(String userGid, String sn) throws ApiException {
        SubAccountDetailForPayVO vo = new SubAccountDetailForPayVO();
        AssertUtil.notBlank(sn);
        SubAccountDetailDTO subAccountDetailDTO = subAccountDetailService.findByDetailSn(sn);
        AssertUtil.notNull(subAccountDetailDTO.getSubAccountDetailDomain());
        AccountBaseDomain accountBaseDomain = new AccountBaseDomain();
        accountBaseDomain.setUserGid(userGid);
        accountBaseDomain = accountBaseService.getSingleByBeanProp(accountBaseDomain);
        if (!subAccountDetailDTO.getSubAccountDetailDomain().getAccountId().equals(accountBaseDomain.getAccountId())) {
            throw new ApiException("操作信息有误");
        }
        if (!SubAccountDetailStatusEnum.WaitForPay.getValue().equals(subAccountDetailDTO.getSubAccountDetailDomain().getStatus())) {
            throw new ApiException("支付信息已失效");
        }
        //BeanUtils.copyProperties(subAccountDetailDTO.getSubAccountDetailDomain(), vo);
        SubAccountDetailDomain subAccountDetailDomain = subAccountDetailDTO.getSubAccountDetailDomain();
        vo.setId(subAccountDetailDomain.getId().toString());
        vo.setAccountId(subAccountDetailDomain.getAccountId().toString());
        vo.setTradeSn(subAccountDetailDomain.getTradeSn());
        vo.setSubAccountId(subAccountDetailDomain.getSubAccountId().toString());
        vo.setAmountPaid(subAccountDetailDomain.getAmountPaid());
        vo.setSubAccountDetailSn(subAccountDetailDomain.getSn());
        vo.setRemark(subAccountDetailDomain.getRemark());
       /* if(subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKOrderPay.getValue())){
            vo.setzKOrderType(ZKTradeSubOrderTypeEnum.ZkRent.getValue());
        }else if(subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKShuttleOrderPay.getValue())){
            vo.setzKOrderType(ZKTradeSubOrderTypeEnum.ShuttleService.getValue());
        }else if(subAccountDetailDomain.getType().equals(SubAccountDetailTypeEnum.ZKMallOrderPay.getValue())){
            vo.setzKOrderType(ZKTradeSubOrderTypeEnum.ZKMall.getValue());
        }*/
        return vo;
    }

    /**
     * 直客退款
     * 1.通过BuyerSubAccountDetailSn找出支付流水的chargeId
     * 2.创建卖家退款账单和支付明细
     * 3.pingpp退款
     * 4.退款请求成功处理
     * @param vo
     * @return
     * @throws ApiException
     */
    @Transactional
    @Override
    public String createDetailForZKRefund(SubAccountDetailAddForZKRefundVO vo) throws ApiException {
        AssertUtil.notNull(vo.getSellerAccountId(), "卖家账户id不能为空");
        AssertUtil.notNull(vo.getBuyerSubAccountDetailSn(), "买家账户id不能为空");
        AssertUtil.notNull(vo.getSellerSubAccountDetailSn(), "卖家账单sn不能为空");
        AssertUtil.notNull(vo.getBuyerSubAccountDetailSn(), "买家账单sn不能为空");
        AssertUtil.notNull(vo.getRefundAmount(), "退款金额不能为空");
        if(CollectionUtils.isEmpty(vo.getItemVOList())) {
            throw new ApiException("退款项不能为空");
        }
        /**
         * 1.通过BuyerSubAccountDetailSn找出支付流水的chargeId
         */
        SubAccountDetailDomain buyerDetail = new SubAccountDetailDomain();
        buyerDetail.setAccountId(vo.getBuyerAccountId());
        buyerDetail.setSn(vo.getBuyerSubAccountDetailSn());
        buyerDetail = subAccountDetailService.getSingleByBeanProp(buyerDetail);
        if(buyerDetail == null) {
            throw new ApiException("买家账单明细不存在，sn=" + vo.getBuyerSubAccountDetailSn());
        }
        SubAccountDetailPaymentDomain buyerPayment = new SubAccountDetailPaymentDomain();
        buyerPayment.setSubAccountDetailId(buyerDetail.getId());
        buyerPayment.setSubAccountId(buyerDetail.getSubAccountId());
        buyerPayment.setStatus(SubAccountDetailPaymentStatusEnum.Paid.getValue());
        buyerPayment = subAccountDetailPaymentService.getFirstByBeanProp(buyerPayment);
        if(buyerPayment == null) {
            throw new ApiException("买家账单支付信息不存在");
        }
        SubAccountDetailPaymentPingppDomain buyerPingppDomain = subAccountDetailPaymentPingppService.findByPaymentId(buyerPayment.getId());
        if(buyerPingppDomain == null) {
            throw new ApiException("买家支付pingpp信息不存在");
        }
        //检查卖家detail
        SubAccountDetailDomain sellerDetail = new SubAccountDetailDomain();
        sellerDetail.setAccountId(vo.getSellerAccountId());
        sellerDetail.setSn(vo.getSellerSubAccountDetailSn());
        sellerDetail = subAccountDetailService.getSingleByBeanProp(sellerDetail);
        if(sellerDetail == null) {
            throw new ApiException("卖家账单明细不存在，sn=" + vo.getSellerSubAccountDetailSn());
        }
        if(!SubAccountDetailStatusEnum.Freeze.getValue().equals(sellerDetail.getStatus())) {
            throw new ApiException("卖家账单已经不是冻结状态，不能在线退款，sn=" + vo.getSellerSubAccountDetailSn());
        }
        if(BigDecimalUtil.gt(vo.getRefundAmount(), sellerDetail.getAmountPaid())) {
            throw new ApiException("退款金额超出已支付金额，sn=" + vo.getSellerSubAccountDetailSn());
        }
        /**
         * 2.创建卖家退款账单和支付明细
         */
        SubAccountBaseDomain sellerSubAccount = subAccountBaseService.findAccountByAccountIdWithType(vo.getSellerAccountId(), SubAccountTypeEnum.BalanceAccount.getValue());
        if(sellerSubAccount == null) {
            throw new ApiException("卖家余额账户不存在");
        }
        SubAccountDetailDomain sellerRefundDetail = new SubAccountDetailDomain();
        sellerRefundDetail.setAccountId(sellerSubAccount.getAccountId());
        sellerRefundDetail.setSubAccountId(sellerSubAccount.getSubAccountId());
        /*if(vo.getZKOrderType().equals(ZKTradeSubOrderTypeEnum.ShuttleService.getValue())){
            sellerRefundDetail.setType(SubAccountDetailTypeEnum.ZKShuttleRefundPay.getValue());
        }else if(vo.getZKOrderType().equals(ZKTradeSubOrderTypeEnum.ZkRent.getValue())){
            sellerRefundDetail.setType(SubAccountDetailTypeEnum.ZKRefundPay.getValue());
        }else if(vo.getZKOrderType().equals(ZKTradeSubOrderTypeEnum.ZKMall.getValue())){
            sellerRefundDetail.setType(SubAccountDetailTypeEnum.ZKMallRefundPay.getValue());
        }*/

        sellerRefundDetail.setTradeSn(sellerDetail.getTradeSn());
        sellerRefundDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeRefund.getValue());
        sellerRefundDetail.setSign(CommOperatorSymbolEnum.Subtract.getValue());
        sellerRefundDetail.setStatus(SubAccountDetailStatusEnum.WaitForRefund.getValue());
        //先查询是否已经创建过交易明细
        zkrefundlock.lock();
        try {
            SubAccountDetailDomain check = subAccountDetailService.getSingleByBeanProp(sellerRefundDetail);
            if(check != null) {
                throw new ApiException("已存在待退款明细，不能再次创建待退款明细");
            }
        }catch (Exception e) {
            throw e;
        } finally {
            zkrefundlock.unlock();
        }
        //创建账户交易明细
        String remark = "订单号为" + sellerRefundDetail.getTradeSn() + "的租车费用退款";
        sellerRefundDetail.setOperator(vo.getRefunder());
        sellerRefundDetail.setRemark(remark);
        sellerRefundDetail.setSn(serialNumberService.generate(SerialNumberTypeEnum.AccountDetail));
        sellerRefundDetail.setAmountPaid(vo.getRefundAmount());
        sellerRefundDetail.setRelaDetailId(sellerDetail.getId());
        Long sellerDetailId = subAccountDetailService.create(sellerRefundDetail);
        //创建扩展信息
        SubAccountDetailExtendDomain sellerDetailExtend = new SubAccountDetailExtendDomain();
        sellerDetailExtend.setAccountId(sellerSubAccount.getAccountId());
        sellerDetailExtend.setSubAccountId(sellerSubAccount.getSubAccountId());
        sellerDetailExtend.setSubAccountDetailId(sellerDetailId);
        SubAccountDetailPaymentExtendDomain paymentExtend = new SubAccountDetailPaymentExtendDomain();
        paymentExtend.setBuyerAccountId(vo.getBuyerAccountId());
        paymentExtend.setRelaSellerIncomeDetailSn(sellerDetail.getSn());
        sellerDetailExtend.setPaymentExtend(JsonUtils.toJsonString(paymentExtend));
        //创建退款项
        List<SubAccountDetailAddForZKRefundItemVO> itemVOList = vo.getItemVOList();
        List<SubAccountDetailPayItemsExtendDomain> refundItemList = new ArrayList<>();
        for(SubAccountDetailAddForZKRefundItemVO itemVO : itemVOList) {
            SubAccountDetailPayItemsExtendDomain itemsExtendDomain = new SubAccountDetailPayItemsExtendDomain();
            itemsExtendDomain.setOrderType(itemVO.getOrderType());
            itemsExtendDomain.setAmount(itemVO.getRefundAmount());
            itemsExtendDomain.setTradeSn(itemVO.getOrderSn());
            refundItemList.add(itemsExtendDomain);
        }
        sellerDetailExtend.setPayItemsExtend(JsonUtils.toJsonString(refundItemList));
        subAccountDetailExtendService.create(sellerDetailExtend);
        //创建支付流水
        SubAccountDetailPaymentDomain sellerRefundPayment = new SubAccountDetailPaymentDomain();
        sellerRefundPayment.setSubAccountId(sellerSubAccount.getSubAccountId());
        sellerRefundPayment.setSubAccountDetailId(sellerRefundDetail.getId());
        sellerRefundPayment.setSn(serialNumberService.generate(SerialNumberTypeEnum.Payment));
        sellerRefundPayment.setStatus(SubAccountDetailPaymentStatusEnum.Paying.getValue());
        sellerRefundPayment.setPlatform(buyerPayment.getPlatform());
        sellerRefundPayment.setMethod(buyerPayment.getMethod());
        sellerRefundPayment.setType(buyerPayment.getType());
        sellerRefundPayment.setAmount(vo.getRefundAmount());
        Long paymentId = subAccountDetailPaymentService.create(sellerRefundPayment);
        /**
         * 3.pingpp退款
         */
        Refund refund = subAccountDetailPaymentPingppService.createRefund(paymentId,
                sellerRefundPayment.getSn(), buyerPingppDomain.getPingppId(),
                remark, vo.getRefundAmount());
        /**
         * 4.退款请求失败处理
         */
        if(StringUtils.isNotEmpty(refund.getFailureCode())) {
            SubAccountDetailPaymentDomain failSellerRefundPayment = new SubAccountDetailPaymentDomain();
            failSellerRefundPayment.setId(paymentId);
            failSellerRefundPayment.setTransactionNo(refund.getFailureCode());
            failSellerRefundPayment.setStatus(SubAccountDetailPaymentStatusEnum.PayFail.getValue());
            subAccountDetailPaymentService.merge(failSellerRefundPayment);

            SubAccountDetailDomain failSellerRefundDetail = new SubAccountDetailDomain();
            failSellerRefundDetail.setId(sellerDetailId);
            failSellerRefundDetail.setStatus(SubAccountDetailStatusEnum.WaitFail.getValue());
            subAccountDetailService.merge(failSellerRefundDetail);
            return "";
        }
        return sellerRefundDetail.getSn();
    }

    /**
     *
     * @param detailSn
     * @throws ApiException
     */
    @Override
    @Transactional
    public void unFreezeDetailForZK(Long buyerAccountId, Long sellerAccountId, String detailSn) throws Exception {
        AssertUtil.notNull(buyerAccountId, "买家账户id不能为空");
        AssertUtil.notNull(sellerAccountId, "卖账户id不能为空");
        AssertUtil.notNull(detailSn, "账单编号不能为空");
        SubAccountDetailDomain sellerDetail = new SubAccountDetailDomain();
        sellerDetail.setAccountId(sellerAccountId);
        sellerDetail.setSn(detailSn);
        unFreezeLock.lock();
        try {
            sellerDetail = subAccountDetailService.getSingleByBeanProp(sellerDetail);
            if(sellerDetail == null) {
                throw new ApiException("账单不存在，sn=" + detailSn);
            }
            if(!SubAccountDetailStatusEnum.Freeze.getValue().equals(sellerDetail.getStatus())) {
                throw new ApiException("账单不是冻结状态，sn=" + detailSn);
            }
            SubAccountDetailDomain newSellerDetail = new SubAccountDetailDomain();
            newSellerDetail.setId(sellerDetail.getId());
            newSellerDetail.setStatus(SubAccountDetailStatusEnum.Received.getValue());
            subAccountDetailService.merge(newSellerDetail);

            SubAccountBaseDomain sellerAccount = subAccountBaseService.findAccountByAccountIdWithType(sellerAccountId, SubAccountTypeEnum.BalanceAccount.getValue());
            if(sellerAccount == null) {
                throw new ApiException("卖家账户不存在，sn=" + detailSn);
            }
            BigDecimal ubUseAmount = sellerAccount.getUnusableBalance().subtract(sellerDetail.getAmountPaid());
            BigDecimal useAmount = sellerAccount.getUsableBalance().add(sellerDetail.getAmountPaid());
            SubAccountBaseDomain newSellerAccount = new SubAccountBaseDomain();
            newSellerAccount.setId(sellerAccount.getId());
            newSellerAccount.setUsableBalance(useAmount);
            newSellerAccount.setUnusableBalance(ubUseAmount);
            subAccountBaseService.merge(newSellerAccount);
        } catch (Exception e) {
            throw e;
        } finally {
            unFreezeLock.unlock();
        }
    }

    /**
     * 同步直客业务台账数据
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<ZKLedgerSyncDTO> syncZKLedgerData(String startTime, String endTime) {
        Timestamp start = null;
        Timestamp end = null;
        try {
            start = new Timestamp(DateUtil.parse(startTime, DateUtil.YYYY_MM_DD_HH_MM_SS).getTime());
            end = new Timestamp(DateUtil.parse(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS).getTime());
        } catch (Exception e) {
            throw new ApiException("时间解析错误");
        }
        List<ZKLedgerSyncDTO> list = new ArrayList<>();

        Map<String, Object> incomeMap = new HashMap<>();
        incomeMap.put("detaiType", new Integer[]{
                SubAccountDetailTypeEnum.ZKOrderIncome.getValue(),
                SubAccountDetailTypeEnum.ZKShuttleOrderIncome.getValue(),
                SubAccountDetailTypeEnum.ZKMallOrderIncome.getValue()
        });
        incomeMap.put("tradeSource", SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
        incomeMap.put("detaiStatus", new Integer[]{SubAccountDetailStatusEnum.Received.getValue(), SubAccountDetailStatusEnum.Freeze.getValue()});
        incomeMap.put("startTime", start);
        incomeMap.put("endTime", end);
        List<ZKLedgerSyncDTO> incomeList = subAccountDetailService.syncZKLedgerData(incomeMap);
        for(ZKLedgerSyncDTO syncDTO : incomeList) {
            SubAccountDetailDomain buyerPayDetail = subAccountDetailService.find(syncDTO.getRelaDetailId());
            SubAccountDetailExtendDomain buyerPayExtend = new SubAccountDetailExtendDomain();
            buyerPayExtend.setAccountId(buyerPayDetail.getAccountId());
            buyerPayExtend.setSubAccountId(buyerPayDetail.getSubAccountId());
            buyerPayExtend.setSubAccountDetailId(buyerPayDetail.getId());
            buyerPayExtend = subAccountDetailExtendService.getSingleByBeanProp(buyerPayExtend);
            List<SubAccountDetailPayItemsExtendDomain> itemsExtendDomainList = JsonUtils.fromJsonToList(buyerPayExtend.getPayItemsExtend(), SubAccountDetailPayItemsExtendDomain.class);
            List<PaymentItemDTO> itemList = new ArrayList<>();
            for(SubAccountDetailPayItemsExtendDomain extendDomain : itemsExtendDomainList) {
                PaymentItemDTO dto = new PaymentItemDTO();
                dto.setOrderType(extendDomain.getOrderType());
                dto.setRemark(extendDomain.getRemark());
                dto.setTradeSn(extendDomain.getTradeSn());
                dto.setAmount(extendDomain.getAmount());
                itemList.add(dto);
            }
            syncDTO.setItemList(itemList);
        }
        list.addAll(incomeList);

        Map<String, Object> refundMap = new HashMap<>();
        refundMap.put("detaiType", new Integer[] {
                SubAccountDetailTypeEnum.ZKRefundPay.getValue(),
                SubAccountDetailTypeEnum.ZKShuttleRefundPay.getValue(),
                SubAccountDetailTypeEnum.ZKMallRefundPay.getValue()
        });
        refundMap.put("tradeSource", SubAccountDetailTradeSourceEnum.TradeRefund.getValue());
        refundMap.put("detaiStatus", new Integer[]{SubAccountDetailStatusEnum.RefundSuccess.getValue()});
        refundMap.put("startTime", start);
        refundMap.put("endTime", end);
        List<ZKLedgerSyncDTO> refundList = subAccountDetailService.syncZKLedgerData(refundMap);

        list.addAll(refundList);
        for(ZKLedgerSyncDTO syncDTO : refundList) {
            SubAccountDetailExtendDomain detailExtend = new SubAccountDetailExtendDomain();
            detailExtend.setSubAccountDetailId(syncDTO.getDetailId());
            detailExtend = subAccountDetailExtendService.getSingleByBeanProp(detailExtend);
            List<SubAccountDetailPayItemsExtendDomain> itemsExtendDomainList = JsonUtils.fromJsonToList(detailExtend.getPayItemsExtend(), SubAccountDetailPayItemsExtendDomain.class);
            List<PaymentItemDTO> itemList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(itemsExtendDomainList)) {
                for(SubAccountDetailPayItemsExtendDomain extendDomain : itemsExtendDomainList) {
                    PaymentItemDTO dto = new PaymentItemDTO();
                    dto.setOrderType(extendDomain.getOrderType());
                    dto.setTradeSn(extendDomain.getTradeSn());
                    dto.setAmount(extendDomain.getAmount());
                    itemList.add(dto);
                }
            }
            syncDTO.setItemList(itemList);
        }
        return list;
    }

    @Transactional
    @Override
    public void fixZKPayItemExtends() {
        //支付的
        SubAccountDetailDomain buyerDetail = new SubAccountDetailDomain();
        buyerDetail.setType(SubAccountDetailTypeEnum.ZKOrderPay.getValue());
        buyerDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeOrder.getValue());
        buyerDetail.setSign(CommOperatorSymbolEnum.Subtract.getValue());
        List<SubAccountDetailDomain> lsit = subAccountDetailService.findByBeanProp(buyerDetail);
        for(SubAccountDetailDomain detailDomain : lsit) {
            SubAccountDetailExtendDomain buyerDetailExtend = new SubAccountDetailExtendDomain();
            buyerDetailExtend.setSubAccountDetailId(detailDomain.getId());
            buyerDetailExtend.setSubAccountId(detailDomain.getSubAccountId());
            buyerDetailExtend = subAccountDetailExtendService.getSingleByBeanProp(buyerDetailExtend);

            SubAccountDetailExtendDomain newExtends = new SubAccountDetailExtendDomain();
            newExtends.setId(buyerDetailExtend.getId());
            newExtends.setAccountId(detailDomain.getAccountId());

            List<SubAccountDetailPayItemsExtendDomain> itemsExtendList = new ArrayList<>();

            SubAccountDetailPayItemsExtendDomain payItemsExtendDomain = new SubAccountDetailPayItemsExtendDomain();
            payItemsExtendDomain.setAmount(detailDomain.getAmountPaid());
            payItemsExtendDomain.setOrderType(1);
            payItemsExtendDomain.setTradeSn(detailDomain.getTradeSn());
            payItemsExtendDomain.setRemark("租车费用");
            itemsExtendList.add(payItemsExtendDomain);

            newExtends.setPayItemsExtend(JsonUtils.toJsonString(itemsExtendList));

            subAccountDetailExtendService.merge(newExtends);
        }
        //退款的
        SubAccountDetailDomain sellerDetail = new SubAccountDetailDomain();
        sellerDetail.setType(SubAccountDetailTypeEnum.ZKRefundPay.getValue());
        sellerDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeRefund.getValue());
        List<SubAccountDetailDomain> refundlsit = subAccountDetailService.findByBeanProp(sellerDetail);
        for(SubAccountDetailDomain detailDomain : refundlsit) {
            SubAccountDetailExtendDomain buyerDetailExtend = new SubAccountDetailExtendDomain();
            buyerDetailExtend.setSubAccountDetailId(detailDomain.getId());
            buyerDetailExtend.setSubAccountId(detailDomain.getSubAccountId());
            buyerDetailExtend = subAccountDetailExtendService.getSingleByBeanProp(buyerDetailExtend);
            if(buyerDetailExtend == null) {
                continue;
            }
            SubAccountDetailExtendDomain newExtends = new SubAccountDetailExtendDomain();
            newExtends.setId(buyerDetailExtend.getId());

            List<SubAccountDetailPayItemsExtendDomain> itemsExtendList = new ArrayList<>();

            SubAccountDetailPayItemsExtendDomain payItemsExtendDomain = new SubAccountDetailPayItemsExtendDomain();
            payItemsExtendDomain.setAmount(detailDomain.getAmountPaid());
            payItemsExtendDomain.setOrderType(1);
            payItemsExtendDomain.setTradeSn(detailDomain.getTradeSn());
            payItemsExtendDomain.setRemark("租车费用退款");
            itemsExtendList.add(payItemsExtendDomain);

            newExtends.setPayItemsExtend(JsonUtils.toJsonString(itemsExtendList));

            subAccountDetailExtendService.merge(newExtends);
        }
    }

    @Transactional
    @Override
    public void fixZKRefundRelaDetailId() {
        SubAccountDetailDomain sellerRefundDetail = new SubAccountDetailDomain();
        sellerRefundDetail.setType(SubAccountDetailTypeEnum.ZKRefundPay.getValue());
        sellerRefundDetail.setTradeSource(SubAccountDetailTradeSourceEnum.TradeRefund.getValue());
        sellerRefundDetail.setSign(CommOperatorSymbolEnum.Subtract.getValue());
        List<SubAccountDetailDomain> detailDomainList = subAccountDetailService.findByBeanProp(sellerRefundDetail);
        for(SubAccountDetailDomain detailDomain : detailDomainList) {
            SubAccountDetailExtendDomain sellerDetailExtend = new SubAccountDetailExtendDomain();
            sellerDetailExtend.setAccountId(detailDomain.getAccountId());
            sellerDetailExtend.setSubAccountId(detailDomain.getSubAccountId());
            sellerDetailExtend.setSubAccountDetailId(detailDomain.getId());
            sellerDetailExtend = subAccountDetailExtendService.getSingleByBeanProp(sellerDetailExtend);
            if(sellerDetailExtend == null) {
                continue;
            }
            String paymentExtend = sellerDetailExtend.getPaymentExtend();
            SubAccountDetailPaymentExtendDomain paymentExtendDomain = JsonUtils.fromJson(paymentExtend, SubAccountDetailPaymentExtendDomain.class);
            String sellerIncomeSn = paymentExtendDomain.getRelaSellerIncomeDetailSn();
            SubAccountDetailDomain sellerIncome = new SubAccountDetailDomain();
            sellerIncome.setSn(sellerIncomeSn);
            sellerIncome = subAccountDetailService.getSingleByBeanProp(sellerIncome);
            SubAccountDetailDomain newDetail = new SubAccountDetailDomain();
            newDetail.setId(detailDomain.getId());
            newDetail.setRelaDetailId(sellerIncome.getId());
            subAccountDetailService.merge(newDetail);
        }
    }
}
