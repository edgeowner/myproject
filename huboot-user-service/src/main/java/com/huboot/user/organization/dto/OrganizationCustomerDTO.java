package com.huboot.user.organization.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *组织-客户部门表
 */
@Data
public class OrganizationCustomerDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("根组织ID")
	private Long organizationId ;
	@ApiModelProperty("名称")
	private String name ;

}

