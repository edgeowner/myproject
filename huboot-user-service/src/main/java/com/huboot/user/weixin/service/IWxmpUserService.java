package com.huboot.user.weixin.service;


import me.chanjar.weixin.mp.api.WxMpService;

/**
 *小程序微信用户Service
 */
public interface IWxmpUserService {

    String saveWeixinUserOnSubscribe(String originalId, String openId, String eventKey, WxMpService weixinService);

    void updateWeixinUserOnUnSubscribe(String openId);
}
