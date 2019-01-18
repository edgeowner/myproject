package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *小程序发布记录
 */
@Data
public class MiniappReleaseLogDetail implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("createTime")
	private LocalDateTime createTime ;
	@ApiModelProperty("miniapp_id")
	private String miniappId ;
	@ApiModelProperty("miniapp_id")
	private String miniappName ;
	@ApiModelProperty("代码模板id")
	private String templateId ;
	@ApiModelProperty("代码版本号，开发者可自定义")
	private String userVersion ;
	@ApiModelProperty("代码描述，开发者可自定义")
	private String userDesc ;
	@ApiModelProperty("状态")
	private String status ;
	@ApiModelProperty("状态")
	private String statusName ;
	@ApiModelProperty("小程序提交代码扩展配置参数")
	private String commitCodeParameter ;
	@ApiModelProperty("提交代码结果")
	private String commitCodeResult ;
	@ApiModelProperty("提交审核结果")
	private String commitAuditResult ;
	@ApiModelProperty("审核编号")
	private String auditId ;
	@ApiModelProperty("审核结果")
	private String auditResult ;
	@ApiModelProperty("审核通过之后是否自动发布")
	private String releaseAfterAudit ;
	@ApiModelProperty("审核通过之后是否自动发布")
	private String releaseAfterAuditName ;
	@ApiModelProperty("发布结果")
	private String releaseResult ;

}

