package com.huboot.business.base_model.weixin_service.dto.business_manage_web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@ApiModel(value="自动回复",description="AutoReplyDTO")
public class AutoReplyDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("用户基础信息")
    private String content;




}