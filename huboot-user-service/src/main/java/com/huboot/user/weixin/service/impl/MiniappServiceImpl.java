package com.huboot.user.weixin.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.xiehua.commons.component.exception.BizException;
import com.xiehua.commons.jpa.QueryCondition;
import com.xiehua.commons.page.ShowPageImpl;
import com.xiehua.commons.utils.AppAssert;
import com.xiehua.share.common.enums.YesOrNoEnum;
import com.xiehua.share.file_service.api.dto.FileDetailResDTO;
import com.xiehua.share.file_service.api.dto.MockMultipartFileDTO;
import com.xiehua.share.file_service.api.feign.FileFeignClient;
import com.xiehua.share.user_service.api.dto.QrcodeCreateDTO;
import com.xiehua.share.user_service.api.dto.UserAuthResultDTO;
import com.xiehua.share.user_service.data.ShopCacheData;
import com.xiehua.share.user_service.enums.WeixinAuthStatusEnum;
import com.xiehua.user.common.constant.WeixinConstant;
import com.xiehua.user.weixin.dto.admin.MiniappPagerDTO;
import com.xiehua.user.weixin.dto.wycshop.MiniappInfoDTO;
import com.xiehua.user.weixin.entity.MiniappEntity;
import com.xiehua.user.weixin.repository.IMiniappRepository;
import com.xiehua.user.weixin.service.IMiniappConfigService;
import com.xiehua.user.weixin.service.IMiniappService;
import com.xiehua.user.weixin.service.IMiniappUserService;
import com.xiehua.user.weixin.service.IWeixinShopRelationService;
import com.xiehua.user.weixin.support.WxServiceFactory;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * 小程序ServiceImpl
 */
@Service("miniappServiceImpl")
public class MiniappServiceImpl implements IMiniappService {

    private Logger logger = LoggerFactory.getLogger(MiniappServiceImpl.class);

    @Autowired
    private IMiniappRepository miniappRepository;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private IMiniappUserService userService;
    @Autowired
    private IMiniappConfigService configService;
    @Autowired
    private FileFeignClient fileFeignClient;
    @Autowired
    private WxServiceFactory wxServiceFactory;
    @Autowired
    private ShopCacheData shopCacheData;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IWeixinShopRelationService weixinShopRelationService;


    @Override
    public MiniappInfoDTO findMiniappInfo(Long shopId) {
        MiniappInfoDTO dto = new MiniappInfoDTO();
        String miniappId = weixinShopRelationService.findMiniappIdByShopId(shopId);
        if (StringUtils.isEmpty(miniappId)) {
            dto.setHasAuth(0);
        } else {
            MiniappEntity entity = miniappRepository.findByMiniappId(miniappId);
            if (entity == null) {
                throw new BizException("小程序信息不存在");
            }
            if(WeixinAuthStatusEnum.authorized.equals(entity.getStatus())) {
                dto.setHasAuth(1);
                dto.setHeadImg(entity.getHeadImg());
                dto.setNickName(entity.getNickName());
                dto.setPrincipalName(entity.getPrincipalName());
                dto.setSignature(entity.getSignature());
            } else {
                dto.setHasAuth(0);
            }
        }
        return dto;
    }

    /**
     * 小程序授权绑定
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public MiniappInfoDTO authAndBindShop(String authorizationCode, Long shopId) {
        WxOpenAuthorizationInfo authorizationInfo;
        WxOpenAuthorizerInfo authorizerInfo;
        try {
            WxOpenQueryAuthResult queryAuthResult = wxOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
            authorizationInfo = queryAuthResult.getAuthorizationInfo();
            WxOpenAuthorizerInfoResult authorizerInfoResult = wxOpenService.getWxOpenComponentService().getAuthorizerInfo(authorizationInfo.getAuthorizerAppid());
            authorizerInfo = authorizerInfoResult.getAuthorizerInfo();
        } catch (WxErrorException e) {
            logger.warn("", e);
            throw new BizException("获取小程序授权信息异常");
        }
        WxOpenAuthorizerInfo.MiniProgramInfo miniProgramInfo = authorizerInfo.getMiniProgramInfo();
        if (miniProgramInfo == null) {
            throw new BizException("你授权的不是小程序应用，请重新授权");
        }
        if(authorizerInfo.getVerifyTypeInfo() == -1) {
            throw new BizException("你的小程序还还未微信认证通过，请先认证");
        }
        MiniappEntity entity = miniappRepository.findByMiniappId(authorizationInfo.getAuthorizerAppid());
        if (entity == null) {
            entity = new MiniappEntity();
            entity.setMiniappId(authorizationInfo.getAuthorizerAppid());
            entity.setStatus(WeixinAuthStatusEnum.authorized);
            entity.setRefreshToken(authorizationInfo.getAuthorizerRefreshToken());
            entity.setCanBitchRelease(YesOrNoEnum.yes);
            entity.setHasRelease(YesOrNoEnum.no);
            entity.setNickName(authorizerInfo.getNickName());
            entity.setHeadImg(authorizerInfo.getHeadImg());
            entity.setPrincipalName(authorizerInfo.getPrincipalName());
            entity.setAlias(authorizerInfo.getAlias());
            entity.setSignature(authorizerInfo.getSignature());
            miniappRepository.create(entity);
        } else {
            entity.setStatus(WeixinAuthStatusEnum.authorized);
            entity.setRefreshToken(authorizationInfo.getAuthorizerRefreshToken());
            entity.setNickName(authorizerInfo.getNickName());
            entity.setHeadImg(authorizerInfo.getHeadImg());
            entity.setPrincipalName(authorizerInfo.getPrincipalName());
            entity.setAlias(authorizerInfo.getAlias());
            entity.setSignature(authorizerInfo.getSignature());
            miniappRepository.modify(entity);
        }

        weixinShopRelationService.saveMiniappRelation(entity.getMiniappId(), shopId);

        //初始化配置
        configService.initConfig(entity.getMiniappId());

        MiniappInfoDTO dto = new MiniappInfoDTO();
        dto.setHasAuth(1);
        dto.setHeadImg(entity.getHeadImg());
        dto.setNickName(entity.getNickName());
        dto.setPrincipalName(entity.getPrincipalName());
        dto.setSignature(entity.getSignature());
        return dto;
    }


    @Override
    public UserAuthResultDTO getOpenId(Long shopId, String code) {
        if (StringUtils.isEmpty(shopId)) {
            throw new BizException("店铺ID不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new BizException("微信code不能为空");
        }
        String appId = weixinShopRelationService.findMiniappIdByShopId(shopId);
        if (StringUtils.isEmpty(appId)) {
            logger.warn("店铺没有绑定小程序, shopId=" + shopId);
            throw new BizException("店铺没有绑定小程序");
        }
        try {
            UserAuthResultDTO resultDTO = new UserAuthResultDTO();
            WxMaJscode2SessionResult accessToken = wxServiceFactory.getWxMaService(appId).jsCode2SessionInfo(code);
            try {
                userService.saveWeixinUser(appId, accessToken);
            } catch (Exception e) {
                logger.warn("", e);
            }
            resultDTO.setOpenId(accessToken.getOpenid());
            resultDTO.setUnionid(accessToken.getUnionid());
            resultDTO.setAppId(appId);
            return resultDTO;
        } catch (Exception e) {
            logger.warn("获取小程序opendId异常, shopId={}, appId={}, code={}", shopId, appId, code);
            throw new BizException("获取小程序opendId异常");
        }
    }


    @Transactional
    @Override
    public void setCanBitchRelease(String miniappId, String yesOrNo) {
        MiniappEntity entity = miniappRepository.findByMiniappId(miniappId);
        entity.setCanBitchRelease(YesOrNoEnum.valueOf(yesOrNo));
        miniappRepository.modify(entity);
    }

    @Override
    public String createQrcode(QrcodeCreateDTO createDTO) {
        String appId = weixinShopRelationService.findMiniappIdByShopId(createDTO.getShopId());
        if (StringUtils.isEmpty(appId)) {
            throw new BizException("店铺还未绑定小程序");
        }
        WxMaQrcodeService qrcodeService = wxServiceFactory.getWxMaService(appId).getQrcodeService();
        try {
            File file = qrcodeService.createWxaCodeUnlimit(createDTO.getScene(), createDTO.getPage());
            FileDetailResDTO resDTO = fileFeignClient.create(new MockMultipartFileDTO("file", file));
            return resDTO.getFullPath();
        } catch (Exception e) {
            logger.warn("小程序生成二维码异常, appId={}", appId, e);
            throw new BizException("小程序生成二维码异常");
        }
    }


    @Override
    public String createPathQrcode(QrcodeCreateDTO createDTO) {
        String appId = weixinShopRelationService.findMiniappIdByShopId(createDTO.getShopId());
        if (StringUtils.isEmpty(appId)) {
            throw new BizException("店铺还未绑定小程序");
        }
        WxMaQrcodeService qrcodeService = wxServiceFactory.getWxMaService(appId).getQrcodeService();
        try {
            File file = qrcodeService.createQrcode(createDTO.getPage());
            FileDetailResDTO resDTO = fileFeignClient.create(new MockMultipartFileDTO("file", file));
            return resDTO.getFullPath();
        } catch (Exception e) {
            logger.warn("小程序生成二维码异常, appId={}", appId, e);
            throw new BizException("小程序生成二维码异常");
        }
    }

    @Override
    public ShowPageImpl<MiniappPagerDTO> getPager(Integer page, Integer size) {
        Page<MiniappEntity> pager = miniappRepository.findPage(QueryCondition.from(MiniappEntity.class).limit(page, size));
        Page<MiniappPagerDTO> dtoPager = pager.map(entity -> {
            MiniappPagerDTO dto = new MiniappPagerDTO();
            Long shopId = weixinShopRelationService.findShopIdByMiniappId(entity.getMiniappId());
            dto.setShopName(shopCacheData.getShopById(shopId).getName());
            dto.setMiniappId(entity.getMiniappId());
            dto.setStatus(entity.getStatus().getShowName());
            dto.setPrincipalName(entity.getPrincipalName());
            dto.setNickName(entity.getNickName());
            dto.setHasRelease(entity.getHasRelease().name());
            dto.setHasReleaseName(entity.getHasRelease().getShowName());
            dto.setCanBitchRelease(entity.getCanBitchRelease().name());
            dto.setCanBitchReleaseName(entity.getCanBitchRelease().getShowName());
            return dto;
        });
        return new ShowPageImpl(dtoPager);
    }

    @Transactional
    @Override
    public String getQrcodeImg(String miniappId) {
        MiniappEntity miniappEntity = miniappRepository.findByMiniappId(miniappId);
        AppAssert.notNull(miniappEntity, "小程序不存在");
        if (!YesOrNoEnum.yes.equals(miniappEntity.getHasRelease())) {
            throw new BizException("小程序还未发布过，不能生成二维码");
        }
        if(StringUtils.isEmpty(miniappEntity.getQrcodeImg())) {
            WxMaQrcodeService qrcodeService =  wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(miniappId).getQrcodeService();
            try {
                File file = qrcodeService.createQrcode(QrcodeCreateDTO.INDEX);
                FileDetailResDTO resDTO = fileFeignClient.create(new MockMultipartFileDTO("file", file));
                miniappEntity.setQrcodeImg(resDTO.getFullPath());
                miniappRepository.modify(miniappEntity);
            } catch (Exception e) {
                logger.warn("小程序生成二维码异常, appId={}", miniappId, e);
                throw new BizException("小程序生成二维码异常");
            }
        }
        return miniappEntity.getQrcodeImg();
    }

    @Override
    public String getExperienceQrcodeImg(String miniappId, String path) throws Exception {
        String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(miniappId, false);
        String url = WeixinConstant.GET_QRCODE + accessToken;
        if(!StringUtils.isEmpty(path)) {
            path = path.replace("/", "%2F");
            url = url + "&path=" + path;
        }
        logger.info("获取小程序体验码url：" + url);
        byte[] response = restTemplate.getForObject(url, byte[].class);
        return Base64.encodeBase64String(response);
    }
}
