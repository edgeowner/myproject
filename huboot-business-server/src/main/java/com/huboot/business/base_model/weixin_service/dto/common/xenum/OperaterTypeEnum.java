package com.huboot.business.base_model.weixin_service.dto.common.xenum;

/**
 * Created by Administrator on 2018/7/30 0030.
 */
public enum OperaterTypeEnum {

    None(0, "无", 0),
    buyer(1, "买家", 1),
    seller(2, "商家", 2),
    system(3, "系统", 3),
    ;

    //值
    private Integer value;
    //显示名称
    private String showName;
    //序列号
    private Integer seqNo;

    OperaterTypeEnum(Integer value, String showName, Integer seqNo) {
        this.value = value;
        this.showName = showName;
        this.seqNo = seqNo;
    }

    public static OperaterTypeEnum valueOf(int value) {
        for (OperaterTypeEnum s : OperaterTypeEnum.values()) {
            if (s.value.equals(value))
                return s;
        }
        throw new IllegalArgumentException(String.format("值%s不能转换成枚举", value));
    }

    public String getShowName() {
        return showName;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getSeqNo() {
        return seqNo;
    }
}
