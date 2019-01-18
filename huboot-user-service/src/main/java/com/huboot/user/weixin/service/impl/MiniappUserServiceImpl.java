package com.huboot.user.weixin.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.huboot.commons.utils.AppAssert;
import com.huboot.user.weixin.dto.MiniappUserDTO;
import com.huboot.user.weixin.entity.MiniappUserEntity;
import com.huboot.user.weixin.repository.IMiniappUserRepository;
import com.huboot.user.weixin.service.IMiniappUserService;
import com.huboot.user.weixin.support.WxServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * 小程序微信用户ServiceImpl
 */
@Service("mimiappUserServiceImpl")
public class MiniappUserServiceImpl implements IMiniappUserService {

    private Logger logger = LoggerFactory.getLogger(MiniappUserServiceImpl.class);

    @Autowired
    private IMiniappUserRepository userRepository;
    @Autowired
    private WxServiceFactory wxServiceFactory;

    @Transactional
    @Override
    public MiniappUserEntity saveWeixinUser(String appId, WxMaJscode2SessionResult accessToken) throws Exception {
        MiniappUserEntity userEntity = userRepository.findByOpenId(accessToken.getOpenid());
        if (userEntity == null) {
            userEntity = new MiniappUserEntity();
            userEntity.setMiniappId(appId);
            userEntity.setOpenId(accessToken.getOpenid());
            userEntity.setSessionKey(accessToken.getSessionKey());
            userEntity.setUnionId(StringUtils.isEmpty(accessToken.getUnionid()) ? "" : accessToken.getUnionid());
            userRepository.create(userEntity);
        } else {
            if(StringUtils.isEmpty(userEntity.getUnionId())) {
                userEntity.setUnionId(StringUtils.isEmpty(accessToken.getUnionid()) ? "" : accessToken.getUnionid());
            }
            userEntity.setSessionKey(accessToken.getSessionKey());
            userRepository.modify(userEntity);
        }
        return userEntity;
    }

    @Override
    public MiniappUserDTO findByOpenId(String opendId) {
        MiniappUserEntity userEntity = userRepository.findByOpenId(opendId);
        AppAssert.notNull(userEntity, "微信用户不存在");
        MiniappUserDTO userDTO = new MiniappUserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }


    @Override
    public String getPhoneNumber(String encryptedData, String iv, String openId) {
        MiniappUserEntity userEntity = userRepository.findByOpenId(openId);
        AppAssert.notNull(userEntity, "微信用户不存在");
        WxMaUserService userService = wxServiceFactory.getWxMaService(userEntity.getMiniappId()).getUserService();
        WxMaPhoneNumberInfo numberInfo = userService.getPhoneNoInfo(userEntity.getSessionKey(), encryptedData, iv);
        return numberInfo.getPhoneNumber();
    }

}
