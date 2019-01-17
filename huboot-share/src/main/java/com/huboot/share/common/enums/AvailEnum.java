package com.huboot.share.common.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum AvailEnum implements BaseEnum {

    effective("有效"),
    invalid("无效"),
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }

}
