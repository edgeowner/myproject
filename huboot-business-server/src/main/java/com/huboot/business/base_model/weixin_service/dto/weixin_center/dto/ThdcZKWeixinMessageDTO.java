package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/3 0003.
 */
@Data
public class ThdcZKWeixinMessageDTO {

    //公司compayuid
    private String companyUid;
    //发送节点
    private Integer node;
    //frist内容
    private String frist;
    //remark内容
    private String remark;
    //keyword
    private List<String> keywordList = new ArrayList<>();
    //跳转路径参数
    private List<String> urlParmasList;
    //消息业务类型（0-订单，1-其他）
    private Integer btype = 0;

    public void addKeyword(String keyword) {
        keywordList.add(keyword);
    }
}
