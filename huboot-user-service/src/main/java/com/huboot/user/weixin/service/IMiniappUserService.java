package com.huboot.user.weixin.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.huboot.user.weixin.dto.MiniappUserDTO;
import com.huboot.user.weixin.entity.MiniappUserEntity;

/**
 *小程序微信用户Service
 */
public interface IMiniappUserService {


    MiniappUserEntity saveWeixinUser(String appId, WxMaJscode2SessionResult accessToken) throws Exception;

    MiniappUserDTO findByOpenId(String opendId);

    String getPhoneNumber(String encryptedData, String iv, String openId);

}
