package com.huboot.user.auth.dto.zkshop;

import com.huboot.share.user_service.enums.OrganizationCompanyStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ZkShopLoginInfoResDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    private Long userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("名称(非实名认证)")
    private String name;
    @ApiModelProperty("店铺id")
    private Long shopId;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("审核状态-枚举:auditing(\"审核中\"),    audited(\"已审核\"),    returned(\"已退回\"),")
    private OrganizationCompanyStatusEnum auditStatus;
    @ApiModelProperty("公司所在省份简称")
    private String areaShortName;
    @ApiModelProperty("用户菜单显示")
    private List<String> permission;
}
