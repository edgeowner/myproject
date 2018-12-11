package com.huboot.business.base_model.weixin_service.dto.business_manage_web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
@Data
public class WeixinUserStatisticChartDTO {

    @ApiModelProperty("日期")
    private List<String> date;

    @ApiModelProperty("新增的用户数量")
    private List<Integer> newUser;

    @ApiModelProperty("取消关注的用户数量")
    private List<Integer> cancelUser;

}
