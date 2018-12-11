package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/17 0017.
 */
@Data
public class WechatViolationSMSDTO {

    @ApiModelProperty("weixinUid")
    private String companyUid;
    @ApiModelProperty("发送节点")
    private Integer node;
    @ApiModelProperty("frist内容")
    private String frist;
    @ApiModelProperty("remark内容")
    private String remark;
    @ApiModelProperty("keyword")
    private List<String> keywordList = new ArrayList<>();
    @ApiModelProperty("跳转路径参数")
    private List<String> urlParmasList;

    public void addKeyword(String keyword) {
        keywordList.add(keyword);
    }

}
