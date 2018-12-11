package com.huboot.business.base_model.login.sso.client.dto;

/**
 * 上传文件业务类型
 */
public enum UploadFileBusinessTypeEnum {

    QRCode(1, "二维码", 1), //
    ;

    //值
    private Integer value;
    //显示名称
    private String showName;
    //序列号
    private Integer seqNo;

    UploadFileBusinessTypeEnum(Integer value, String showName, Integer seqNo) {
        this.value = value;
        this.showName = showName;
        this.seqNo = seqNo;
    }

    public static UploadFileBusinessTypeEnum valueOf(int value) {
        for (UploadFileBusinessTypeEnum s : UploadFileBusinessTypeEnum.values()) {
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
