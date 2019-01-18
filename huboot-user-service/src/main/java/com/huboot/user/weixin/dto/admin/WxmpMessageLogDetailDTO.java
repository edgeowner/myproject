package com.huboot.user.weixin.dto.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WxmpMessageLogDetailDTO {

    //
    private Long id;
    //
    private LocalDateTime createTime;
    //公众号appid
    private String wxmpappId ;
    //公众号appid
    private String wxmpappName ;
    //参数
    private String parameter;
    //消息节点
    private String node ;
    //消息节点
    private String nodeName ;
    //微信用户openid
    private String openId ;
    //消息内容
    private String content ;
    //消息相应
    private String response ;
    //发送状态
    private String sendStatusName ;
    //错误原因
    private String errorReason ;
    //备注
    private String remark ;

}

