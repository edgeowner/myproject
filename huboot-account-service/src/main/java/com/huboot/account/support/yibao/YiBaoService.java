package com.huboot.account.support.yibao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.commons.component.exception.BizException;
import com.huboot.share.account_service.api.dto.yibao.bakabce.YiBaoBalanceRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.CashierRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.PaySuccessRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.YiBaoPayCloseRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.YibaoPayBaseRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp2DTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundRespDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegCompanyRespDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegPersionRespDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegStatusRespDTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideRespDTO;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.client.YopRsaClient;
import com.yeepay.g3.sdk.yop.config.AppSdkConfigProvider;
import com.yeepay.g3.sdk.yop.config.AppSdkConfigProviderRegistry;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;


@Service
@Slf4j
public class YiBaoService {

    public static final Integer YIBAO_ORDER_TIMEOUT = 30 * 1;//单位：分钟，默认 24 小时，最小 1 分钟，1 最大 180 天

    public static final String YIBAO_FAIL = "FAILURE";

    public static final String YIBAO_SUCCESS = "SUCCESS";

    public static final String YIBAO_SUCCESS_CODE_PAY = "OPR00000";

    public static final String YIBAO_SUCCESS_CODE_CASHIER = "CAS00000";

    public static final String YIBAO_SUCCESS_CODE_REG = "REG00000";

    //验签顺序
    public static final String[] TRADEORDER_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "orderAmount", "notifyUrl"};

    public static final String[] ORDERQUERY_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "uniqueOrderNo"};

    public static final String[] REFUND_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "uniqueOrderNo", "refundRequestId", "refundAmount"};

    public static final String[] REFUNDQUERY_HMAC = {"parentMerchantNo", "merchantNo", "refundRequestId", "orderId", "uniqueRefundNo"};

    public static final String[] MULTIORDERQUERY_HMAC = {"parentMerchantNo", "merchantNo", "requestDateBegin", "requestDateEnd", "pageNo", "pageSize"};

    public static final String[] ORDERCLOSE_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "uniqueOrderNo"};

    public static final String[] SETTLEMENTSQUERY_HMAC = {"parentMerchantNo", "merchantNo", "startSettleDate", "endSettleDate"};

    public static final String[] TRADEDIVIDE_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "uniqueOrderNo", "divideRequestId"};

    public static final String[] TRADEDIVIDEQUERY_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "uniqueOrderNo", "divideRequestId"};

    public static final String[] TRADEFULLSETTLE_HMAC = {"parentMerchantNo", "merchantNo", "orderId", "uniqueOrderNo"};

    //支付方式
    public static final String[] CASHIER = {"merchantNo","token","timestamp","directPayType","cardType","userNo","userType","ext"};

    public static final String[] APICASHIER = {"token","payTool","payType","userNo","userType","appId","openId","payEmpowerNo","merchantTerminalId","merchantStoreNo","userIp","version"};


    @Autowired
    private ObjectMapper mapper;

    @Value("${yibao.parentMerchantNo}")
    private String parentMerchantNo;//平台编号

    @Value("${yibao.merchantNo}")
    private String merchantNo;//收单商户编号

    @Value("${yibao.host}")
    private String yibaoHost;//易宝host

    @Value("${yibao.service.hmackeyqueryURI}")
    private String hmackeyqueryURI;//YOP获取子商户密钥URI

    @Value("${yibao.service.personURI}")
    private String personURI;//个人入驻

    @Value("${yibao.service.enterpriseURI}")
    private String enterpriseURI;//企业入驻

    @Value("${yibao.notify.url.reg}")
    private String notifyReg;//入网回调

    @Value("${yibao.notify.url.pay}")
    private String notifyPay;//支付回调

    @Value("${yibao.notify.url.refund}")
    private String notifyRefund;//支付回调

    @Value("${yibao.service.regstatusqueryURI}")
    private String regstatusqueryURI;//商户入网状态查询

    @Value("${yibao.service.tradeOrderURI}")
    private String tradeOrderURI;//YOP订单创建URI

    @Value("${yibao.service.APICASHIER}")
    private String apiCashier;//API收银台

    @Value("${yibao.service.orderQueryURI}")
    private String orderQueryURI;//YOP单笔订单查询URI

    @Value("${yibao.service.orderCloseURI}")
    private String orderCloseURI;//YOP订单关闭URI

    @Value("${yibao.service.balancequeryURI}")
    private String balancequeryURI;//YOP余额查询接口URI

    @Value("${yibao.service.refundURI}")
    private String refundURI;//YOP单笔退款URI

    @Value("${yibao.service.refundQueryURI}")
    private String refundQueryURI;//YOP单笔退款查询URI

    @Value("${yibao.service.tradedivideURI}")
    private String tradedivideURI;//分账

    @Value("${yibao.service.tradedividequeryURI}")
    private String tradedividequeryURI;//分账查询

    private static String YIBAO_CONFIG;

    static {
        ClassPathResource resource = new ClassPathResource("config/yop_sdk_config_default.json");
        try {
            YIBAO_CONFIG = new String(IOUtils.toByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
           log.error("读取配置易宝文件失败:{}",e);
        }
    }

    /**
     * 注册个人商户
     **/
    public YibaoRegPersionRespDTO regPersionMerchant(Map<String, String> reqDTO) throws IOException {
        reqDTO.put("notifyUrl", notifyReg);
        YopResponse response = call(reqDTO, personURI, TRADEORDER_HMAC);
        return response.unmarshal(YibaoRegPersionRespDTO.class);
    }

    /**
     * 注册企业商户
     **/
    public YibaoRegCompanyRespDTO regCompanyMerchantReg(Map<String, String> reqDTO) throws IOException {
        reqDTO.put("notifyUrl", notifyReg);
        YopResponse response = call(reqDTO, enterpriseURI, TRADEORDER_HMAC);
        return response.unmarshal(YibaoRegCompanyRespDTO.class);
    }

    /***
     * 易宝服务调用
     * **/
    private YopResponse call(Map<String, String> reqDTO, String path, String[] paramHmac) throws IOException {

        YopRequest request = buildRequest();

        reqDTO.entrySet().forEach(s -> {
            request.addParam(s.getKey(), s.getValue());
        });

        StringBuffer hmacBuffer = new StringBuffer();
        for (int i = 0; i < paramHmac.length; i++) {
            String key = paramHmac[i];
            hmacBuffer.append(key).append("=").append(reqDTO.get(key)).append("&");
        }
        String hmacStr = hmacBuffer.subSequence(0, hmacBuffer.length() - 1).toString();
        String hmac = Md5Utils.encoderHmacSha256(hmacStr, getMerchantKey());
        request.addParam("hmac", hmac);

        log.info("易宝支付请求:{}", ToStringBuilder.reflectionToString(request));
        YopResponse response = YopRsaClient.post(path, request);
        log.info("易宝支付响应:{}", ToStringBuilder.reflectionToString(response));
        if (YIBAO_FAIL.equals(response.getState())) {
            if (response.getError() != null)
                throw new BizException(response.getError().getMessage(), response.getError().getCode());
            throw new BizException("系统错误");
        }
        return response;
    }

    //获取子商户密钥
    public String getMerchantKey() throws IOException {
        //build  map
        Map<String, String> reqDTO = new HashMap<>();
        reqDTO.put("parentMerchantNo", parentMerchantNo);
        reqDTO.put("merchantNo", merchantNo);
        YopRequest request = buildRequest();
        reqDTO.entrySet().forEach(s -> {
            request.addParam(s.getKey(), s.getValue());
        });
        log.info("易宝支付请求:{}", ToStringBuilder.reflectionToString(request));
        YopResponse response = YopRsaClient.post(hmackeyqueryURI, request);
        log.info("易宝支付响应:{}", ToStringBuilder.reflectionToString(response));
        if (YIBAO_FAIL.equals(response.getState())) {
            if (response.getError() != null)
                throw new BizException(response.getError().getMessage(), response.getError().getCode());
            throw new BizException("系统错误");
        }
        Map<String, String> map = mapper.readValue(response.getStringResult(), new TypeReference<TreeMap<String, String>>() {
        });
        return map.get("merHmacKey");
    }

    private YopRequest buildRequest() throws IOException{
        AppSdkConfigProvider provider = new YibaoSdkConfigProvider(mapper,YIBAO_CONFIG);
        AppSdkConfigProviderRegistry.registerCustomProvider(provider);
        YopRequest request = new YopRequest("OPR:" + parentMerchantNo);
        return request;
    }

    /**
     * 入网信息查询接口
     **/
    public YibaoRegStatusRespDTO reqInfoQuery(String merchantNo) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        YopResponse response = call(map, regstatusqueryURI, TRADEORDER_HMAC);
        YibaoRegStatusRespDTO respDTO = response.unmarshal(YibaoRegStatusRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_REG.equals(respDTO.getReturnCode())) throw new BizException(respDTO.getReturnMsg());
        return respDTO;
    }

    /***
     * 下单
     * **/
    public PaySuccessRespDTO doOrder(String orderId, String orderAmount, String goodsDesc) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderId);
        map.put("orderAmount", orderAmount);
        map.put("notifyUrl", notifyPay);
        map.put("goodsParamExt", goodsDesc);
        map.put("fundProcessType", "DELAY_SETTLE");//DELAY_SETTLE("延迟结算"),REAL_TIME("实时订单");REAL_TIME_DIVIDE（” 实时 分账” ）SPLIT_ACCOUNT_IN("实时拆分入账");
        YopResponse response = call(map, tradeOrderURI, TRADEORDER_HMAC);
        YibaoPayBaseRespDTO baseRespDTO = response.unmarshal(YibaoPayBaseRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(baseRespDTO.getCode())) throw new BizException(baseRespDTO.getMessage(), baseRespDTO.getCode());

        //return  mapper.readValue(response.getResult().toString(), PaySuccessRespDTO.class);
        PaySuccessRespDTO successRespDTO = new PaySuccessRespDTO();
        BeanUtils.populate(successRespDTO, (LinkedHashMap<String, String>) response.getResult());
        return successRespDTO;
    }


    /**
     * 收银台支付接口
     * TODO 错误处理
     */
    public Tuple2<CashierRespDTO, Map<String, String>> pay(String token, String userNo, String openId, String appId, String ip) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("payTool", "MINI_PROGRAM");//支付工具  SCCANPAY（用户扫码支付）MSCANPAY（商户扫码支付）WECHAT_OPENID（公众号支付） ZFB_SHH（支付宝生活号）MINI_PROGRAM（微信小程序）EWALLET（SDK支付）
        map.put("payType", "WECHAT"); //可选枚举：WECHAT：微信 ALIPAY：支付宝 UPOP:银联支付

//        map.put("payTool", "ZFB_SHH");
//        map.put("payType", "ALIPAY");

        map.put("userNo", userNo); //用户标识
        map.put("appId", appId);
        map.put("openId", openId);
        map.put("userIp", ip);
        map.put("version","1.0");
        YopResponse response = call(map, apiCashier, APICASHIER);
        CashierRespDTO cashierRespDTO = response.unmarshal(CashierRespDTO.class);
        if(!YIBAO_SUCCESS_CODE_CASHIER.equals(cashierRespDTO.getCode())) throw new BizException(cashierRespDTO.getMessage(),cashierRespDTO.getCode());
        return new Tuple2<>(cashierRespDTO, map);
    }

    /**
     * 支付结果查询
     */
    public String payResult(String orderId, String uniqueOrderNo) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderId);
        map.put("uniqueOrderNo", uniqueOrderNo);
        YopResponse response = call(map, orderQueryURI, ORDERQUERY_HMAC);
        YibaoPayBaseRespDTO baseRespDTO = mapper.readValue(response.getStringResult(),YibaoPayBaseRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(baseRespDTO.getCode()))  throw new BizException(baseRespDTO.getMessage(), baseRespDTO.getCode());
        return response.getStringResult();
    }


    /**
     * 支付单关闭
     **/
    public YiBaoPayCloseRespDTO payClose(String orderId, String uniqueOrderNo) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderId);
        map.put("uniqueOrderNo", uniqueOrderNo);
        YopResponse response = call(map, orderCloseURI, ORDERCLOSE_HMAC);
        YiBaoPayCloseRespDTO closeRespDTO = response.unmarshal(YiBaoPayCloseRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(closeRespDTO.getCode()))
            throw new BizException(closeRespDTO.getMessage(), closeRespDTO.getCode());
        return closeRespDTO;
    }


    /****
     * 查询账余额
     * ***/
    public YiBaoBalanceRespDTO queryBalance(String merchantSn) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantSn);
        map.put("parentMerchantNo", parentMerchantNo);
        YopResponse response = call(map, balancequeryURI, TRADEORDER_HMAC);
        YiBaoBalanceRespDTO baoBalanceRespDTO = response.unmarshal(YiBaoBalanceRespDTO.class);
        return baoBalanceRespDTO;
    }

    /***
     * 发起退款
     * **/
    public Tuple2<YiBaoRefundRespDTO,Map<String,String>> refund(String orderSn, String refundSn, String uniqueOrderNo, String refundAmount) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderSn);
        map.put("refundRequestId", refundSn);
        map.put("uniqueOrderNo", uniqueOrderNo);
        map.put("refundAmount", refundAmount);
      //  map.put("accountDivided", "DELAY_SETTLE");
        map.put("notifyUrl", notifyRefund);
        YopResponse response = call(map, refundURI, REFUND_HMAC);
        YiBaoRefundRespDTO yiBaoRefundRespDTO = response.unmarshal(YiBaoRefundRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(yiBaoRefundRespDTO.getCode())) throw new BizException(yiBaoRefundRespDTO.getMessage(), yiBaoRefundRespDTO.getCode());
        return new Tuple2<>(yiBaoRefundRespDTO,map);
    }

    /****
     * 退款查询
     * ****/
    public YiBaoRefundResp2DTO refundQuery(String orderSn, String refundSn, String uniqueOrderNo) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderSn);
        map.put("refundRequestId", refundSn);
        map.put("uniqueRefundNo", uniqueOrderNo);
        YopResponse response = call(map, refundQueryURI, TRADEORDER_HMAC);
        YiBaoRefundResp2DTO yiBaoRefundResp2DTO = response.unmarshal(YiBaoRefundResp2DTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(yiBaoRefundResp2DTO.getCode()))
            throw new BizException(yiBaoRefundResp2DTO.getMessage(), yiBaoRefundResp2DTO.getCode());
        return yiBaoRefundResp2DTO;
    }

    /**
     * 分账
     *
     * @param
     **/
    public TradedivideRespDTO tradedivide(String orderId, String uniqueOrderNo, String divideRequestId, String divideDetail) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderId);
        map.put("uniqueOrderNo", uniqueOrderNo);
        map.put("divideRequestId", divideRequestId);
        map.put("divideDetail", divideDetail);
        YopResponse response = call(map, tradedivideURI, TRADEORDER_HMAC);
        TradedivideRespDTO tradedivideRespDTO = response.unmarshal(TradedivideRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(tradedivideRespDTO.getCode()))
            throw new BizException(tradedivideRespDTO.getMessage(), tradedivideRespDTO.getCode());
        return tradedivideRespDTO;
    }

    /**
     * 分账查询
     *
     * @param
     **/
    public TradedivideRespDTO tradedivideQuery(String orderId, String uniqueOrderNo, String divideRequestId) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("merchantNo", merchantNo);
        map.put("parentMerchantNo", parentMerchantNo);
        map.put("orderId", orderId);
        map.put("uniqueOrderNo", uniqueOrderNo);
        map.put("divideRequestId", divideRequestId);
        YopResponse response = call(map, tradedividequeryURI, TRADEORDER_HMAC);
        TradedivideRespDTO tradedivideRespDTO = response.unmarshal(TradedivideRespDTO.class);
        if (!YIBAO_SUCCESS_CODE_PAY.equals(tradedivideRespDTO.getCode()))
            throw new BizException(tradedivideRespDTO.getMessage(), tradedivideRespDTO.getCode());
        return tradedivideRespDTO;
    }


}
