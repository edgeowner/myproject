package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserPublicMapEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserPublicMapRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserService;
import com.huboot.business.base_model.weixin_service.support.WechatMpFactory;
import com.huboot.business.common.component.exception.BizException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
@Service("weixinUserServiceImpl")
public class WeixinUserServiceImpl implements IWeixinUserService {

    private Logger logger = LoggerFactory.getLogger(WeixinPublicServiceImpl.class);

    @Autowired
    private IWeixinUserRepository weixinUserRepository;

    @Autowired
    private WechatMpFactory wechatMpFactory;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;

    @Autowired
    private IWeixinUserPublicMapRepository userPublicMapRepository;


    @Transactional
    @Override
    public WeixinUserEntity setPubUser(WeixinPublicEntity publicEntity, String openId) {
        WeixinUserEntity userEntity = weixinUserRepository.findByOpenId(openId);
        if (userEntity == null) {
            WxMpUser wxuser = getWxMpUser(publicEntity, openId);
            userEntity = new WeixinUserEntity();
            userEntity.setOpenId(openId);
            userEntity.setCity(wxuser.getCity());
            userEntity.setProvince(wxuser.getProvince());
            userEntity.setCountry(wxuser.getCountry());
            userEntity.setNickname(this.filterEmoji(wxuser.getNickname()));
            userEntity.setSex(wxuser.getSex());
            userEntity.setHeadimgurl(wxuser.getHeadImgUrl());
            weixinUserRepository.create(userEntity);
        }
        return userEntity;
    }

    private WxMpUser getWxMpUser(WeixinPublicEntity publicEntity, String openId) {
        try {
            WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
            return wxMpService.getUserService().userInfo(openId, "zh_CN");
        } catch (WxErrorException e) {
            logger.error("获取微信用户信息异常", e);
            throw new BizException(BizException.RETRY, "获取微信用户信息异常");
        }
    }

    /**
     * 过滤昵称中的特殊字符
     *
     * @param source
     * @return
     */
    private String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "#");
        } else {
            return source;
        }
    }

    /**
     * 用户取消关注
     * 1.公众号未配置好之前，用户已经关注，配置好之后取消关注
     * 2.公众号未配置好之后，用户已经关注，然后取消关注
     *
     * @param originalId 公众号id
     * @param openId     用户openid
     */
    @Transactional
    @Override
    public void unsubscribe(String originalId, String openId) {
        try {
            WeixinUserEntity userEntity = weixinUserRepository.findByOpenId(openId);
            if (userEntity != null) {
                userEntity.setSubscribe(0);
                userEntity.setSubscribeTime(null);
                userEntity.setSubscribeSource("");
                weixinUserRepository.update(userEntity);
                //不用解绑
                //b2CShopService.unBindUserOpenIdToShop(openId);
            }
        } catch (Exception e) {
            logger.error("取消关注异常", e);
        }
    }


    /**
     * 用户关注公众号
     * 1.用户在公众号未配置好的时候关注了，
     * 2.用户在公众号配置好后关注了
     *
     * @param originalId 公众号id
     * @param openId     用户openid
     */
    @Transactional
    @Override
    public void subscribe(String originalId, String openId, String eventKey) {
        try {
            WeixinPublicEntity publicEntity = weixinPublicRepository.findByOriginalId(originalId);
            if (publicEntity == null) {
                logger.error("公众配置号不存在！originalId={}", originalId);
                return;
            }
            WeixinUserEntity userEntity = weixinUserRepository.findByOpenId(openId);
            if(userEntity == null) {//从未关注过
                logger.info("还未关注：opendId={}, weixinUid={}", openId, publicEntity.getWeixinUid());
                WxMpUser wxuser = getWxMpUser(publicEntity, openId);
                userEntity = new WeixinUserEntity();
                userEntity.setOpenId(openId);
                userEntity.setCity(wxuser.getCity());
                userEntity.setProvince(wxuser.getProvince());
                userEntity.setCountry(wxuser.getCountry());
                userEntity.setNickname(this.filterEmoji(wxuser.getNickname()));
                userEntity.setSex(wxuser.getSex());
                userEntity.setHeadimgurl(wxuser.getHeadImgUrl());
                userEntity.setHisSubscribe(1);
                userEntity.setSubscribeSource(eventKey);
                userEntity.setSubscribeTime(LocalDateTime.now());
                weixinUserRepository.create(userEntity);
            } else {
                logger.info("重新关注：opendId={}, weixinUid={}", openId, publicEntity.getWeixinUid());
                userEntity.setSubscribe(1);
                userEntity.setHisSubscribe(1);
                userEntity.setSubscribeSource(eventKey);
                userEntity.setSubscribeTime(LocalDateTime.now());
                weixinUserRepository.update(userEntity);
            }
        } catch (Exception e) {
            logger.error("关注异常", e);
        }
    }


    @Transactional
    @Override
    public void userYouhua() {
        logger.info("開始初始化微信用戶信息");
        List<WeixinPublicEntity> list = weixinPublicRepository.findByTypeAndSystem(WeixinPublicEntity.TypeEnum.pubapp, SystemEnum.zk.getVal());
        for(WeixinPublicEntity publicEntity : list) {
            List<WeixinUserPublicMapEntity> userList = userPublicMapRepository.findByWeixinUid(publicEntity.getWeixinUid());
            for(WeixinUserPublicMapEntity userPublicMapEntity : userList) {
                if(StringUtils.isEmpty(userPublicMapEntity.getOpenId())) {
                    try {
                        userPublicMapRepository.remove(userPublicMapEntity.getId());
                    } catch (Exception e) {
                        logger.error("userPublicMapEntity.id=" + userPublicMapEntity.getId(), e);
                    }
                    continue;
                }
                WeixinUserEntity userEntity = weixinUserRepository.findByOpenId(userPublicMapEntity.getOpenId());
                if (userEntity == null) {
                    WxMpUser wxuser;
                    try {
                        wxuser = getWxMpUser(publicEntity, userPublicMapEntity.getOpenId());
                    } catch (Exception e) {
                        logger.error("userPublicMapEntity.id=" + userPublicMapEntity.getId(), e);
                        continue;
                    }
                    userEntity = new WeixinUserEntity();
                    userEntity.setOpenId(userPublicMapEntity.getOpenId());
                    userEntity.setCity(wxuser.getCity());
                    userEntity.setProvince(wxuser.getProvince());
                    userEntity.setCountry(wxuser.getCountry());
                    userEntity.setNickname(this.filterEmoji(wxuser.getNickname()));
                    userEntity.setSex(wxuser.getSex());
                    userEntity.setHeadimgurl(wxuser.getHeadImgUrl());
                    userEntity.setSubscribe(wxuser.getSubscribe() ? 1 : 0);
                    userEntity.setHisSubscribe(1);
                    userEntity.setSubscribeSource(userPublicMapEntity.getExtendEntity().getEventKey());
                    if(wxuser.getSubscribeTime() != null) {
                        userEntity.setSubscribeTime(userPublicMapEntity.getCreateTime());
                    }
                    weixinUserRepository.create(userEntity);
                }
            }
        }
        logger.info("微信用戶信息初始化完成");
    }
}
