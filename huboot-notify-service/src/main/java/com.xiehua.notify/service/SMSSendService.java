package com.xiehua.notify.service;

import com.xiehua.notify.dto.SMSSendResult;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
public interface SMSSendService {


    SMSSendResult sendRequest(String phones, String sign, String content, String type);

}
