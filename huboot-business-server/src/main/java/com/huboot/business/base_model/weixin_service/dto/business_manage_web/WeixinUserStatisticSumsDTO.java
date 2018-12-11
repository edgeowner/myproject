package com.huboot.business.base_model.weixin_service.dto.business_manage_web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
@Data
public class WeixinUserStatisticSumsDTO {

    @ApiModelProperty("净增用户数量=new_user减去cancel_user")
    private Integer addUser = 0;

    @ApiModelProperty("总用户量")
    private Integer cumulateUser = 0;
}
