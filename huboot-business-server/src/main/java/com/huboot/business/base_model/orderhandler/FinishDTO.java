package com.huboot.business.base_model.orderhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2018/8/14 0014.
 */
@Data
@ToString
public class FinishDTO extends AbstractRentOrderOperateDTO {
    @ApiModelProperty("订单编号")
    private String rentOrderSn ;
    @ApiModelProperty("操作人，前端不需要传")
    private String operaterName;
}
