package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
@Data
public class ReleaseLogDetailDTO {

    @ApiModelProperty("发布记录ID")
    private Integer logId ;

    @ApiModelProperty("店铺名称")
    private String shopName ;

    @ApiModelProperty("小程序名称")
    private String miniappName ;

    @ApiModelProperty("小程序UID")
    private String miniappUid ;

    @ApiModelProperty("创建时间")
    private String createTime ;

    @ApiModelProperty("状态")
    private String statusName ;

    @ApiModelProperty("templateId")
    private Integer templateId ;

    @ApiModelProperty("用户版本")
    private String userVersion ;

    @ApiModelProperty("版本描述")
    private String userDesc ;

    @ApiModelProperty("代码提交参数")
    private String commitCodeParameter;

    @ApiModelProperty("提交代码结果")
    private String commitCodeResult ;

    @ApiModelProperty("提交审核参数")
    private String commitCheckParameter ;

    @ApiModelProperty("提交审核结果")
    private String commitCheckResult ;

    @ApiModelProperty("审核编号")
    private String auditId ;

    @ApiModelProperty("审核结果")
    private String auditResult ;

    @ApiModelProperty("审核通过之后是否自动发布")
    private String releaseAfterAudit ;

    @ApiModelProperty("发布时间")
    private String releaseTime ;

    @ApiModelProperty("发布结果")
    private String releaseResult ;

}
