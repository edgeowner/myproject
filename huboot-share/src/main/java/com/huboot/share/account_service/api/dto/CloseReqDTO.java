package com.huboot.share.account_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CloseReqDTO implements Serializable {

    @NotBlank(message = "支付单Sn不能为空")
    @ApiModelProperty("支付单Sn")
    private String paymentSn;

}
