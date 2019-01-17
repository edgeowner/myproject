package com.huboot.share.common.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum AbleEnum implements BaseEnum {

    enable("启用"),
    disable("禁用"),
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }

}
