package com.huboot.user.organization.dto.admin;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-公司表
 */
@Data
public class OrganizationCompanyDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("组织主ID")
	private Long organizationId ;
	@ApiModelProperty("公司编号")
	private String sn ;
	@ApiModelProperty("公司代码")
	private String code ;
	@ApiModelProperty("注册号/统一社会信用代码")
	private String regNum ;
	@ApiModelProperty("公司名称")
	private String name ;
	@ApiModelProperty("简称")
	private String abbreviation ;
	@ApiModelProperty("地区")
	private Long areaId ;
	@ApiModelProperty("地址")
	private String address ;
	@ApiModelProperty("成立时间")
	private LocalDateTime buildTime ;
	@ApiModelProperty("法人")
	private String person ;
	@ApiModelProperty("法人身份证")
	private String personIdcard ;
	@ApiModelProperty("营业执照原图路径")
	private String businessLicensePath ;

}

