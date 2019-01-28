package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PayMethodEnum implements BaseEnum {

    //交易
    online("线上"),
    offline("线下"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
