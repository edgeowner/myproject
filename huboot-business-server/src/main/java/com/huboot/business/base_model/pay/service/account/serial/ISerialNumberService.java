package com.huboot.business.base_model.pay.service.account.serial;


import com.huboot.business.base_model.pay.enums.SerialNumberTypeEnum;

/**
 *字典值Service
 */
public interface ISerialNumberService {

    /**
     * 生成序列号
     * @param serialNumberType
     * @return
     */
    String generate(SerialNumberTypeEnum serialNumberType);

}
