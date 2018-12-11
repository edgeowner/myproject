package com.huboot.business.base_model.weixin_service.dto.business_manage_web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@ApiModel(value="自动回复",description="AutoReplyDTO")
public class ReplyLinkDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("页面Id")
    private Integer id;
    @ApiModelProperty("页面名称")
    private String pageName;
    @ApiModelProperty("页面链接")
    private String pageUrl;
    @ApiModelProperty("页面图片链接")
    private String pageImageUrl;




}