package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/8 0008.
 */
@Data
public class WeixinSubscribeCountDTO implements Serializable {

    //总关注数量
    private Integer totalSubscribeCount = 0;
    //实际关注数量
    private Integer actualSubscribeCount = 0;
}
