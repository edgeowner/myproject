package com.huboot.user.organization.dto.zkshop;

import java.io.Serializable;

import com.huboot.share.user_service.enums.OrganizationShopStoreTypeEnum;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *直客商家端-组织-店铺门店表
 */
@Data
public class ZkshopOrganizationShopStoreModifyReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一标识", hidden = true)
	private Long id;
	@ApiModelProperty("类型-枚举:store-门店,take_car-提车点,other-其他,")
	private OrganizationShopStoreTypeEnum type ;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("地区")
	private Long areaId;
	@ApiModelProperty("地址")
	private String address;

}

