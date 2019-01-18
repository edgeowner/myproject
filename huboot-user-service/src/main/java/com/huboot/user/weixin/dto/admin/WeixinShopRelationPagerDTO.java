package com.huboot.user.weixin.dto.admin;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/7 0007.
 */
@Data
public class WeixinShopRelationPagerDTO {

    //店铺id
    private Long shopId ;
    //店铺sn
    private String shopSn ;
    //店铺名称
    private String shopName ;
    //所创建的开放平台帐号的appid
    private String openAppid ;
    //miniapp_id
    private String miniappId ;
    //
    private String miniappName ;
    //小程序绑定虚拟开放平台状态
    private String miniappBindStatus;
    //公众号appid
    private String wxmpId ;
    //公众号appid
    private String wxmpName ;
    //公众号绑定虚拟开放平台状态
    private String wxmpBindStatus;
}
