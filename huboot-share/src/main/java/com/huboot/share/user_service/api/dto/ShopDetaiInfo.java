package com.huboot.share.user_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *用户服务-部门店铺表
 */
@Data
public class ShopDetaiInfo implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("组织主ID")
	private Long organizationId ;
	@ApiModelProperty("店铺经营模式-枚举")
	private String businessType ;
	@ApiModelProperty("服务窗编号")
	private String sn ;
	@ApiModelProperty("服务窗编号")
	private String name ;
	@ApiModelProperty("logo原图路径")
	private String logoPath ;
	@ApiModelProperty("地区")
	private Long areaId ;
	@ApiModelProperty("联系地址")
	private String address ;
	@ApiModelProperty("联系地址")
	private String fullAddress ;
	@ApiModelProperty("联系人")
	private String contract ;
	@ApiModelProperty("联系电话")
	private String phone ;
	@ApiModelProperty("店铺状态-枚举")
	private String status ;

}

