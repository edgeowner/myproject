package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
@Data
public class MiniappPagerDTO {

    @ApiModelProperty("直客店铺名称")
    private String shopName ;

    @ApiModelProperty("小程序名称")
    private String miniappName ;

    @ApiModelProperty("小程序UID")
    private String miniappUid ;

    @ApiModelProperty("绑定模式")
    private String bindTypeName ;

    @ApiModelProperty("小程序是否授权")
    private Integer miniappAuth ;

    @ApiModelProperty("是否全网发布过")
    private Integer releaseOnLine ;

    @ApiModelProperty("是否全网发布过")
    private String releaseOnLineName ;

    @ApiModelProperty("小程序是否授权")
    private String miniappAuthName ;

    @ApiModelProperty("公众号的主体名称")
    private String principalName ;

    @ApiModelProperty("备注")
    private String remark ;

}
