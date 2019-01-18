package com.huboot.user.common.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserSmsEnum implements BaseEnum {

    login("尊敬的客户，您的短信验证码是{0}，10分钟内有效，请勿泄露，并在有效期内完成操作。"),
    ;

    private String showName;
}
