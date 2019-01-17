package com.huboot.share.common.enums;


import com.huboot.commons.enums.BaseEnum;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
public enum YesOrNoEnum implements BaseEnum {

    yes("是"),
    no("否"),
    ;

    private String showName;

    YesOrNoEnum(String showName) {
        this.showName = showName;
    }

    @Override
    public String getShowName() {
        return showName;
    }

    public static YesOrNoEnum nameOf(String name) {
        for (YesOrNoEnum s : YesOrNoEnum.values()) {
            if (s.name().equals(name))
                return s;
        }
        throw new IllegalArgumentException(String.format("值%s不能转换成枚举", name));
    }
}
