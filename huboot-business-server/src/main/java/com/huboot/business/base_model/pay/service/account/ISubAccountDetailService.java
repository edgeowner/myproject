package com.huboot.business.base_model.pay.service.account;



import com.huboot.business.base_model.pay.domain.account.SubAccountDetailDomain;
import com.huboot.business.base_model.pay.dto.account.*;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.IBaseService;
import com.huboot.business.mybatis.Pager;
import com.huboot.business.mybatis.QueryCondition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 账户中心-子账户明细Service
 */
public interface ISubAccountDetailService extends IBaseService<SubAccountDetailDomain, Long> {
    /**
     * 创建明细信息（卖家）
     *
     * @param subAccountDetailAddDTO
     * @return
     * @throws ApiException
     */
    public void createDetail(SubAccountDetailAddDTO subAccountDetailAddDTO) throws ApiException;

    /**
     * 取消收款（卖家）
     *
     * @param id
     * @return
     * @throws ApiException
     */
    public void cancelRecharge(Long id) throws ApiException;
    /**
     * 取消账单
     *
     * @param
     * @return
     * @throws ApiException
     */
    public void cancelRecharge(String detailSn) throws ApiException;

    /**
     * 根据ID，查询支付成功，true:支付成功，false失败
     * @param id
     * @return
     */
    Boolean checkDetailPaidById(Long id);

    /**
     * @param accountId
     * @param subAccountId
     * @param status
     * @return
     * @throws ApiException
     */
    List<SubAccountDetailDomain> findByAccountIdsWithStatus(Long accountId, Long subAccountId, Integer status) throws ApiException;

    /**
     * 分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<SubAccountDetailDomain> findByAdminQueryForPager(QueryCondition queryCondition);

    /**
     * 支付成功回调-唯一方法
     * @param subAccountDetailForPaidDTO
     */
    void updateForPaid(SubAccountDetailForPaidDTO subAccountDetailForPaidDTO);

    /**
     * 余额OR红包账户明细分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    public Pager<SubAccountDetailDomain> findBalanceOrRedPacketAccountDetailForPager(QueryCondition queryCondition) throws ApiException;

    /**
     * 代付明细分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    public Pager<SubAccountDetailDomain> findBalancePayForAnotherForPager(QueryCondition queryCondition) throws ApiException;
    /**
     * 余额账户提现
     */
    void cashBalanceAccount(SubAccountForCashDTO subAccountForCashDTO);
    /**
     * 余额账户退款
     */
    void refundBalanceAccount(SubAccountForRefundDTO subAccountForRefundDTO);
    /**
     * 余额账户订单收入
     */
    void incomeBalanceAccount(SubAccountForOrderDTO subAccountForOrderDTO);

    /**
     * 余额账户登录返现
     */
    void incomeBalanceAccountForMarketLogin(SubAccountForLoginDTO subAccountForLoginDTO);
    /**
     * 余额账户交易抽奖返现
     */
    void incomeBalanceAccountForMarketTrade(SubAccountForOrderDTO subAccountForOrderDTO);

    /**
     * 余额账户提现返还失败
     */
    void cashBackFail(SubAccountCashBackFailDTO subAccountCashBackFailDTO);
    /**
     * 财务-提交代付
     * @param
     * @return PaymentDTO
     * @throws ApiException
     */
    public void submitsPayAnother(SubAccountDetailForPayAnotherDTO dto)  throws ApiException;

    /**
     * 能否提现
     * @param balanceAccountId
     * @return true 能
     */
    public Boolean balanceAccountCanCash(Long balanceAccountId);

    /**
     *
     * @param sn
     * @return
     */
    SubAccountDetailDTO findByDetailSn(String sn);
    /**
     *
     * @param id
     * @return
     */
    SubAccountDetailDTO findByDetailId(Long id);

    /**
     *
     * @return
     */
    List<SubAccountDetailDomain> findBySubAccountIdAndTradeSnAndType(Long subAccountId, String tradeSn, Integer type);
    /**
     * 支付失败
     * @param subAccountDetailForPayFailDTO
     */
    void updateForPayFail(SubAccountDetailForPayFailDTO subAccountDetailForPayFailDTO);
    void updateForPaying(Long detailId);
    /**
     * 代付状态-已提交代付的状态更新任务
     * @param detailId
     */
    void payAnotherStatusTask(Long detailId);

    /**
     *  根据店铺名称和类型统计红包子账户
     *
     * @param shopName
     * @return
     */
    BigDecimal sumRedPacketSubAccount(String shopName, List<Integer> types);

    /**
     * 红包账户操作方法
     *
     * @param accountDetailAddDTO
     * @return detailSn
     * @throws ApiException
     */
    SubAccountDetailDTO createRedPacketAccountDetail(RedPacketAccountDetailAddDTO accountDetailAddDTO);
    /**
     * 临时账户操作方法
     *
     * @param accountDetailAddDTO
     * @return detailSn
     * @throws ApiException
     */
    SubAccountDetailDTO createBillAccountDetail(BillAccountDetailAddDTO accountDetailAddDTO);


    /**
     * 创建明细信息（订单生成待支付信息的时候调用）
     *
     * @param subAccountDetailAddForOrderDTO
     * @return detailSn
     * @throws ApiException
     */
    SubAccountDetailDTO createBillDetailForOrder(SubAccountDetailAddForOrderDTO subAccountDetailAddForOrderDTO);
    /**
     * 发放红包-- 创建明细信息（订单状态成为已结算，调用该方法）
     *
     * @param redPacketAccountDetailAddForOrderDTO
     * @return detailSn
     * @throws ApiException
     */
    void createRedPacketDetailForOrder(RedPacketAccountDetailAddForOrderDTO redPacketAccountDetailAddForOrderDTO);
    /**
     * 发放红包-- 创建明细信息（订单状态成为已结算，调用该方法）
     *
     * @param dto
     * @return detailSn
     * @throws ApiException
     */
    void createRedPacketDetailForShopCar(RedPacketAccountDetailAddForShopCarDTO dto);
    /**
     * 到期失效-- 创建明细信息（订单状态成为已结算，调用该方法）
     *
     * @param redPacketAccountDetailAddForOrderDTO
     * @return detailSn
     * @throws ApiException
     */
    void createRedPacketDetailForExpire(RedPacketAccountDetailAddForOrderDTO redPacketAccountDetailAddForOrderDTO);
    /**
     * 系统发放-- 创建明细信息（订单状态成为已结算，调用该方法）
     *
     * @param redPacketAccountDetailAddForOrderDTO
     * @return detailSn
     * @throws ApiException
     */
    void createRedPacketDetailForSystemProvide(RedPacketAccountDetailAddForOrderDTO redPacketAccountDetailAddForOrderDTO);
    /**
     * 营业收入明细分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<SubAccountDetailDomain> findForOrderByAdminQueryForPager(QueryCondition queryCondition) throws ApiException;

    /**
     * F模式押金收入明细分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    Pager<SubAccountDetailDomain> findForDepositByAdminQueryForPager(QueryCondition queryCondition);

    /**
     * 余额账户押金退款
     */
    SubAccountDetailDomain refundDepositBalanceAccount(SubAccountForRefundDepositDTO subAccountForRefundDepositDTO);


    /**
     * 获取直客昨日交易数据
     * @param shopId
     * @return
     */
    BigDecimal getZKYesterdayTransactionAmount(Long shopId);

    List<ZKLedgerSyncDTO> syncZKLedgerData(Map<String, Object> map);
}
