package com.huboot.share.account_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@AllArgsConstructor
@Getter
public enum AccountTypeEnum implements BaseEnum {

    person("个人"),// relaId 关联userId
    company("公司"), // relaId 关联campanyId
    ;

    private String showName;

    @Override
    public String getShowName() {
        return showName;
    }

}
