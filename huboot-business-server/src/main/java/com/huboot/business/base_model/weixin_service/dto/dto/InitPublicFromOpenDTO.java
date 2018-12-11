package com.huboot.business.base_model.weixin_service.dto.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/2/3 0003.
 */
@Data
public class InitPublicFromOpenDTO {

    @ApiModelProperty("shopUid")
    private String shopUid;
    @ApiModelProperty("授权认证码")
    private String authorizationCode;
    @ApiModelProperty("授权类型：1-公众号，2-小程序")
    private String type;
}
