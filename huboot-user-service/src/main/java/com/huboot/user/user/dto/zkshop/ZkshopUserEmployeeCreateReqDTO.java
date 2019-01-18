package com.huboot.user.user.dto.zkshop;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *直客商家端-用户服务-企业员工表
 */
@Data
public class ZkshopUserEmployeeCreateReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("用户ID")
	private Long userId ;
	@ApiModelProperty("员工所属组织ID")
	private Long organizationId ;
	@ApiModelProperty("工号")
	private String jobNumber ;
	@ApiModelProperty("状态-枚举")
	private String status ;

}

