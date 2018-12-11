package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
@Data
public class WexinPublicPagerDTO {

    @ApiModelProperty("直客店铺名称")
    private String shopName ;

    @ApiModelProperty("公众号名称")
    private String publicName ;

    @ApiModelProperty("公众号UID")
    private String publicUid ;

    @ApiModelProperty("绑定模式")
    private String bindTypeName ;

    @ApiModelProperty("公众号是否授权")
    private Integer publicAuth ;

    @ApiModelProperty("公众号是否授权")
    private String publicAuthName ;

    @ApiModelProperty("公众号的主体名称")
    private String principalName ;

    @ApiModelProperty("备注")
    private String remark ;

}
