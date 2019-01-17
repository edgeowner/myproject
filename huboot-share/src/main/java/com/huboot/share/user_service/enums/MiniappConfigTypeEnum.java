package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MiniappConfigTypeEnum implements BaseEnum {

    server_domain("服务器域名"),
    webview_domain("业务域名"),
    base_library_version("基础库版本"),
    ;

    private String showName;
}
