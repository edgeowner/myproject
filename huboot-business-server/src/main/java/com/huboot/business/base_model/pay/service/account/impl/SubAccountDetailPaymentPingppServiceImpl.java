package com.huboot.business.base_model.pay.service.account.impl;

import com.huboot.business.base_model.pay.config.pingpp.PingppProperties;
import com.huboot.business.base_model.pay.config.pingpp.PingppPropertiesMiniRisk;
import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailPaymentPingppDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentPingppDomain;
import com.huboot.business.base_model.pay.dto.account.PaymentPingppForDetailDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDetailPaymentPingppDTO;
import com.huboot.business.base_model.pay.enums.CustomerPaymentPingppOperateTypeEnum;
import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentExtendCashAccountTypeEnum;
import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentPingppOperateTypeEnum;
import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentTypeEnum;
import com.huboot.business.base_model.pay.service.account.ISubAccountDetailPaymentPingppService;
import com.huboot.business.common.utils.BigDecimalUtil;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.mybatis.AbstractBaseService;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.AssertUtil;
import com.huboot.business.mybatis.ErrorCodeEnum;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;
import com.pingplusplus.model.Transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户中心-子账户明细支付PINGPPServiceImpl
 */
@Service("subAccountDetailPaymentPingppServiceImpl")
@EnableConfigurationProperties({PingppProperties.class,PingppPropertiesMiniRisk.class})
public class SubAccountDetailPaymentPingppServiceImpl extends AbstractBaseService<SubAccountDetailPaymentPingppDomain, Long> implements ISubAccountDetailPaymentPingppService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String CHARGE_CURRENCY = "cny";
    private static final String TRANSFER_CHANNEL = "unionpay";
    private static PublicKey publicKey = null;
    @Autowired
    private PingppProperties properties;
    @Autowired
    private PingppPropertiesMiniRisk propertiesMiniRisk;

    @Autowired
    public SubAccountDetailPaymentPingppServiceImpl(ISubAccountDetailPaymentPingppDao subAccountDetailPaymentPingppDao) {
        super(subAccountDetailPaymentPingppDao);
    }

    public enum PayFrom{

        MiniRisk,
        Oterh;

    }

    /**
     * 创建 Charge
     * <p>
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://www.pingxx.com/api#api-c-new
     *
     * @return Charge
     */
    public String createCharge(SubAccountDetailPaymentPingppDTO subAccountDetailPaymentPingppDTO, PayFrom type) {
        //检查&&赋值
        SubAccountDetailPaymentTypeEnum paymentType = SubAccountDetailPaymentTypeEnum.valueOf(subAccountDetailPaymentPingppDTO.getPaymentType());
        if (BigDecimalUtil.lte(subAccountDetailPaymentPingppDTO.getAmount(), BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.JsonError, "支付金额必须大于0");
        }
        String channel = "";
        Map<String, Object> extra = new HashMap<String, Object>();
        String chargeString = "";
        String exceptionString = "";
        switch (paymentType) {
            case JSAPI:
                channel = "wx_pub";
                if (StringUtils.isEmpty(subAccountDetailPaymentPingppDTO.getUserOpenid())) {
                    throw new ApiException(ErrorCodeEnum.ParamsError, "请绑定微信再支付");
                }
                extra.put("open_id", subAccountDetailPaymentPingppDTO.getUserOpenid());
                break;
            case wx_lite:
                channel = "wx_lite";
                if (StringUtils.isEmpty(subAccountDetailPaymentPingppDTO.getUserOpenid())) {
                    throw new ApiException(ErrorCodeEnum.ParamsError, "请绑定微信再支付");
                }
                extra.put("open_id", subAccountDetailPaymentPingppDTO.getUserOpenid());
                break;
            case AlipayWap: {
                channel = "alipay_wap";
                extra.put("success_url", properties.getSuccessUrl());
                extra.put("app_pay", true);//尝试吊调支付宝app支付
                break;
            }
            default:
                throw new ApiException(ErrorCodeEnum.ParamsError, "支付方式错误");
        }

        int amount = BigDecimalUtil.getTwoDecimalRound(subAccountDetailPaymentPingppDTO.getAmount()).multiply(new BigDecimal(100)).intValue();
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", amount);//订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100）
        chargeMap.put("currency", CHARGE_CURRENCY);
        chargeMap.put("subject", "租车盒子订单" /*+ subAccountDetailPaymentPingppDTO.getPaymentSn()*/);
        chargeMap.put("body", "租车盒子支付单：" + subAccountDetailPaymentPingppDTO.getPaymentSn());
        chargeMap.put("order_no", subAccountDetailPaymentPingppDTO.getPaymentSn());// 推荐使用 8-20 位，要求数字或字母，不允许其他字符
        chargeMap.put("channel", channel);// 支付使用的第三方支付渠道取值，请参考：https://www.pingxx.com/api#api-c-new
        chargeMap.put("client_ip", "127.0.0.1");// subAccountDetailPaymentPingppDTO.getClientIp()); // 发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        Map<String, String> app = new HashMap<String, String>();
        if(type == PayFrom.MiniRisk){
            app.put("id", propertiesMiniRisk.getAppId());
        } else {
            app.put("id", properties.getAppId());
        }
        chargeMap.put("app", app);
        chargeMap.put("extra", extra);
        try {
            logger.info("支付发送的报文：{}", JsonUtils.toJsonString(chargeMap));
            //发起交易请求
            charge = Charge.create(chargeMap);
            // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
            chargeString = charge.toString();
            logger.info("支付接收的报文：{}", chargeString);
            SubAccountDetailPaymentPingppDomain subAccountDetailPaymentPingppDomain = new SubAccountDetailPaymentPingppDomain();
            subAccountDetailPaymentPingppDomain.setSubAccountDetailPaymentId(subAccountDetailPaymentPingppDTO.getPaymentId());
            subAccountDetailPaymentPingppDomain.setSubAccountDetailPaymentSn(subAccountDetailPaymentPingppDTO.getPaymentSn());
            subAccountDetailPaymentPingppDomain.setPingppRequest(JsonUtils.toJsonString(chargeMap));
            subAccountDetailPaymentPingppDomain.setPingppResponse(StringUtils.isEmpty(chargeString) ? exceptionString : chargeString);
            subAccountDetailPaymentPingppDomain.setOperateType(StringUtils.isEmpty(chargeString) ? SubAccountDetailPaymentPingppOperateTypeEnum.None.getValue() : SubAccountDetailPaymentPingppOperateTypeEnum.POST.getValue());
            subAccountDetailPaymentPingppDomain.setPingppId(charge.getId());
            this.create(subAccountDetailPaymentPingppDomain);
        } catch (APIConnectionException e) {
            logger.error("APIConnectionException", e);
        } catch (ChannelException e) {
            logger.error("ChannelException", e);
        } catch (RateLimitException e) {
            logger.error("RateLimitException", e);
        } catch (AuthenticationException e) {
            logger.error("AuthenticationException", e);
        } catch (APIException e) {
            logger.error("APIException", e);
        } catch (InvalidRequestException e) {
            logger.error("APIException", e);
        }
        return chargeString;
    }

    @Override
    @Transactional
    public Transfer transfer(PaymentPingppForDetailDTO paymentPingppForDetailDTO) {
        if (BigDecimalUtil.lte(paymentPingppForDetailDTO.getAmount(), BigDecimal.ZERO)) {
            throw new ApiException(ErrorCodeEnum.JsonError, "转账金额必须大于0");
        }
        Transfer transfer = null;
        String exceptionString = "";
        String transferString = "";
        String transferType = "";
        //银联限制不能带字符
        String order_no = paymentPingppForDetailDTO.getPaymentSn().substring(1);
        int amount = BigDecimalUtil.getTwoDecimalRound(paymentPingppForDetailDTO.getAmount()).multiply(new BigDecimal(100)).intValue();
        if (SubAccountDetailPaymentExtendCashAccountTypeEnum.CompanyAccount.equals(paymentPingppForDetailDTO.getAccountType())) {
            transferType = "b2b";
        } else if (SubAccountDetailPaymentExtendCashAccountTypeEnum.PersonalAccount.equals(paymentPingppForDetailDTO.getAccountType())) {
            transferType = "b2c";
        } else {
            throw new ApiException(ErrorCodeEnum.ParamsError, "支付渠道信息错误");
        }
        Map<String, Object> transferMap = new HashMap<>();
        transferMap.put("channel", TRANSFER_CHANNEL);// 目前支持 wx(新渠道)、 wx_pub、unionpay
        transferMap.put("order_no", order_no);// 企业转账使用的商户内部订单号。wx(新渠道)、wx_pub 规定为 1 ~ 50 位不能重复的数字字母组合，unionpay 为1~16位的纯数字。
        transferMap.put("amount", amount);// 订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100,企业付款最小发送金额为 1 元）
        transferMap.put("type", transferType);// 付款类型，当前仅支持 b2c 企业付款
        transferMap.put("currency", CHARGE_CURRENCY);
//        transferMap.put("recipient", openid);// 接收者 id， 为用户在 wx(新渠道)、wx_pub 下的 open_id ;渠道为  unionpay 时，不需要传该参数。
        transferMap.put("description", "支付序列号:" + paymentPingppForDetailDTO.getPaymentSn());
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", properties.getAppId());
        transferMap.put("app", app);
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("card_number", paymentPingppForDetailDTO.getCardNumber());
        extra.put("user_name", paymentPingppForDetailDTO.getUserName());
        extra.put("open_bank_code", paymentPingppForDetailDTO.getBankCode());
        transferMap.put("extra", extra);
        try {
            logger.info("代付发送的报文：{}", JsonUtils.toJsonString(transferMap));
            transfer = Transfer.create(transferMap);
            transferString = transfer.toString();
            logger.info("代付接收的报文：{}", transferString);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (InvalidRequestException e) {
            exceptionString = e.getMessage();
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (APIConnectionException e) {
            exceptionString = e.getMessage();
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (APIException e) {
            exceptionString = e.getMessage();
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (ChannelException e) {
            exceptionString = e.getMessage();
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (RateLimitException e) {
            exceptionString = e.getMessage();
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } finally {
            try {
                SubAccountDetailPaymentPingppDomain paymentPingppDomain = new SubAccountDetailPaymentPingppDomain();
                paymentPingppDomain.setSubAccountDetailPaymentId(paymentPingppForDetailDTO.getPaymentId());
                paymentPingppDomain.setSubAccountDetailPaymentSn(paymentPingppForDetailDTO.getPaymentSn());
                paymentPingppDomain.setPingppRequest(JsonUtils.toJsonString(transferMap));
                paymentPingppDomain.setPingppResponse(StringUtils.isEmpty(transferString) ? exceptionString : transferString);
                paymentPingppDomain.setOperateType(StringUtils.isEmpty(transferString) ? CustomerPaymentPingppOperateTypeEnum.None.getValue() : CustomerPaymentPingppOperateTypeEnum.POST.getValue());
                paymentPingppDomain.setPingppId(transfer.getId());
                this.create(paymentPingppDomain);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("添加pingpp记录失败");
            }
        }
        return transfer;
    }

    @Override
    public Transfer getTransferByPaymentId(Long paymentId) {
        SubAccountDetailPaymentPingppDomain subAccountDetailPaymentPingppDomain = this.findByPaymentId(paymentId);
        String transferString = "";
        String exceptionString = "";
        Transfer transfer = null;
        try {
            transfer = Transfer.retrieve(subAccountDetailPaymentPingppDomain.getPingppId());
            transferString = transfer.toString();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (APIException e) {
            e.printStackTrace();
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (ChannelException e) {
            e.printStackTrace();
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } catch (RateLimitException e) {
            e.printStackTrace();
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } finally {
            try {
                SubAccountDetailPaymentPingppDomain paymentPingppDomain = new SubAccountDetailPaymentPingppDomain();
                paymentPingppDomain.setId(subAccountDetailPaymentPingppDomain.getId());
                paymentPingppDomain.setPingppRequest(JsonUtils.toJsonString(paymentId));
                paymentPingppDomain.setPingppResponse(StringUtils.isEmpty(transferString) ? exceptionString : transferString);
                paymentPingppDomain.setOperateType(StringUtils.isEmpty(transferString) ? CustomerPaymentPingppOperateTypeEnum.None.getValue() : CustomerPaymentPingppOperateTypeEnum.GET.getValue());
                this.merge(paymentPingppDomain);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("添加pingpp记录失败");
            }
        }
        return transfer;
    }

    @Override
    public SubAccountDetailPaymentPingppDomain findByPaymentId(Long paymengId) {
        SubAccountDetailPaymentPingppDomain subAccountDetailPaymentPingppDomain = new SubAccountDetailPaymentPingppDomain();
        subAccountDetailPaymentPingppDomain.setSubAccountDetailPaymentId(paymengId);
        return this.getSingleByBeanProp(subAccountDetailPaymentPingppDomain);
    }

    @Override
    public SubAccountDetailPaymentPingppDomain findByPingppIdAndOperateType(String pingppId, CustomerPaymentPingppOperateTypeEnum typeEnum) {
        SubAccountDetailPaymentPingppDomain subAccountDetailPaymentPingppDomain = new SubAccountDetailPaymentPingppDomain();
        subAccountDetailPaymentPingppDomain.setPingppId(pingppId);
        subAccountDetailPaymentPingppDomain.setOperateType(typeEnum.getValue());
        return this.getSingleByBeanProp(subAccountDetailPaymentPingppDomain);
    }

    /**
     * @param chargeId
     * @param description
     * @param amount
     * @return
     */
    @Override
    public Refund createRefund(Long paymentId, String paymentSn, String chargeId, String description, BigDecimal amount) {
        AssertUtil.notNull(chargeId, "chargeId不能为空");
        AssertUtil.notNull(description, "description不能为空");
        if (BigDecimalUtil.lte(amount, BigDecimal.ZERO)) {
            throw new ApiException("退款金额不能小于0");
        }
        // 退款的金额, 单位为对应币种的最小货币单位，例如：人民币为分（如退款金额为 1 元，此处请填 100）。必须小于等于可退款金额，默认为全额退款
        amount = BigDecimalUtil.getTwoDecimalRound(amount).multiply(new BigDecimal(100));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("description", description);
        params.put("amount", amount);
        Refund refund = null;
        String exceptionString = "";
        String refundString = "";
        try {
            refund = Refund.create(chargeId, params);
            refundString = refund.toString();
            logger.info("退款请求返回：" + refundString);
        } catch (Exception e) {
            logger.error("退款错误：", e);
            exceptionString = e.getMessage();
            throw new ApiException(ErrorCodeEnum.JsonError, e.getMessage());
        } finally {
            try {
                SubAccountDetailPaymentPingppDomain paymentPingppDomain = new SubAccountDetailPaymentPingppDomain();
                paymentPingppDomain.setSubAccountDetailPaymentId(paymentId);
                paymentPingppDomain.setSubAccountDetailPaymentSn(paymentSn);
                params.put("chargeId", chargeId);
                paymentPingppDomain.setPingppRequest(JsonUtils.toJsonString(params));
                paymentPingppDomain.setPingppId(refund.getId());
                paymentPingppDomain.setPingppResponse(StringUtils.isEmpty(refundString) ? exceptionString : refundString);
                paymentPingppDomain.setOperateType(StringUtils.isEmpty(refundString) ? CustomerPaymentPingppOperateTypeEnum.None.getValue() : CustomerPaymentPingppOperateTypeEnum.POST.getValue());
                this.create(paymentPingppDomain);
            } catch (Exception e) {
                logger.error("添加pingpp记录失败");
            }
        }
        return refund;
    }
}
