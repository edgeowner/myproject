package com.huboot.share.notify_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum SMSSendStatusEnum implements BaseEnum {

    none("未发送"),
    success("发送成功"),
    failure("发送失败"),
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }

}
