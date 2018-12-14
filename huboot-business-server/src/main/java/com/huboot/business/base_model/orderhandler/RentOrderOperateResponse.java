package com.huboot.business.base_model.orderhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/6/11 0011.
 */
@Data
public class RentOrderOperateResponse {

    @ApiModelProperty("订单编号")
    public String orderSn;
    @ApiModelProperty("商品ID")
    private String errorGoodsId;
    @ApiModelProperty("错误类型：1,库存不足；2.已下架")
    private Integer errorType;
    @ApiModelProperty("提示内容")
    private String remindContent ;
}
