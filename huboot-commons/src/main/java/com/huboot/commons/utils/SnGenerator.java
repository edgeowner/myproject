package com.huboot.commons.utils;

import com.huboot.commons.utils.keyGenerator.PrimaryKeyGenerator;

public class SnGenerator {

    private static final String USERNAME_SN_PREFIX = "U";//用户服务-用户名
    private static final String ORGANIZATION_COMPANY_SN_PREFIX = "OC";//用户服务-组织架构公司
    private static final String ORGANIZATION_SHOP_SN_PREFIX = "OS";//用户服务-组织架构店铺
    private static final String PAYMENT_ORDER_SN_PREFIX = "PA";//账户服务，支付订单
    private static final String MARKETING_PROMOTION_SN_PREFIX = "S";// 营销渠道
    private static final String MARKETING_GENERALIZE_SN_PREFIX = "G";// 营销渠道
    private static final String RISKCONTROL_REPORT_SN_PREFIX = "RC";// 风控服务-报告
    private static final String ORDER_SN_PREFIX = "ZK";//直客订单

    /**
     * 用户名
     *
     * @return
     */
    public static String generatorUsername() {
        return USERNAME_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 用户服务-组织架构公司
     *
     * @return
     */
    public static String generatorOrganizationCompany() {
        return ORGANIZATION_COMPANY_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 用户服务-组织架构店铺
     *
     * @return
     */
    public static String generatorOrganizationShop() {
        return ORGANIZATION_SHOP_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 账户服务，支付订单
     *
     * @return
     */
    public static String generatorPaymentOrder() {
        return PAYMENT_ORDER_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 营销渠道
     *
     * @return
     */
    public static String generatorPromotion() {
        return MARKETING_PROMOTION_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 营销渠道
     *
     * @return
     */
    public static String generatorGeneralize() {
        return MARKETING_GENERALIZE_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 风控报告
     *
     * @return
     */
    public static String generatorRiskcontrolReport() {
        return RISKCONTROL_REPORT_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

    /**
     * 直客订单
     *
     * @return
     */
    public static String generatorZKOrder() {
        return ORDER_SN_PREFIX + String.valueOf(PrimaryKeyGenerator.SEQUENCE.next());
    }

}
