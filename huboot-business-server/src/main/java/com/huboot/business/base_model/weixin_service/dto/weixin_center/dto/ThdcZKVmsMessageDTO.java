package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import lombok.Data;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/3 0003.
 */
@Data
public class ThdcZKVmsMessageDTO {

    private Integer node;
    private String phone;
    private Map<String,String> map;
    private String remark;
    private String requestId ;
}
