package com.huboot.business.base_model.ali_service.dto.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class DisableTemplateReqDTO implements Serializable {



    //直客店铺id
    @ApiModelProperty("直客店铺id")
    @NotEmpty(message = "直客店铺id")
    private String shopUid;

    //模板id
    @ApiModelProperty("模板id")
    @NotEmpty(message = "模板id不能为空")
    private String templateId;


}
