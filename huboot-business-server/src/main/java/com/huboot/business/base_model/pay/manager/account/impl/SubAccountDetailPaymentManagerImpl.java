package com.huboot.business.base_model.pay.manager.account.impl;


import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentDomain;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailPaymentPingppDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailPrePaymentDTO;
import com.huboot.business.base_model.pay.enums.*;
import com.huboot.business.base_model.pay.manager.account.ISubAccountDetailPaymentManager;
import com.huboot.business.base_model.pay.service.account.*;
import com.huboot.business.base_model.pay.service.account.impl.SubAccountDetailPaymentPingppServiceImpl;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailPaymentForMiniRiskVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailPaymentForZKVO;
import com.huboot.business.base_model.pay.vo.account.SubAccountDetailPaymentVO;
import com.huboot.business.common.utils.NetworkUtil;
import com.huboot.business.mybatis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户中心-子账户明细支付ManagerImpl
 */
@Service("subAccountDetailPaymentManagerImpl")
public class SubAccountDetailPaymentManagerImpl implements ISubAccountDetailPaymentManager {

    @Autowired
    private ISubAccountDetailService subAccountDetailService;
    @Autowired
    private ISubAccountDetailPaymentBaseService subAccountDetailPaymentBaseService;
    @Autowired
    private ISubAccountDetailPaymentPingppService subAccountDetailPaymentPingppService;
    @Autowired
    private ISubAccountDetailPaymentService subAccountDetailPaymentService;

    @Autowired
    private IAccountBaseService accountBaseService;


    @Override
    @Transactional
    public String payForDetail(SubAccountDetailPaymentVO vo, HttpServletRequest request) throws ApiException {
        User user = SecurityContextHelper.getCurrentUser();
        String charge = "";
        String ipAddress = NetworkUtil.getIpAddress(request);
        AssertUtil.notNull(vo.getPaymentType(), "支付类型不能为空");
        AssertUtil.notNull(vo.getSubAccountDetailId(), "支付明细id不能为空");
        //查询明细信息
        SubAccountDetailDomain subAccountDetailDomain = subAccountDetailService.find(vo.getSubAccountDetailId());
        AssertUtil.notNull(subAccountDetailDomain, "支付信息不正确");
        //校验身份
        if (!user.getLoginCustomer().getAccountId().equals(subAccountDetailDomain.getAccountId())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "支付信息有误");
        }
        if (!SubAccountDetailStatusEnum.WaitForPay.getValue().equals(subAccountDetailDomain.getStatus())) {
            throw new ApiException("支付信息已失效");
        }
        //支付基础信息
        SubAccountDetailPaymentBaseDomain subAccountDetailPaymentBaseDomain = subAccountDetailPaymentBaseService.findByPayType(vo.getPaymentType());
        Integer payMethod = subAccountDetailPaymentBaseDomain.getMethod();
        if (SubAccountDetailPaymentMethodEnum.OnLine.getValue().equals(payMethod)) {
            //预支付单
            SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO = new SubAccountDetailPrePaymentDTO();
            subAccountDetailPrePaymentDTO.setDetailId(subAccountDetailDomain.getId());
            subAccountDetailPrePaymentDTO.setPaymentType(vo.getPaymentType());
            subAccountDetailPrePaymentDTO.setRedPacketAmount(vo.getRedPacketAmount());
            SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = subAccountDetailPaymentService.prePayForDetail(subAccountDetailPrePaymentDTO);
            if (subAccountDetailPaymentDomain.getType().equals(SubAccountDetailPaymentTypeEnum.ApplyForPaid.getValue())) {
                /*SharePaymentDTO sharePaymentDTO = new SharePaymentDTO();
                sharePaymentDTO.setSubAccountDetailId(subAccountDetailPaymentDomain.getSubAccountDetailId());
                sharePaymentDTO.setUserId(String.valueOf(user.getUserId()));
                sharePaymentDTO.setRedPacketAmount(subAccountDetailPrePaymentDTO.getRedPacketAmount());
                sharePaymentDTO.setAmount(subAccountDetailPaymentDomain.getAmount());
                sharePaymentDTO.setTitle("邀请支付");
                SubShareDTO subShareDTO = subShareService.createSharePay(sharePaymentDTO);
                charge = subShareDTO.getSubShareBaseDomain().getSubShareId().toString();*/
            } else {
                SubAccountDetailPaymentPingppDTO subAccountDetailPaymentPingppDTO = new SubAccountDetailPaymentPingppDTO();
                subAccountDetailPaymentPingppDTO.setAmount(subAccountDetailPaymentDomain.getAmount());
                subAccountDetailPaymentPingppDTO.setClientIp(ipAddress);
                subAccountDetailPaymentPingppDTO.setPaymentId(subAccountDetailPaymentDomain.getId());
                subAccountDetailPaymentPingppDTO.setPaymentSn(subAccountDetailPaymentDomain.getSn());
                subAccountDetailPaymentPingppDTO.setPaymentType(vo.getPaymentType());
                subAccountDetailPaymentPingppDTO.setUserOpenid(user.getWeixinOpenid());
                subAccountDetailPaymentPingppDTO.setUserOpenid(user.getWeixinOpenid());
                /*if (StringUtils.isEmpty(user.getWeixinOpenid())) {
                    UserDomain userDomain = userService.find(user.getUserId());
                    subAccountDetailPaymentPingppDTO.setUserOpenid(userDomain.getWeixinOpenid());
                } else {
                    subAccountDetailPaymentPingppDTO.setUserOpenid(user.getWeixinOpenid());
                }*/
                //请求pingpp
                charge = subAccountDetailPaymentPingppService.createCharge(subAccountDetailPaymentPingppDTO, SubAccountDetailPaymentPingppServiceImpl.PayFrom.Oterh);
            }
        } else {
            //线下转账--直接支付完成
//            subAccountDetailPaymentService.paySuccessForOrder(new Date(), paymentDTO.getId(), user.getLoginCustomer().getCustomerId(), "");
        }
        return charge;
    }

    @Override
    public String payForZK(SubAccountDetailPaymentForZKVO vo) throws ApiException {
        String charge = "";

        AssertUtil.notNull(vo.getIpAddress(), "ip不能为空");
        AssertUtil.notNull(vo.getUserGid(), "用户gid不能为空");
        if(2 == vo.getPaymentType() || 12 == vo.getPaymentType()) {
            AssertUtil.notNull(vo.getUserOpendId(), "用户微信opendi不能为空");
        }
        AssertUtil.notNull(vo.getPaymentType(), "支付类型不能为空");
        AssertUtil.notNull(vo.getSubAccountDetailId(), "支付明细id不能为空");
        //查询明细信息
        SubAccountDetailDomain subAccountDetailDomain = subAccountDetailService.find(vo.getSubAccountDetailId());
        AssertUtil.notNull(subAccountDetailDomain, "支付信息不正确");
        //校验身份
        AccountBaseDomain accountBaseDomain = new AccountBaseDomain();
        accountBaseDomain.setUserGid(vo.getUserGid());
        accountBaseDomain = accountBaseService.getSingleByBeanProp(accountBaseDomain);
        if (!accountBaseDomain.getAccountId().equals(subAccountDetailDomain.getAccountId())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "支付信息有误");
        }
        if (!SubAccountDetailStatusEnum.WaitForPay.getValue().equals(subAccountDetailDomain.getStatus())) {
            throw new ApiException("支付信息已失效");
        }
        //支付基础信息
        SubAccountDetailPaymentBaseDomain subAccountDetailPaymentBaseDomain = subAccountDetailPaymentBaseService.findByPayType(vo.getPaymentType());
        Integer payMethod = subAccountDetailPaymentBaseDomain.getMethod();
        if (SubAccountDetailPaymentMethodEnum.OnLine.getValue().equals(payMethod)) {
            //预支付单
            SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO = new SubAccountDetailPrePaymentDTO();
            subAccountDetailPrePaymentDTO.setDetailId(subAccountDetailDomain.getId());
            subAccountDetailPrePaymentDTO.setPaymentType(vo.getPaymentType());
            subAccountDetailPrePaymentDTO.setRedPacketAmount(new BigDecimal(0));
            SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = subAccountDetailPaymentService.prePayForDetail(subAccountDetailPrePaymentDTO);

            //直客代付
            if (subAccountDetailPaymentDomain.getType().equals(SubAccountDetailPaymentTypeEnum.ApplyForPaid.getValue())) {
                /*SharePaymentDTO sharePaymentDTO = new SharePaymentDTO();
                sharePaymentDTO.setSubAccountDetailId(subAccountDetailPaymentDomain.getSubAccountDetailId());
                sharePaymentDTO.setUserId(accountBaseDomain.getUserGid());
                sharePaymentDTO.setRedPacketAmount(subAccountDetailPrePaymentDTO.getRedPacketAmount());
                sharePaymentDTO.setAmount(subAccountDetailPaymentDomain.getAmount());
                sharePaymentDTO.setTitle("直客代付");
                SubShareDTO subShareDTO = subShareService.createSharePay(sharePaymentDTO);
                charge = subShareDTO.getSubShareBaseDomain().getSubShareId().toString();*/
            }else {
                //正常支付
                SubAccountDetailPaymentPingppDTO subAccountDetailPaymentPingppDTO = new SubAccountDetailPaymentPingppDTO();
                subAccountDetailPaymentPingppDTO.setAmount(subAccountDetailPaymentDomain.getAmount());
                subAccountDetailPaymentPingppDTO.setClientIp(vo.getIpAddress());
                subAccountDetailPaymentPingppDTO.setPaymentId(subAccountDetailPaymentDomain.getId());
                subAccountDetailPaymentPingppDTO.setPaymentSn(subAccountDetailPaymentDomain.getSn());
                subAccountDetailPaymentPingppDTO.setPaymentType(vo.getPaymentType());
                subAccountDetailPaymentPingppDTO.setUserOpenid(vo.getUserOpendId());
                charge = subAccountDetailPaymentPingppService.createCharge(subAccountDetailPaymentPingppDTO,SubAccountDetailPaymentPingppServiceImpl.PayFrom.Oterh);
            }
        }
        return charge;
    }

    @Override
    public String payForMiniRisk(SubAccountDetailPaymentForMiniRiskVO vo, HttpServletRequest request) {

        String charge = "";
        User user = SecurityContextHelper.getCurrentUser();
        String ipAddress = NetworkUtil.getIpAddress(request);
        AssertUtil.notNull(vo.getPaymentType(), "支付类型不能为空");
        AssertUtil.notNull(vo.getSubAccountDetailSn(), "支付明细id不能为空");
        //查询明细信息
        SubAccountDetailDTO detailDTO = subAccountDetailService.findByDetailSn(vo.getSubAccountDetailSn());
        SubAccountDetailDomain subAccountDetailDomain = subAccountDetailService.find(detailDTO.getSubAccountDetailDomain().getId());
        AssertUtil.notNull(subAccountDetailDomain, "支付信息不正确");
        //校验身份
        AccountBaseDomain accountBaseDomain = new AccountBaseDomain();
        if (!user.getLoginCustomer().getAccountId().equals(subAccountDetailDomain.getAccountId())) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "支付信息有误");
        }
        if (!SubAccountDetailStatusEnum.WaitForPay.getValue().equals(subAccountDetailDomain.getStatus())) {
            throw new ApiException("支付信息已失效");
        }
        //支付基础信息
        SubAccountDetailPaymentBaseDomain subAccountDetailPaymentBaseDomain = subAccountDetailPaymentBaseService.findByPayType(vo.getPaymentType());
        Integer payMethod = subAccountDetailPaymentBaseDomain.getMethod();
        if (SubAccountDetailPaymentMethodEnum.OnLine.getValue().equals(payMethod)) {
            //预支付单
            SubAccountDetailPrePaymentDTO subAccountDetailPrePaymentDTO = new SubAccountDetailPrePaymentDTO();
            subAccountDetailPrePaymentDTO.setDetailId(subAccountDetailDomain.getId());
            subAccountDetailPrePaymentDTO.setPaymentType(vo.getPaymentType());
            subAccountDetailPrePaymentDTO.setRedPacketAmount(new BigDecimal(0));
            SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = subAccountDetailPaymentService.prePayForDetail(subAccountDetailPrePaymentDTO);
            SubAccountDetailPaymentPingppDTO subAccountDetailPaymentPingppDTO = new SubAccountDetailPaymentPingppDTO();
            subAccountDetailPaymentPingppDTO.setAmount(subAccountDetailPaymentDomain.getAmount());
            subAccountDetailPaymentPingppDTO.setClientIp(ipAddress);
            subAccountDetailPaymentPingppDTO.setPaymentId(subAccountDetailPaymentDomain.getId());
            subAccountDetailPaymentPingppDTO.setPaymentSn(subAccountDetailPaymentDomain.getSn());
            subAccountDetailPaymentPingppDTO.setPaymentType(vo.getPaymentType());
            subAccountDetailPaymentPingppDTO.setUserOpenid(user.getMiniRiskOpenId());
            charge = subAccountDetailPaymentPingppService.createCharge(subAccountDetailPaymentPingppDTO, SubAccountDetailPaymentPingppServiceImpl.PayFrom.MiniRisk);

        }
        return charge;

    }


    @Override
    @Transactional
    public List<Long> migratePay() throws ApiException {
      /*  PaymentDomain paymentDomain = new PaymentDomain();
        paymentDomain.setStatus(CustomerPaymentStatusEnum.Paid.getValue());
        paymentDomain.setSource(CustomerPaymentSourceEnum.OrderPay.getValue());
        List<PaymentDomain> paymentDomains = paymentService.findByBeanProp(paymentDomain);*/
        List<Long> ids = new ArrayList<>();//重复支付的信息
        /*if (!CollectionUtils.isEmpty(paymentDomains)) {
            paymentDomains.forEach(paymentDomain1 -> {
                OrderPaymentRelaDomain orderPaymentRelaDomain = orderPaymentRelaService.findByPaymentId(paymentDomain1.getId());
                OrderDomain orderDomain = orderService.find(orderPaymentRelaDomain.getOrderId());
                if (orderDomain != null) {
                    OrderPaymentDomain orderPaymentDomain = orderPaymentService.findSingleByOrderIdWithStage(orderPaymentRelaDomain.getOrderId(), orderPaymentRelaDomain.getPaymentStage(), null);
                    if (orderPaymentDomain == null) {
                        //创建订单支付信息
                        OrderPaymentDomain orderPayment = orderPaymentService.createOrderPayment(orderDomain, paymentDomain1.getAmountPaid(), orderPaymentRelaDomain.getPaymentStage());
                        if (orderPayment.getPaymentStatus().equals(CustomerPaymentStatusEnum.Unpaid.getValue())) {
                            OrderPaymentDomain newOrderPaymentDomain = new OrderPaymentDomain();
                            newOrderPaymentDomain.setId(orderPayment.getId());
                            newOrderPaymentDomain.setPaymentStatus(CustomerPaymentStatusEnum.Paid.getValue());
                            orderPaymentService.merge(newOrderPaymentDomain);
                            //账单
                            SubAccountDetailDTO subAccountDetailDTO = subAccountDetailService.findByDetailSn(orderPayment.getSubAccountDetailSn());
                            SubAccountDetailDomain subAccountDetailDomain = new SubAccountDetailDomain();
                            subAccountDetailDomain.setId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
                            subAccountDetailDomain.setStatus(SubAccountDetailStatusEnum.Finished.getValue());
                            subAccountDetailDomain.setPaymentDate(paymentDomain1.getPaymentDate());
                            subAccountDetailService.merge(subAccountDetailDomain);
                            //插入支付数据
                            SubAccountDetailPaymentDomain subAccountDetailPaymentDomain = new SubAccountDetailPaymentDomain();
                            subAccountDetailPaymentDomain.setSubAccountId(subAccountDetailDTO.getSubAccountDetailDomain().getSubAccountId());
                            subAccountDetailPaymentDomain.setSubAccountDetailId(subAccountDetailDTO.getSubAccountDetailDomain().getId());
                            subAccountDetailPaymentDomain.setSn(paymentDomain1.getSn());
                            subAccountDetailPaymentDomain.setType(paymentDomain1.getType());
                            subAccountDetailPaymentDomain.setPlatform(paymentDomain1.getPlatform());
                            subAccountDetailPaymentDomain.setMethod(paymentDomain1.getPayMethod());
                            subAccountDetailPaymentDomain.setTransactionNo(paymentDomain1.getTransactionNo());
                            subAccountDetailPaymentDomain.setPaymentDate(paymentDomain1.getPaymentDate());
                            subAccountDetailPaymentDomain.setAmount(paymentDomain1.getAmountPaid());
                            subAccountDetailPaymentDomain.setRemark("");
                            subAccountDetailPaymentDomain.setStatus(SubAccountDetailPaymentStatusEnum.Paid.getValue());
                            subAccountDetailPaymentService.create(subAccountDetailPaymentDomain);
                        }
                    } else {
                        ids.add(orderPaymentRelaDomain.getPaymentId());
                    }
                }
            });
        }*/
        return ids;
    }


}
