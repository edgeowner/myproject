package com.huboot.user.organization.dto.zkshop;

import com.huboot.share.user_service.enums.OrganizationShopStoreTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 直客商家端-组织-店铺门店表
 */
@Data
public class ZkshopOrganizationShopStoreCreateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("类型-枚举:store-门店,take_car-提车点,other-其他,")
    @NotNull(message = "类型必填")
    private OrganizationShopStoreTypeEnum type;
    @ApiModelProperty("名称")
    @NotNull(message = "名称必填")
    private String name;
    @ApiModelProperty("地区")
    @NotNull(message = "地区必填")
    private Long areaId;
    @ApiModelProperty("地址")
    @NotNull(message = "地址必填")
    private String address;

}

