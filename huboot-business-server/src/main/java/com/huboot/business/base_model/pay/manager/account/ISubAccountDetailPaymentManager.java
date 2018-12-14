package com.huboot.business.base_model.pay.manager.account;



import com.huboot.business.base_model.pay.vo.account.SubAccountDetailPaymentForMiniRiskVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailPaymentForZKVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailPaymentVO;
import com.huboot.business.mybatis.ApiException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
*账户中心-子账户明细支付Manager
*/
public interface ISubAccountDetailPaymentManager {

    /**
    * 创建一个对象
    * @param vo
    * @return
    * @throws ApiException
    */
    public String payForDetail(SubAccountDetailPaymentVO vo, HttpServletRequest request) throws ApiException;


    /**
     * 直客支付
     * @param vo
     * @return
     * @throws ApiException
     */
    String payForZK(SubAccountDetailPaymentForZKVO vo)throws ApiException;

    List<Long> migratePay() throws ApiException;

    /**
     * 风控小程序支付
     * @param vo
     * @return
     */
    String payForMiniRisk(SubAccountDetailPaymentForMiniRiskVO vo, HttpServletRequest request);

}
