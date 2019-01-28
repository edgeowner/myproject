package com.huboot.share.account_service.api.dto.yibao;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateYiBaoAccountReqDTO implements Serializable {

    public static final String REG_TYPE_PERSION = "person";//个人资质

    public static final String REG_TYPE_COMPANY = "company";//企业资质

    @ApiModelProperty("结算账户id")
    @NotNull(message = "结算账户id不能为空")
    private Long accountId;

    @ApiModelProperty("结算账户id")
    @NotNull(message = "结算账户id不能为空")
    private String regType = REG_TYPE_PERSION;



}
