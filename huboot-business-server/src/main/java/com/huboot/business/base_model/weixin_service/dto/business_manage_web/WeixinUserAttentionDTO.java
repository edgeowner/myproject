package com.huboot.business.base_model.weixin_service.dto.business_manage_web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
@Data
public class WeixinUserAttentionDTO {

    @ApiModelProperty("昨日关注人数")
    private int count;

    @ApiModelProperty("昨日")
    private Item yesterday;

    @ApiModelProperty("周")
    private Item week;

    @ApiModelProperty("月")
    private Item month;

    @Data
    public class Item {

        @ApiModelProperty("分值")
        private String value;

        @ApiModelProperty("true:正序,false:倒叙")
        private boolean asc;

    }

}
