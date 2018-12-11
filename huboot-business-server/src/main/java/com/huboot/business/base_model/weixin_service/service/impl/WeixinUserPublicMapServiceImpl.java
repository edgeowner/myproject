package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.service.IWeixinUserPublicMapService;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserPublicMapEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserPublicMapRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
@Service("weixinUserPublicMapServiceImpl")
public class WeixinUserPublicMapServiceImpl implements IWeixinUserPublicMapService {

    private Logger logger = LoggerFactory.getLogger(WeixinUserPublicMapServiceImpl.class);

    @Autowired
    private IWeixinUserPublicMapRepository weixinUserPublicMapRepository;

    @Transactional
    @Override
    public void createPublicMap(WeixinAuthDTO authDTO, String weixinUid) {
        WeixinUserPublicMapEntity publicMapEntity = weixinUserPublicMapRepository.findByOpenIdAndWeixinUid(authDTO.getOpenId(), weixinUid);
        if (publicMapEntity == null) {
            publicMapEntity = new WeixinUserPublicMapEntity();
            publicMapEntity.setOpenId(authDTO.getOpenId());
            publicMapEntity.setWeixinUid(weixinUid);
            publicMapEntity.setUnionid(authDTO.getUnionid());
            publicMapEntity.setSessionKey(authDTO.getSessionKey());
            weixinUserPublicMapRepository.create(publicMapEntity);
        } else {
            if(!StringUtils.isEmpty(authDTO.getSessionKey())) {
                if(!authDTO.getSessionKey().equals(publicMapEntity.getSessionKey())) {
                    publicMapEntity.setSessionKey(authDTO.getSessionKey());
                    weixinUserPublicMapRepository.update(publicMapEntity);
                }
            }
        }
    }

}
