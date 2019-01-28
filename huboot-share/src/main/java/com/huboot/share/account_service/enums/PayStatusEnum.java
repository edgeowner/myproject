package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum PayStatusEnum implements BaseEnum {

    //交易
    wait("待操作"),
    failure("失败"),
    success("成功"),
    cancel("取消"),
    ;

    private String showName;


    @Override
    public String getShowName() {
        return showName;
    }

}
