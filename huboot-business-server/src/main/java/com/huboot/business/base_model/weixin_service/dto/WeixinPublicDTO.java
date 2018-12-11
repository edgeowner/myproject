package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 *商家微信公众号配置信息表
 */
@Data
public class WeixinPublicDTO implements Serializable {

	//微信公众号审核通过后生成的唯一标识,对外暴露
	private String weixinUid ;
	//公众号appid
	private String appId ;
	//公众号secret
	private String secret ;
	//公众号token
	private String token ;
	//公众号aes_key
	private String aesKey ;
	//原始id
	private String originalId ;
	//状态（0-待验证，1-服务器验证通过，2-账号验证通过，3-服务器验证失败）
	private Integer status;
	//备注
	private String remark;
	//二维码图片路径
	private String qrcodeUrl;
	//授权方昵称
	private String nickName;
	//授权方头像
	private String headImg;
	//公众号的主体名称
	private String principalName;
	//授权方公众号所设置的微信号，可能为空
	private String alias;
	//账号介绍
	private String signature;

	private Integer bindType;
	//长按保存二维码
	private String saveQrcodeUrl;
	//功能
	private String funcInfo;
}

