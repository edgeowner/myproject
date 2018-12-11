package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicDTO;
import com.huboot.business.common.component.exception.BizException;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
public interface IWeixinOpenService {

    /**
     * 获取开放平台授权url
     * @param url
     * @return
     */
    String getPreAuthUrl(String url);


    /**
     * 通过微信开放平台初始化公众号
     * @param authorizationCode
     * @throws BizException
     */
    WeixinPublicDTO initWeixinWithOpenAuthCode(String authorizationCode, Integer type) throws BizException;

    /**
     * 跟新授权授权
     */
    void updateAuthorizeForOpenAPI(WxOpenXmlMessage inMessage);


    String getAccessToken(String weixinUid)throws Exception;
}
