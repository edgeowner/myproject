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
public class ZkshopUserEmployeeModifyReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一标识", hidden = true)
	private Long id;
	@ApiModelProperty("名称(非实名认证)")
	private String name;
	@ApiModelProperty("角色Id")
	private Long roleId;
}

