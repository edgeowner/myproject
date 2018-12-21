package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.service.IMiniAppService;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.base_model.weixin_service.config.WeixinConstant;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicDTO;
import com.huboot.business.base_model.weixin_service.service.IWeixinOpenService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
@Service("wexinOpenServiceImpl")
public class WexinOpenServiceImpl implements IWeixinOpenService {

    private Logger logger = LoggerFactory.getLogger(WeixinPublicServiceImpl.class);

    @Value("${huboot.domain.sjhtFront}")
    private String authFront;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;

    @Autowired
    private IMiniAppService miniAppService;


    /**
     * @param url
     * @return
     */
    @Override
    public String getPreAuthUrl(String url) {
        Assert.notNull(url, "授权重定向url不能为空");
        url = authFront + url;
        try {
            logger.info("授权重定向url：" + url);
            return wxOpenService.getWxOpenComponentService().getPreAuthUrl(url);
        } catch (Exception e) {
            logger.error("getPreAuthUrl", e);
            throw new BizException(e);
        }
    }

    /**
     * 获取开放平台授权url
     *
     * @param authorizationCode
     * @throws BizException
     */
    @Transactional
    @Override
    public WeixinPublicDTO initWeixinWithOpenAuthCode(String authorizationCode, Integer type) throws BizException {
        WeixinPublicEntity weixinPublicEntity = this.savePublic(authorizationCode, type);
        WeixinPublicDTO dto = new WeixinPublicDTO();
        BeanUtils.copyProperties(weixinPublicEntity, dto);
        dto.setStatus(weixinPublicEntity.getStatus().ordinal());
        dto.setBindType(weixinPublicEntity.getBindType().ordinal());
        dto.setType(weixinPublicEntity.getType().ordinal());
        if(WeixinPublicEntity.TypeEnum.miniapp.equals(weixinPublicEntity.getType())) {
            try {
                //初始化设置信息
                miniAppService.commitDomain(weixinPublicEntity.getWeixinUid());
                miniAppService.commitViewDomain(weixinPublicEntity.getWeixinUid());
                miniAppService.setWeappSupportVersion(weixinPublicEntity.getWeixinUid(), WeixinConstant.SUPPORT_VERSION);
            } catch (Exception e) {
                logger.error("小程序初始化信息设置失败");
            }
        }

        return dto;
    }

    private WeixinPublicEntity savePublic(String authorizationCode, Integer type) {
        WxOpenAuthorizerInfo authorizerInfo;
        String appid;
        String funcInfo = "";
        try {
            WxOpenQueryAuthResult queryAuthResult = wxOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
            WxOpenAuthorizationInfo authorizationInfo = queryAuthResult.getAuthorizationInfo();
            appid = authorizationInfo.getAuthorizerAppid();
            funcInfo = this.getFuncInfo(authorizationInfo.getFuncInfo());
            WxOpenAuthorizerInfoResult authorizerInfoResult = wxOpenService.getWxOpenComponentService().getAuthorizerInfo(appid);
            authorizerInfo = authorizerInfoResult.getAuthorizerInfo();
        } catch (WxErrorException e) {
            logger.error("initPublicFromOpenAPI", e);
            throw new BizException(e);
        }

        WeixinPublicEntity weixinPublicEntity = weixinPublicRepository.findByAppId(appid);
        if (weixinPublicEntity == null) {
            weixinPublicEntity = new WeixinPublicEntity();
            weixinPublicEntity.setAppId(appid);
            weixinPublicEntity.setOriginalId(authorizerInfo.getUserName());
            weixinPublicEntity.setWeixinUid(UUID.randomUUID().toString().replace("-", ""));
        }
        weixinPublicEntity.setSystem(SystemEnum.zk.getVal());
        weixinPublicEntity.setNickName(authorizerInfo.getNickName());
        weixinPublicEntity.setPrincipalName(authorizerInfo.getPrincipalName());
        weixinPublicEntity.setAlias(authorizerInfo.getAlias());
        weixinPublicEntity.setSignature(authorizerInfo.getSignature());
        weixinPublicEntity.setFuncInfo(funcInfo);
        weixinPublicEntity.setStatus(WeixinPublicEntity.StatusEnum.authorizedSuccess);
        weixinPublicEntity.setQrcodeUrl(authorizerInfo.getQrcodeUrl());
        weixinPublicEntity.setBindType(WeixinPublicEntity.BindTypeEnum.weixin3open);
        WxOpenAuthorizerInfo.MiniProgramInfo miniProgramInfo = authorizerInfo.getMiniProgramInfo();
        if (miniProgramInfo == null) {
            if(type != 1) {
                throw new BizException("您授权的不是公众号，请选好公众号后再重新授权");
            }
            weixinPublicEntity.setType(WeixinPublicEntity.TypeEnum.pubapp);
            weixinPublicEntity.setHeadImg(StringUtils.isEmpty(authorizerInfo.getHeadImg()) ? "" : authorizerInfo.getHeadImg());

        } else {
            if(type != 2) {
                throw new BizException("您授权的不是小程序，请选好小程序后再重新授权");
            }
            weixinPublicEntity.setType(WeixinPublicEntity.TypeEnum.miniapp);
        }
        if (weixinPublicEntity.getId() == null) {
            weixinPublicRepository.create(weixinPublicEntity);
        } else {
            weixinPublicRepository.update(weixinPublicEntity);
        }
        return weixinPublicEntity;
    }

    private String getFuncInfo(List<Integer> funcInfo) {
        StringBuilder sb = new StringBuilder("");
        for (Integer func : funcInfo) {
            if ("".equals(sb.toString())) {
                sb.append(func.toString());
            } else {
                sb.append("," + func.toString());
            }
        }
        return sb.toString();
    }

    /**
     * @param inMessage
     */
    @Transactional
    @Override
    public void updateAuthorizeForOpenAPI(WxOpenXmlMessage inMessage) {
        if ("unauthorized".equals(inMessage.getInfoType())) {
            this.cancelAuth(inMessage.getAuthorizerAppid());
        }
        if("authorized".equals(inMessage.getInfoType())) {
        }
        if("updateauthorized".equals(inMessage.getInfoType())) {
        }
    }

    private void cancelAuth(String appId) {
        WeixinPublicEntity weixinPublicEntity = weixinPublicRepository.findByAppId(appId);
        if (weixinPublicEntity == null) {
            logger.error("公众号不存在：authorizerAppid=" + appId);
            return;
        }
        weixinPublicEntity.setStatus(WeixinPublicEntity.StatusEnum.unAuthorized);
        weixinPublicRepository.update(weixinPublicEntity);
        //通知直客店铺不可用
        /*if (WeixinPublicEntity.TypeEnum.pubapp.equals(weixinPublicEntity.getType())) {
            b2CShopService.cancellationAuthorization(weixinPublicEntity.getWeixinUid());
        }
        if (WeixinPublicEntity.TypeEnum.miniapp.equals(weixinPublicEntity.getType())) {
            b2CShopService.cancelMiniappAuthorization(weixinPublicEntity.getWeixinUid());
        }*/

    }


    @Override
    public String getAccessToken(String weixinUid) throws Exception {
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if(publicEntity == null) {
            throw new BizException("微信配置不存在");
        }
        return wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(publicEntity.getAppId(), true);
    }
}
