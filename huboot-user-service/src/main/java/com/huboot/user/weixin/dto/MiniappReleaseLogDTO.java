package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序发布记录
 */
@Data
public class MiniappReleaseLogDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("miniapp_id")
	private String miniappId ;
	@ApiModelProperty("代码模板id")
	private String templateId ;
	@ApiModelProperty("状态")
	private String status ;
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
	@ApiModelProperty("发布结果")
	private String releaseResult ;

}

