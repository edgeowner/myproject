package com.huboot.user.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DriverImportDTO implements Serializable {
    // 电话
    private String phone;
    // 姓名
    private String name;
    // 性别
    private String sex;
    // 籍贯
    private String birthplace;
    // 住址
    private String liveAddr;
    // 初次领证日期
    private String licGetDate;
    // 身份证号码
    private String num;
    // 准驾车型
    private String licCarModel;
    // 有效期开始时间
    private String startTime;
    // 有效期结束时间
    private String endTime;
    // 紧急联系人
    private String contactName;
    // 紧急联系人电话
    private String contactPhone;
}
