package com.huboot.business.base_model.pay.service.account.serial.impl;


import com.huboot.business.base_model.pay.dao.account.serial.ISerialNumberDao;
import com.huboot.business.base_model.pay.domain.account.serial.SerialNumberDomain;
import com.huboot.business.base_model.pay.enums.SerialNumberTypeEnum;
import com.huboot.business.base_model.pay.service.account.serial.ISerialNumberService;
import com.huboot.business.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 字典值ServiceImpl
 */
@Service("serialNumberServiceImpl")
public class SerialNumberServiceImpl implements ISerialNumberService {

    private HiloOptimizer inquirySheetHiloOptimizer;
    private HiloOptimizer offerSheetHiloOptimizer;
    private HiloOptimizer orderHiloOptimizer;
    private HiloOptimizer paymentHiloOptimizer;
    private HiloOptimizer shopHiloOptimizer;
    private HiloOptimizer companyHiloOptimizer;
    private HiloOptimizer riskOrderHiloOptimizer;
    private HiloOptimizer accountDetailHiloOptimizer;
    private HiloOptimizer directClientOrderlHiloOptimizer;
    private HiloOptimizer miniRiskOrderOptimizer;

    // 会员询价单
    private String inquirySheetPrefix = "X";
    private int inquirySheetMaxLo = 1000000000;
    // 会员报价单
    private String offerSheetPrefix = "B";
    private int offerSheetMaxLo = 1000000000;
    // 会员订单
    private String orderPrefix = "D";
    private int orderMaxLo = 1000000000;
    // 会员支付单--单独定制位数少一些16位（包含首字母）
    private String paymentPrefix = "Z";
    private int paymentMaxLo = 10000000;
    // 会员店铺
    private String shopPrefix = "F";
    private int shopMaxLo = 1000000000;
    // 会员公司
    private String companyPrefix = "G";
    private int companyMaxLo = 1000000000;
    // 风控订单
    private String riskOrderPrefix = "F";
    private int riskOrderMaxLo = 1000000000;
    // 风控订单
    private String accountDetailPrefix = "A";
    private int accountDetailMaxLo = 1000000000;
    // 直客订单
    private String directClientOrderPrefix = "ZK";
    private int directClientOrderMaxLo = 1000000000;

    // 风控小程序订单
    private String miniRiskOrderPrefix = "MR";
    private int miniRiskOrderMaxLo = 1000000000;


    @Autowired
    ISerialNumberDao serialNumberDao;

    public SerialNumberServiceImpl() {
        inquirySheetHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.InquirySheet, inquirySheetPrefix, inquirySheetMaxLo);
        offerSheetHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.OfferSheet, offerSheetPrefix, offerSheetMaxLo);
        orderHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.Order, orderPrefix, orderMaxLo);
        paymentHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.Payment, paymentPrefix, paymentMaxLo);
        shopHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.Shop, shopPrefix, shopMaxLo);
        companyHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.Company, companyPrefix, companyMaxLo);
        riskOrderHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.RiskOrder, riskOrderPrefix, riskOrderMaxLo);
        accountDetailHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.AccountDetail, accountDetailPrefix, accountDetailMaxLo);
        directClientOrderlHiloOptimizer = new HiloOptimizer(SerialNumberTypeEnum.DirectClientOrder, directClientOrderPrefix, directClientOrderMaxLo);
        miniRiskOrderOptimizer = new HiloOptimizer(SerialNumberTypeEnum.MiniRiskOrder, miniRiskOrderPrefix, miniRiskOrderMaxLo);
    }

    @Override
    public String generate(SerialNumberTypeEnum serialNumberType) {
        Assert.notNull(serialNumberType);
        if (serialNumberType == SerialNumberTypeEnum.InquirySheet) {
            return inquirySheetHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.OfferSheet) {
            return offerSheetHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.Order) {
            return orderHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.Payment) {
            return paymentHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.Shop) {
            return shopHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.Company) {
            return companyHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.RiskOrder) {
            return riskOrderHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.AccountDetail) {
            return accountDetailHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.DirectClientOrder) {
            return directClientOrderlHiloOptimizer.generate();
        } else if (serialNumberType == SerialNumberTypeEnum.MiniRiskOrder) {
            return miniRiskOrderOptimizer.generate();
        }

        return null;
    }

    @Transactional
    private long getLastValue(SerialNumberTypeEnum serialNumberType) {
        SerialNumberDomain serialNumberQuery = new SerialNumberDomain();
        serialNumberQuery.setType(serialNumberType.getValue());
        List<SerialNumberDomain> domains = serialNumberDao.findByBeanProp(serialNumberQuery);
        SerialNumberDomain sn = domains != null ? domains.get(0) : null;
        if (sn != null) {
            Integer lastValue = sn.getLastValue();
            sn.setLastValue(lastValue + 1);
            serialNumberDao.update(sn);
            return lastValue;
        } else {
            return 0;
        }
    }

    /**
     * 高低位算法
     */
    private class HiloOptimizer {

        private SerialNumberTypeEnum serialNumberType;
        private String prefix;
        private int maxLo;
        private int lo;
        private long hi;
        private long lastValue;

        public HiloOptimizer(SerialNumberTypeEnum serialNumberType, String prefix, int maxLo) {
            this.serialNumberType = serialNumberType;
            if (prefix != null) {
                this.prefix = prefix;
            } else {
                this.prefix = "";
            }
            this.maxLo = maxLo;
            this.lo = maxLo + 1;
        }

        public synchronized String generate() {
            if (lo > maxLo) {
                lastValue = getLastValue(serialNumberType);
                lo = lastValue == 0 ? 1 : 0;
                hi = lastValue * (maxLo + 1);
            }

            return prefix + DateUtil.getCurrentDateYYMMDD() + (hi + lo++);
        }

        // 生成唯一的数字序列号
        public synchronized String generateSerialNumber() {
            if (lo > maxLo) {
                lastValue = getLastValue(serialNumberType);
                lo = lastValue == 0 ? 1 : 0;
                hi = lastValue * (maxLo + 1);
            }

            return DateUtil.getCurrentDateYYMMDD() + (hi + lo++);
        }

    }
}
