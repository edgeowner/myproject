package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.user_service.enums.MiniappConfigTypeEnum;
import com.huboot.user.common.config.RefreshValue;
import com.huboot.user.common.constant.WeixinConstant;
import com.huboot.user.weixin.dto.admin.MiniappSettingDTO;
import com.huboot.user.weixin.entity.MiniappConfigEntity;
import com.huboot.user.weixin.entity.MiniappEntity;
import com.huboot.user.weixin.repository.IMiniappConfigRepository;
import com.huboot.user.weixin.repository.IMiniappRepository;
import com.huboot.user.weixin.service.IMiniappConfigService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *小程序配置ServiceImpl
 */
@Service("mimiappConfigServiceImpl")
public class MiniappConfigServiceImpl implements IMiniappConfigService {

    private Logger logger = LoggerFactory.getLogger(MiniappConfigServiceImpl.class);

    @Autowired
    private IMiniappConfigRepository configRepository;
    @Autowired
    private RefreshValue refreshValue;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private IMiniappRepository miniappRepository;

    @Transactional
    @Override
    public void initConfig(String appId) {
        try {
            this.configServerDomain(appId);
        } catch (Exception e) {
            logger.warn("小程序设置服务器域名异常", e);
        }
        try {
            this.configBaseLibraryVersion(appId, WeixinConstant.DEFAULT_VERSION);
        } catch (Exception e) {
            logger.warn("小程序设置版本库异常", e);
        }
    }

    @Transactional
    @Override
    public void configServerDomain(String appId) {
        //设置服务器域名
        Map<String, Object> domainPostMap = new HashMap<>();
        domainPostMap.put("action", "set");
        domainPostMap.put("requestdomain", Arrays.asList(refreshValue.getRequestdomain().split(",")));
        domainPostMap.put("wsrequestdomain", Arrays.asList(refreshValue.getWsrequestdomain().split(",")));
        domainPostMap.put("uploaddomain", Arrays.asList(refreshValue.getUploaddomain().split(",")));
        domainPostMap.put("downloaddomain", Arrays.asList(refreshValue.getDownloaddomain().split(",")));
        String domainPostData = JsonUtil.buildNormalMapper().toJson(domainPostMap);

        try {
            String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appId, false);
            String url = WeixinConstant.MODIFY_DOMAIN + accessToken;
            logger.info("小程序服务器域名设置提交参数url={}, data={}：", url, domainPostData);

            String result = wxOpenService.post(url, domainPostData);
            logger.info("小程序服务器域名设置提交结果result={}", result);
            Map<String, String> map = JsonUtil.buildNonDefaultMapper().fromJson(result, Map.class);
            if("ok".equals(map.get("errmsg"))) {
                saveConfig(appId, MiniappConfigTypeEnum.server_domain, domainPostData);
            } else {
                throw new BizException("小程序设置服务器域名异常");
            }
        } catch (Exception e) {
            logger.warn("小程序设置服务器域名异常", e);
            throw new BizException("小程序设置服务器域名异常");
        }
    }

    private void saveConfig(String appId, MiniappConfigTypeEnum typeEnum, String postData) {
        MiniappConfigEntity configEntity = configRepository.findByMiniappIdAndType(appId, typeEnum);
        if(configEntity == null) {
            configEntity = new MiniappConfigEntity();
            configEntity.setMiniappId(appId);
            configEntity.setType(typeEnum);
            configEntity.setRequestBody(postData);
            configRepository.create(configEntity);
        } else {
            configEntity.setRequestBody(postData);
            configRepository.modify(configEntity);
        }
    }

    @Transactional
    @Override
    public void configWebviewDomain(String appId) {

    }

    @Override
    public void bitchSetServerDomain() {
        List<MiniappEntity> list = miniappRepository.findAll();
        for(MiniappEntity entity : list) {
            configServerDomain(entity.getMiniappId());
        }
    }

    @Transactional
    @Override
    public void configBaseLibraryVersion(String appId, String version) {
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("version", version);
        String postData = JsonUtil.buildNormalMapper().toJson(postMap);

        try {
            String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appId, false);
            String url = WeixinConstant.SET_WEAPP_SUPPORT_VERSION + accessToken;
            logger.info("小程序设置版本库提交参数url={}, data={}：", url, postData);

            String result = wxOpenService.post(url, postData);
            logger.info("小程序设置版本库结果：" + result);
            Map<String, String> map = JsonUtil.buildNonDefaultMapper().fromJson(result, Map.class);
            if("ok".equals(map.get("errmsg"))) {
                saveConfig(appId, MiniappConfigTypeEnum.base_library_version, postData);
            } else {
                throw new BizException("小程序设置版本库异常");
            }
        } catch (Exception e) {
            logger.warn("小程序设置版本库异常", e);
            throw new BizException("小程序设置版本库异常");
        }
    }

    @Override
    public void checkConfig(String appId) {
        MiniappConfigEntity serverConfigEntity = configRepository.findByMiniappIdAndType(appId, MiniappConfigTypeEnum.server_domain);
        if(serverConfigEntity == null) {
            throw new BizException("小程序服务器域名没有设置");
        }
        MiniappConfigEntity versionConfigEntity = configRepository.findByMiniappIdAndType(appId, MiniappConfigTypeEnum.base_library_version);
        if(versionConfigEntity == null) {
            throw new BizException("小程序基础版本库没有设置");
        }
    }

    @Override
    public MiniappSettingDTO getSettingInfo(String miniappId) {
        List<MiniappConfigEntity> list = configRepository.findByMiniappId(miniappId);
        MiniappSettingDTO dto = new MiniappSettingDTO();
        list.forEach(entity -> {
            if(MiniappConfigTypeEnum.server_domain.equals(entity.getType())) {
                dto.setServerDomain(entity.getRequestBody());
            } else if(MiniappConfigTypeEnum.base_library_version.equals(entity.getType())) {
                dto.setBaseVersion(entity.getRequestBody());
            }
        });
        return dto;
    }
}
