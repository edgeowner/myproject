package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *小程序发布记录
 */
@Data
public class MiniappReleaseLogPagerDTO implements Serializable {

	@ApiModelProperty("发布记录ID")
	private Long logId ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("小程序AppId")
	private String miniappId ;
	@ApiModelProperty("小程序名称")
	private String miniappName ;
	@ApiModelProperty("发布版本")
	private String userVersion ;
	@ApiModelProperty("代码模板id")
	private String templateId ;
	@ApiModelProperty("状态")
	private String status ;
	@ApiModelProperty("状态")
	private String statusName ;
	@ApiModelProperty("审核ID")
	private String auditId;


}

