package com.huboot.share.notify_service.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Data
public class SMSSendDTO {

    //场景码，方便记录查询，自己随便定义,可传可不穿
    private String code;

    private String sign = "【租车盒子】";

    private String content;

    private List<String> phoneList = new ArrayList<>();

    private String remark;

    public void addPhone(String phone) {
        phoneList.add(phone);
    }

    public void addPhone(List<String> phones) {
        phoneList.addAll(phones);
    }
}
