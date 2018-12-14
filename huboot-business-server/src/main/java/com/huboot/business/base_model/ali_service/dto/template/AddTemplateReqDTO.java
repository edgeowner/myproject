package com.huboot.business.base_model.ali_service.dto.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AddTemplateReqDTO implements Serializable {

    //短信节点
    @ApiModelProperty("短信节点")
    @Min(value = 100,message = "短信节点不合法")
    private Integer node;

    //模板内容
    @ApiModelProperty("模板内容")
    @NotEmpty(message = "模板内容不能为空")
    private String template;

    //系统
    @ApiModelProperty("系统")
    @NotEmpty(message = "系统不能为空")
    private String system;

    //店铺标识
    @ApiModelProperty("店铺标识")
    @NotEmpty(message = "店铺标识不能为空")
    private String shopUid;
}
