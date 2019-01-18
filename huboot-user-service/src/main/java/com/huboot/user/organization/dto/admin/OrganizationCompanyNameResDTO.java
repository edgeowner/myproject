package com.huboot.user.organization.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *用户服务-公司表
 */
@Data
public class OrganizationCompanyNameResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("组织主ID")
	private Long organizationId ;
	@ApiModelProperty("公司名称")
	private String name ;
	@ApiModelProperty("简称")
	private String abbreviation ;

}

