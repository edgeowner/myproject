package com.huboot.business.base_model.pay.manager.account;




import com.huboot.business.base_model.pay.dto.account.ZKLedgerSyncDTO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailAddForZKOrderVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailAddForZKRefundVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailForPayVO;
import com.huboot.business.mybatis.ApiException;

import java.util.List;
import java.util.Map;

/**
 * 账户中心-子账户明细Manager
 */
public interface ISubAccountDetailForZKManager {

    /**
     * 获取店铺账户id
     * @param companyUid
     * @return
     * @throws ApiException
     */
    Map<String, String> getAccountIdByCompanyUidForZK(String userGid, String companyUid)throws ApiException;

    /**
     * 创建直客支付
     * @param vo
     * @return
     * @throws Exception
     */
    String createDetailForZKOrder(SubAccountDetailAddForZKOrderVO vo)throws ApiException;

    /**
     * 根据主键查询一个对象
     * @param sn
     * @return
     * @throws ApiException
     */
    public SubAccountDetailForPayVO findForPayByDetailSnForZK(String userGid, String sn) throws ApiException;

    /**
     * 创建直客退款
     * @param vo
     * @return
     * @throws Exception
     */
    String createDetailForZKRefund(SubAccountDetailAddForZKRefundVO vo)throws ApiException;

    /**
     * 解冻直客支付明细
     * @param detailSn
     * @throws ApiException
     */
    void unFreezeDetailForZK(Long buyerAccountId, Long sellerAccountId, String detailSn)throws Exception;

    /**
     * 同步直客业务台账数据
     * @param startTime
     * @param endTime
     * @return
     */
    List<ZKLedgerSyncDTO> syncZKLedgerData(String startTime, String endTime);

    void fixZKPayItemExtends();

    void fixZKRefundRelaDetailId();
}
