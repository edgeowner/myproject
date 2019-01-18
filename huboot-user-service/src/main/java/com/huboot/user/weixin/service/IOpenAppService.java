package com.huboot.user.weixin.service;

/**
 * Created by Administrator on 2018/12/3 0003.
 */
public interface IOpenAppService {

    //
    String getPreAuthUrl(String url);

    void updateAuthorize(String appId, String infoType);

    String getAccessToken(String appId) throws Exception;

    String createOpen(String appId);

    String getOpenAppId(String appId);

    void bindOpen(String appId, String openAppId);

}
