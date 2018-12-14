package com.huboot.business.base_model.pay.service.account;



import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentDomain;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailForPayAnotherDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailPaymentTransferDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailPrePaymentDTO;
import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentStatusEnum;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.IBaseService;

import java.util.Date;
import java.util.List;

/**
 * 账户中心-子账户明细支付基础信息Service
 */
public interface ISubAccountDetailPaymentService extends IBaseService<SubAccountDetailPaymentDomain, Long> {
    /**
     * 通过subAccountDetailId查询结果
     *
     * @param subAccountDetailId
     * @return
     */
    SubAccountDetailPaymentDomain findFirstBySubAccountDetailId(Long subAccountDetailId, Integer status);

    /**
     * 通过subAccountDetailId查询结果
     *
     * @param subAccountDetailId
     * @return
     */
    List<SubAccountDetailPaymentDomain> findBySubAccountDetailIdAndStatus(Long subAccountDetailId, Integer status, Integer type);

    /**
     * 通过subAccountDetailId查询结果
     *
     * @param subAccountDetailSn
     * @return
     */
    List<SubAccountDetailPaymentDomain> findBySubAccountDetailSn(String subAccountDetailSn);

    /**
     * 通过subAccountDetailSn和状态查询结果
     *
     * @param subAccountDetailSn
     * @return
     */
    SubAccountDetailPaymentDomain findBySubAccountDetailSnAndStatus(String subAccountDetailSn, SubAccountDetailPaymentStatusEnum status);

    /**
     * 通过subAccountDetailId查询结果
     *
     * @param subAccountDetailSn
     * @return
     */
    List<SubAccountDetailPaymentDomain> findForOrderBySubAccountDetailSn(String subAccountDetailSn);
    /**
     * 通过subAccountDetailId关闭交易
     *
     * @param subAccountDetailId
     */
    void closeBySubAccountDetailId(Long subAccountDetailId);

    /**
     * 预支付
     *
     * @param
     * @return PaymentDTO
     * @throws ApiException
     */
    SubAccountDetailPaymentDomain prePayForDetail(SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO) throws ApiException;

    public SubAccountDetailPaymentDomain offlinePrePayForDetail(SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO, SubAccountDetailForPayAnotherDTO dto) throws ApiException;;

    //根据单号查询订单
    SubAccountDetailPaymentDomain findByPaymentSn(String sn) throws ApiException;

    /**
     * 支付成功
     */
    public void paidForDetail(Date paidDate, Long paymentId, String transactionNo, Integer payType);

    /**
     * 支付失败
     * @param paymentId
     */
    public void payFailedForDetail(Long paymentId, String remark);

    /**
     * 支付中
     * @param paymentId
     */
    public void payingForDetail(Long paymentId, String transactionNo);

    void transfer(SubAccountDetailPaymentTransferDTO dto);

    /**
     * 查询转账信息:
     */
    void getTransferByDetailId(Long detailId);

}
