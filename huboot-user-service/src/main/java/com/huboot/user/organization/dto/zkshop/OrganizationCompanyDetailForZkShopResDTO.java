package com.huboot.user.organization.dto.zkshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *用户服务-公司表
 */
@Data
public class OrganizationCompanyDetailForZkShopResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
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
	@ApiModelProperty("身份证正面路径")
	private String idcardFacePath ;
	@ApiModelProperty("身份证反面路径")
	private String idcardBackPath ;
}

