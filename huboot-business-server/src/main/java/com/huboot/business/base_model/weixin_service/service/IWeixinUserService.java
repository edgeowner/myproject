package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserEntity;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
public interface IWeixinUserService {

    WeixinUserEntity setPubUser(WeixinPublicEntity publicEntity, String openId);

    /**
     * 用户取消关注
     * @param originalId 公众号id
     * @param openId 用户openid
     */
    void unsubscribe(String originalId, String openId);


    /**
     * 用户关注公众号
     * @param originalId 公众号id
     * @param openId 用户openid
     */
    void subscribe(String originalId, String openId, String eventKey);


    void userYouhua();

}
