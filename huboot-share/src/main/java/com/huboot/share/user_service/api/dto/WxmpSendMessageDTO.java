package com.huboot.share.user_service.api.dto;

import com.huboot.share.user_service.enums.WeixinMessageNodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/6 0006.
 */
@Data
public class WxmpSendMessageDTO {

    @ApiModelProperty("店铺id")
    private Long shopId;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("微信节点")
    private WeixinMessageNodeEnum nodeEnum;
    @ApiModelProperty("frist内容")
    private String frist;
    @ApiModelProperty("remark内容")
    private String remark;
    @ApiModelProperty("keyword")
    private List<String> keywordList = new ArrayList<>();
    @ApiModelProperty("urlParmas")
    private Map<String, String> urlParmas = new HashMap<>();
    @ApiModelProperty("desc")
    private String desc;

    public WxmpSendMessageDTO() {
    }

    public WxmpSendMessageDTO(Long shopId, Long userId) {
        this.shopId = shopId;
        this.userId = userId;
    }

    public void addKeyword(String keyword) {
        keywordList.add(keyword);
    }

    public void addParma(String key, String value) {
        urlParmas.put(key, value);
    }
}
