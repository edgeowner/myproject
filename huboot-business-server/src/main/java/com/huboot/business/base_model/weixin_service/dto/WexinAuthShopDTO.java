package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
@Data
public class WexinAuthShopDTO {

    @ApiModelProperty("直客店铺名称")
    private String shopName ;

    @ApiModelProperty("直客店铺状态")
    private String shopStatusName ;

    @ApiModelProperty("公众号名称")
    private String publicName ;

    @ApiModelProperty("公众号UID")
    private String publicUid ;

    @ApiModelProperty("公众号是否授权")
    private String publicAuth ;

    @ApiModelProperty("小程序名称")
    private String miniappName ;

    @ApiModelProperty("小程序UID")
    private String miniappUid ;

    @ApiModelProperty("小程序是否授权")
    private String  miniappAuth;
}
