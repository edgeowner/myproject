package com.huboot.business.base_model.ali_service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class PromotionBatchReqDTO implements Serializable {

    @NotEmpty(message = "手机号码不能为空")
    private List<String> phones;

    @NotEmpty(message = "参数不能为空")
    private List<String> params;

    @Min(value = 0,message = "来源系统不正确")
    private Integer fromSys;

    @Min(value = 0,message = "发送节点不正确")
    private Integer node;

    @NotEmpty(message = "直客店铺uid不能为空")
    private String shopUid;
}
