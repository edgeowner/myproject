package com.huboot.share.notify_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum SMSTaskStatusEnum implements BaseEnum {

    sending("发送中"),
    finish("已完成"),
    failure("失败"),
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }
}
