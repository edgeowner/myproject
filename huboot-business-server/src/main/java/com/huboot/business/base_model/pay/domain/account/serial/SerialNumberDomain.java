package com.huboot.business.base_model.pay.domain.account.serial;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*字典值
*/
public class SerialNumberDomain extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //最后的值
    private Integer lastValue ;
    //单据序列号类型,code=SERIAL_NUMBER_TYPE
    private Integer type ;
                
    public Integer getLastValue(){
        return this.lastValue;
    }

    public void setLastValue(Integer  lastValue){
        this.lastValue = lastValue;
    }
        
    public Integer getType(){
        return this.type;
    }

    public void setType(Integer  type){
        this.type = type;
    }
                
}

