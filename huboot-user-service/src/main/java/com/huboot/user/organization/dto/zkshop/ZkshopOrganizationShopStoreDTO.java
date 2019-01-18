package com.huboot.user.organization.dto.zkshop;

import java.io.Serializable;

import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.enums.OrganizationShopStoreTypeEnum;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *直客商家端-组织-店铺门店表
 */
@Data
public class ZkshopOrganizationShopStoreDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("店铺id")
	private Long shopId ;
	@ApiModelProperty("类型-枚举:store-门店,take_car-提车点,other-其他,")
	private OrganizationShopStoreTypeEnum type ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("地区")
	private Long areaId ;
	@ApiModelProperty("地址")
	private String address ;
	@ApiModelProperty("默认-枚举:yes是,no否")
	private YesOrNoEnum defaultStatus ;

}

