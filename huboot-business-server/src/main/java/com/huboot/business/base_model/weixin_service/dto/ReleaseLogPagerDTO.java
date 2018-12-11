package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
@Data
public class ReleaseLogPagerDTO {

    @ApiModelProperty("发布记录ID")
    private Integer logId ;

    @ApiModelProperty("小程序名称")
    private String miniappName ;

    @ApiModelProperty("小程序UID")
    private String miniappUid ;

    @ApiModelProperty("创建时间")
    private String createTime ;

    @ApiModelProperty("状态")
    private Integer status ;

    @ApiModelProperty("状态")
    private String statusName ;

    @ApiModelProperty("templateId")
    private Integer templateId ;

    @ApiModelProperty("用户版本")
    private String userVersion ;

    @ApiModelProperty("版本描述")
    private String userDesc ;

}
