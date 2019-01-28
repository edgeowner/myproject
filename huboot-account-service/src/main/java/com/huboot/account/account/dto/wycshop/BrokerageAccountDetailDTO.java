package com.huboot.account.account.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/14 0014.
 */
@Data
public class BrokerageAccountDetailDTO {

    @ApiModelProperty("姓名")
    private String name ;
    @ApiModelProperty("手机")
    private String phone ;
    @ApiModelProperty("身份证")
    private String idcard ;
    @ApiModelProperty("总余额")
    private String totalBalance ;
}
