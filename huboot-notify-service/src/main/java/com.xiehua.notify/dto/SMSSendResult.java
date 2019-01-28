package com.xiehua.notify.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
@Data
public class SMSSendResult {

    private boolean success;
    //
    private String response;
    //
    private String errorReason;
    //
    private List<String> successPhoneList = new ArrayList<>();
    //
    private List<String> failurePhoneList = new ArrayList<>();
}
