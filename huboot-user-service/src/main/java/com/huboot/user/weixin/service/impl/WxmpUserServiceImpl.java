package com.huboot.user.weixin.service.impl;


import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.component.redis.RedisHelper;
import com.huboot.commons.utils.EmojiFilterUtil;
import com.huboot.share.common.constant.RedisQueueName;
import com.huboot.user.weixin.entity.WxmpEntity;
import com.huboot.user.weixin.entity.WxmpUserEntity;
import com.huboot.user.weixin.entity.WxmpUserSubscribeLogEntity;
import com.huboot.user.weixin.repository.IWxmpRepository;
import com.huboot.user.weixin.repository.IWxmpUserRepository;
import com.huboot.user.weixin.repository.IWxmpUserSubscribeLogRepository;
import com.huboot.user.weixin.service.IWxmpUserService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *小程序微信用户ServiceImpl
 */
@Service("wxmpUserServiceImpl")
public class WxmpUserServiceImpl implements IWxmpUserService {

    private Logger logger = LoggerFactory.getLogger(WxmpUserServiceImpl.class);

    @Autowired
    private IWxmpUserRepository wxmpUserRepository;
    @Autowired
    private IWxmpUserSubscribeLogRepository subscribeLogRepository;
    @Autowired
    private IWxmpRepository wxmpRepository;
    @Autowired
    private RedisHelper redisHelper;

    @Transactional
    @Override
    public String saveWeixinUserOnSubscribe(String originalId, String openId,
                                          String eventKey, WxMpService weixinService) {
        WxmpEntity wxmpEntity = wxmpRepository.findByOriginalId(originalId);
        if(wxmpEntity == null) {
            logger.error("公众号信息不存在,originalId={}", originalId);
            throw new BizException("公众号信息不存在,originalId="+originalId);
        }
        WxmpUserEntity userEntity = wxmpUserRepository.findByOpenId(openId);
        if(userEntity == null) {
            userEntity = new WxmpUserEntity();
            userEntity.setWxmpappId(wxmpEntity.getWxmpappId());
            userEntity.setOpenId(openId);
            userEntity.setSubscribe(1);
            WxMpUser wxMpUser = this.getWxMpUser(wxmpEntity.getWxmpappId(), openId, weixinService);
            if(wxMpUser != null) {
                userEntity.setUnionId(StringUtils.isEmpty(wxMpUser.getUnionId()) ? "" : wxMpUser.getUnionId());
                userEntity.setNickname(EmojiFilterUtil.filterEmoji(wxMpUser.getNickname()));
                userEntity.setHeadImgUrl(wxMpUser.getHeadImgUrl());
            }
            wxmpUserRepository.create(userEntity);

            //第一次关注，粉丝数累加
            String sn = eventKey;
            if(!StringUtils.isEmpty(sn)) {
                //sn变化不会改变eventKey的值
                redisHelper.sendMessage(RedisQueueName.WXMP_SUBSCRIBE, sn.replace("qrscene_", ""));
            }

        } else {
            if(StringUtils.isEmpty(userEntity.getUnionId())) {
                WxMpUser wxMpUser = this.getWxMpUser(wxmpEntity.getWxmpappId(), openId, weixinService);
                if(wxMpUser != null) {
                    userEntity.setUnionId(wxMpUser.getUnionId());
                    userEntity.setNickname(wxMpUser.getNickname());
                    userEntity.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                }
            }
            userEntity.setSubscribe(1);
            wxmpUserRepository.modify(userEntity);
        }
        WxmpUserSubscribeLogEntity logEntity = new WxmpUserSubscribeLogEntity();
        logEntity.setWxmpappId(wxmpEntity.getWxmpappId());
        logEntity.setOpenId(openId);
        logEntity.setQrScene(eventKey);
        subscribeLogRepository.create(logEntity);

        String subscribeReply = wxmpEntity.getSubscribeReply();
        if(StringUtils.isEmpty(subscribeReply)){
            return "感谢关注";
        }else{
            return subscribeReply;
        }

    }

    private WxMpUser getWxMpUser(String wxmpappId, String openId, WxMpService weixinService) {
        WxMpUserService wxMpUserService = weixinService.getUserService();
        try {
            WxMpUser wxMpUser = wxMpUserService.userInfo(openId);
            return wxMpUser;
        } catch (Exception e) {
            logger.error("获取微信用户信息异常，wxmpappId={}，openId={}", wxmpappId, openId);
        }
        return null;
    }

    @Transactional
    @Override
    public void updateWeixinUserOnUnSubscribe(String openId) {
        WxmpUserEntity userEntity = wxmpUserRepository.findByOpenId(openId);
        if(userEntity != null) {
            userEntity.setSubscribe(0);
            wxmpUserRepository.modify(userEntity);
        }
    }

}
