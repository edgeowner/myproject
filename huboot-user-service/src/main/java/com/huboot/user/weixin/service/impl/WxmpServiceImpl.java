package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.data.ShopCacheData;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.WeixinAuthStatusEnum;
import com.huboot.user.weixin.dto.WxmpUpdateDTO;
import com.huboot.user.weixin.dto.admin.WxmpPagerDTO;
import com.huboot.user.weixin.dto.wycshop.WxmpappInfoDTO;
import com.huboot.user.weixin.entity.WxmpEntity;
import com.huboot.user.weixin.repository.IWxmpRepository;
import com.huboot.user.weixin.service.IWeixinShopRelationService;
import com.huboot.user.weixin.service.IWxmpService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 *小程序ServiceImpl
 */
@Service("wxmpServiceImpl")
public class WxmpServiceImpl implements IWxmpService {

    private Logger logger = LoggerFactory.getLogger(WxmpServiceImpl.class);

    @Autowired
    private IWxmpRepository wxmpRepository;
    @Autowired
    private IWeixinShopRelationService weixinShopRelationService;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private ShopCacheData shopCacheData;
    /*@Autowired
    private FileFeignClient fileFeignClient;*/
    @Autowired
    private UserCacheData userCacheData;

    @Override
    public WxmpappInfoDTO findWxmpappInfo() {
        Long shopId = userCacheData.getCurrentUserWycShopId();
        WxmpappInfoDTO dto = new WxmpappInfoDTO();
        String appId = weixinShopRelationService.findWxmpappIdByShopId(shopId);
        if (StringUtils.isEmpty(appId)) {
            dto.setHasAuth(0);
        } else {
            WxmpEntity entity = wxmpRepository.findByWxmpappId(appId);
            if (entity == null) {
                throw new BizException("公众号信息不存在");
            }
            if(WeixinAuthStatusEnum.authorized.equals(entity.getStatus())) {
                dto.setHasAuth(1);
                dto.setHeadImg(entity.getHeadImg());
                dto.setNickName(entity.getNickName());
                dto.setPrincipalName(entity.getPrincipalName());
                dto.setSignature(entity.getSignature());
                dto.setSubscribeReply(entity.getSubscribeReply());
            } else {
                dto.setHasAuth(0);
                dto.setSubscribeReply(entity.getSubscribeReply());
            }
        }
        return dto;
    }

    @Transactional
    @Override
    public WxmpappInfoDTO authAndBindShop(String authorizationCode) {
        Long shopId = userCacheData.getCurrentUserWycShopId();
        WxOpenAuthorizationInfo authorizationInfo;
        WxOpenAuthorizerInfo authorizerInfo;
        try {
            WxOpenQueryAuthResult queryAuthResult = wxOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
            authorizationInfo = queryAuthResult.getAuthorizationInfo();
            WxOpenAuthorizerInfoResult authorizerInfoResult = wxOpenService.getWxOpenComponentService().getAuthorizerInfo(authorizationInfo.getAuthorizerAppid());
            authorizerInfo = authorizerInfoResult.getAuthorizerInfo();
        } catch (WxErrorException e) {
            logger.warn("", e);
            throw new BizException("获取公众号授权信息异常");
        }
        WxOpenAuthorizerInfo.MiniProgramInfo miniProgramInfo = authorizerInfo.getMiniProgramInfo();
        if (miniProgramInfo != null) {
            throw new BizException("你授权的不是公众号，请重新授权");
        }
        if(authorizerInfo.getVerifyTypeInfo() == -1) {
            throw new BizException("你的公众号还还未微信认证通过，请先认证");
        }
        WxmpEntity entity = wxmpRepository.findByWxmpappId(authorizationInfo.getAuthorizerAppid());
        if (entity == null) {
            entity = new WxmpEntity();
            entity.setWxmpappId(authorizationInfo.getAuthorizerAppid());
            entity.setOriginalId(authorizerInfo.getUserName());
            entity.setRefreshToken(authorizationInfo.getAuthorizerRefreshToken());
            entity.setStatus(WeixinAuthStatusEnum.authorized);
            entity.setNickName(authorizerInfo.getNickName());
            entity.setHeadImg(authorizerInfo.getHeadImg());
            entity.setPrincipalName(authorizerInfo.getPrincipalName());
            entity.setAlias(authorizerInfo.getAlias());
            entity.setSignature(authorizerInfo.getSignature());
            wxmpRepository.create(entity);
        } else {
            entity.setStatus(WeixinAuthStatusEnum.authorized);
            entity.setNickName(authorizerInfo.getNickName());
            entity.setHeadImg(authorizerInfo.getHeadImg());
            entity.setPrincipalName(authorizerInfo.getPrincipalName());
            entity.setAlias(authorizerInfo.getAlias());
            entity.setSignature(authorizerInfo.getSignature());
            wxmpRepository.modify(entity);
        }

        weixinShopRelationService.saveWxmpRelation(entity.getWxmpappId(), shopId);

        WxmpappInfoDTO dto = new WxmpappInfoDTO();
        dto.setHasAuth(1);
        dto.setWxmpId(entity.getWxmpappId());
        dto.setHeadImg(entity.getHeadImg());
        dto.setNickName(entity.getNickName());
        dto.setPrincipalName(entity.getPrincipalName());
        dto.setSignature(entity.getSignature());
        return dto;
    }

    @Override
    public ShowPageImpl<WxmpPagerDTO> getPager(Integer page, Integer size) {
        Page<WxmpEntity> entityPage = wxmpRepository.findPage(QueryCondition.from(WxmpEntity.class).limit(page, size));
        Page<WxmpPagerDTO> dtoPage = entityPage.map(entity -> {
            WxmpPagerDTO dto = new WxmpPagerDTO();

            Long shopId = weixinShopRelationService.findShopIdByWxmpappId(entity.getWxmpappId());
            dto.setShopName(shopCacheData.getShopById(shopId).getName());
            dto.setWxmpappId(entity.getWxmpappId());
            dto.setStatus(entity.getStatus().getShowName());
            dto.setNickName(entity.getNickName());
            dto.setPrincipalName(entity.getPrincipalName());
            dto.setQrcodeImg(entity.getQrcodeImg());

            return dto;
        });
        return ShowPageImpl.pager(dtoPage);
    }


    @Override
    public String createQrcode(QrcodeCreateDTO createDTO) {
        String appId = weixinShopRelationService.findWxmpappIdByShopId(createDTO.getShopId());
        if (StringUtils.isEmpty(appId)) {
            throw new BizException("店铺还未绑定公众号");
        }
        WxmpEntity wxmpEntity = wxmpRepository.findByWxmpappId(appId);
        AppAssert.notNull(wxmpEntity, "公众号不存在");
        try {
            WxMpQrcodeService qrcodeService =  wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getQrcodeService();
            WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateLastTicket(createDTO.getScene());
            File file = qrcodeService.qrCodePicture(ticket);
            //FileDetailResDTO resDTO = fileFeignClient.create(new MockMultipartFileDTO("file", file));
            return "";
        } catch (Exception e) {
            logger.error("公众号生成二维码异常:", e);
            throw new BizException("公众号生成二维码异常，请检查");
        }
    }

    @Transactional
    @Override
    public String getQrcodeImg(String wxmpappId) {
        WxmpEntity wxmpEntity = wxmpRepository.findByWxmpappId(wxmpappId);
        AppAssert.notNull(wxmpEntity, "公众号不存在");
        if(StringUtils.isEmpty(wxmpEntity.getQrcodeImg())) {
            try {
                WxMpQrcodeService qrcodeService =  wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(wxmpappId).getQrcodeService();
                WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateLastTicket("init");
                File file = qrcodeService.qrCodePicture(ticket);
                /*FileDetailResDTO resDTO = fileFeignClient.create(new MockMultipartFileDTO("file", file));
                wxmpEntity.setQrcodeImg(resDTO.getFullPath());*/
                wxmpRepository.modify(wxmpEntity);
            } catch (Exception e) {
                logger.error("公众号生成二维码异常:", e);
                throw new BizException("公众号生成二维码异常，请检查");
            }
        }
        return wxmpEntity.getQrcodeImg();
    }

    @Override
    public WxmpEntity findWxmpEntityByShopId(Long shopId) {
        String wxmpappId = weixinShopRelationService.findWxmpappIdByShopId(shopId);
        if(StringUtils.isEmpty(wxmpappId)){
            throw new BizException("公众号appId不能为空,可能没有授权绑定");
        }
        WxmpEntity entity = wxmpRepository.findByWxmpappId(wxmpappId);
        if(entity == null){
            throw new BizException("公众号不存在");
        }
        return entity;
    }

    @Override
    public void saveSubscribeReply(WxmpUpdateDTO wxmpUpdateDTO) {
        WxmpEntity entity = findWxmpEntityByShopId(userCacheData.getCurrentUserWycShopId());
        entity.setSubscribeReply(wxmpUpdateDTO.getSubscribeReply());
        wxmpRepository.modify(entity);
    }

}
