package com.huboot.commons.sso;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionResourcesDTO implements Serializable {
    //url，支持Ant
    private List<String> url;
    //请求方法-枚举
    private List<String> method;
}
