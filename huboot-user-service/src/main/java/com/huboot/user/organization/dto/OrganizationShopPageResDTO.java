package com.huboot.user.organization.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-部门店铺表
 */
@Data
public class OrganizationShopPageResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
	@ApiModelProperty("组织主ID")
	private Long organizationId ;
	@ApiModelProperty("店铺经营模式-枚举")
	private String businessType ;
	@ApiModelProperty("服务窗编号")
	private String sn ;
	@ApiModelProperty("logo原图路径")
	private String logoPath ;
	@ApiModelProperty("地区")
	private Long areaId ;
	@ApiModelProperty("联系地址")
	private String address ;
	@ApiModelProperty("联系人")
	private String contract ;
	@ApiModelProperty("联系电话")
	private String phone ;
	@ApiModelProperty("店铺状态-枚举")
	private String status ;
	@ApiModelProperty("所属系统-枚举")
	private String system ;

}

