package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.user_service.enums.OpenBindStatusEnum;
import com.huboot.share.user_service.enums.WeixinAuthStatusEnum;
import com.huboot.user.common.constant.WeixinConstant;
import com.huboot.user.weixin.entity.MiniappEntity;
import com.huboot.user.weixin.entity.WeixinShopRelationEntity;
import com.huboot.user.weixin.entity.WxmpEntity;
import com.huboot.user.weixin.repository.IMiniappRepository;
import com.huboot.user.weixin.repository.IWeixinShopRelationRepository;
import com.huboot.user.weixin.repository.IWxmpRepository;
import com.huboot.user.weixin.service.IOpenAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/3 0003.
 */
@Service
public class OpenAppServiceImpl implements IOpenAppService {

    private Logger logger = LoggerFactory.getLogger(OpenAppServiceImpl.class);

    @Autowired
    private WxOpenService wxOpenService;
    @Value("${domain.wycshop}")
    private String wycshopFront;
    @Autowired
    private IMiniappRepository miniappRepository;
    @Autowired
    private IWeixinShopRelationRepository weixinShopRelationRepository;
    @Autowired
    private IWxmpRepository wxmpRepository;
    @Autowired
    private IOpenAppService openAppService;


    @Override
    public String getPreAuthUrl(String url) {
        AppAssert.notEmpty(url, "授权重定向url不能为空");
        try {
            url = wycshopFront + url;
            String redurl = wxOpenService.getWxOpenComponentService().getPreAuthUrl(url);
            logger.info("获取的授权重定向url：" + redurl);
            return redurl;
        } catch (Exception e) {
            logger.warn("", e);
            throw new BizException("获取授权重定向url失败");
        }
    }

    @Transactional
    @Override
    public void updateAuthorize(String appId, String infoType) {
        if ("unauthorized".equals(infoType)) {
            this.cancelAuthMiniapp(appId);
            this.cancelAuthWxmpapp(appId);
        }
        /*if("authorized".equals(infoType)) {

        }
        if("updateauthorized".equals(infoType)) {

        }*/
    }

    private void cancelAuthMiniapp(String appId) {
        MiniappEntity miniappEntity = miniappRepository.findByMiniappId(appId);
        if(miniappEntity == null) {
            return;
        }
        miniappEntity.setStatus(WeixinAuthStatusEnum.unauthorized);
        miniappRepository.modify(miniappEntity);

        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByMiniappId(appId);
        if(relationEntity != null && !StringUtils.isEmpty(relationEntity.getOpenAppid())) {
            boolean unbind = this.openUnbind(relationEntity.getMiniappId(), relationEntity.getOpenAppid());
            if(unbind) {
                relationEntity.setMiniappBindStatus(OpenBindStatusEnum.unbind);
                weixinShopRelationRepository.modify(relationEntity);
            }
        }
    }

    private void cancelAuthWxmpapp(String appId) {
        WxmpEntity entity = wxmpRepository.findByWxmpappId(appId);
        if(entity == null) {
            return;
        }
        entity.setStatus(WeixinAuthStatusEnum.unauthorized);
        wxmpRepository.modify(entity);

        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByWxmpId(appId);
        if(relationEntity != null && !StringUtils.isEmpty(relationEntity.getOpenAppid())) {
            boolean unbind = this.openUnbind(relationEntity.getWxmpId(), relationEntity.getOpenAppid());
            if(unbind) {
                relationEntity.setWxmpBindStatus(OpenBindStatusEnum.unbind);
                weixinShopRelationRepository.modify(relationEntity);
            }
        }
    }

    private boolean openUnbind(String appId, String openAppId) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("open_appid", openAppId);
        String json = JsonUtil.buildNormalMapper().toJson(map);
        try {
            String accessToken = this.getAccessToken(appId);
            wxOpenService.post(WeixinConstant.OPEN_UNBIND + accessToken, json);
            return true;
        } catch (WxErrorException e) {
            logger.error("平台解绑失败，appid={}，open_appid={}", appId, openAppId, e);
        }
        return false;
    }

    @Override
    public String createOpen(String appId) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        String json = JsonUtil.buildNormalMapper().toJson(map);
        try {
            String accessToken = this.getAccessToken(appId);
            String resultStr = wxOpenService.post(WeixinConstant.OPEN_CREATE + accessToken, json);
            Map<String, Object> result = JsonUtil.buildNormalMapper().fromJson(resultStr, Map.class);
            if((Integer)result.get("errcode") == 0) {
                return result.get("open_appid").toString();
            } else {
                throw new BizException(result.get("errmsg").toString());
            }
        } catch (WxErrorException e) {
            logger.error("创建虚拟开放平台失败, appId={}", appId, e);
            throw new BizException("创建虚拟开放平台失败");
        }
    }

    @Override
    public void bindOpen(String appId, String openAppId) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("open_appid", openAppId);
        String json = JsonUtil.buildNormalMapper().toJson(map);
        try {
            String accessToken = this.getAccessToken(appId);
            wxOpenService.post(WeixinConstant.OPEN_BIND + accessToken, json);
        } catch (WxErrorException e) {
            logger.error("平台绑定失败，appid={}，open_appid={}", appId, openAppId, e);
            if(e.getError().getErrorCode() == 89000) {
                String oldOpenId = this.getOpenAppId(appId);
                if(!openAppId.equals(oldOpenId)) {
                    logger.error("绑定虚拟平台失败,openAppId{}, oldOpenId={}", openAppId, oldOpenId);
                    throw new BizException("绑定虚拟平台失败");
                } else {
                    logger.warn("appid={}已经绑定过平台open_appid={}", appId, openAppId);
                }
            } else {
                throw new BizException("绑定虚拟平台失败");
            }
        }
    }

    @Override
    public String getOpenAppId(String appId) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        String json = JsonUtil.buildNormalMapper().toJson(map);
        try {
            String accessToken = this.getAccessToken(appId);
            String result = wxOpenService.post(WeixinConstant.GET_OPEN_APPID + accessToken, json);
            Map<String, Object> resultMap = JsonUtil.buildNormalMapper().fromJson(result, Map.class);
            if((Integer)resultMap.get("errcode") == 0) {
                return resultMap.get("open_appid").toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("获取公众号/小程序所绑定的开放平台帐号失败，appid={}", appId, e);
        }
        return null;
    }

    @Override
    public String getAccessToken(String appId) {
        try {
            return wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appId, true);
        } catch (WxErrorException e) {
            logger.error("", e);
            throw new BizException("获取AccessToken异常");
        }
    }

}
