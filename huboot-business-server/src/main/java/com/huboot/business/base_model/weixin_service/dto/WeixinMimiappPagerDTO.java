package com.huboot.business.base_model.weixin_service.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/6 0006.
 */
@Data
public class WeixinMimiappPagerDTO {

    //商户名称
    private String shopName;
    //欲发版本
    private String userVersion;
    //审核状态
    private Integer checkStatus;
    //审核状态
    private String checkStatusName;
    //失败原因
    private String failureReason;
    //欲发时间
    private String createTime;
}
