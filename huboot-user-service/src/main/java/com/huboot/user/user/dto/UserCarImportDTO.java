package com.huboot.user.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCarImportDTO implements Serializable {
    // 电话
    private String phone;
    // 姓名
    private String name;
    // 车牌号
    private String pn;

}
