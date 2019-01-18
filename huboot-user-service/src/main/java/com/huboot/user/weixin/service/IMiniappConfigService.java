package com.huboot.user.weixin.service;


import com.huboot.user.weixin.dto.admin.MiniappSettingDTO;

/**
 *小程序配置Service
 */
public interface IMiniappConfigService {

    //初始化配置
    void initConfig(String appId);

    void configServerDomain(String appId);

    void configWebviewDomain(String appId);

    void bitchSetServerDomain();

    void configBaseLibraryVersion(String appId, String version);

    void checkConfig(String appId);

    MiniappSettingDTO getSettingInfo(String miniappId);
}
