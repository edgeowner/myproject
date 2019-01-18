package com.huboot.user.weixin.service.impl;

import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.share.user_service.data.ShopCacheData;
import com.huboot.share.user_service.enums.OpenBindStatusEnum;
import com.huboot.user.weixin.dto.admin.WeixinShopRelationPagerDTO;
import com.huboot.user.weixin.entity.MiniappEntity;
import com.huboot.user.weixin.entity.WeixinShopRelationEntity;
import com.huboot.user.weixin.entity.WxmpEntity;
import com.huboot.user.weixin.repository.IMiniappRepository;
import com.huboot.user.weixin.repository.IWeixinShopRelationRepository;
import com.huboot.user.weixin.repository.IWxmpRepository;
import com.huboot.user.weixin.service.IOpenAppService;
import com.huboot.user.weixin.service.IWeixinShopRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/12/3 0003.
 */
@Service
public class WeixinShopRelationServiceImpl implements IWeixinShopRelationService {

    private Logger logger = LoggerFactory.getLogger(WeixinShopRelationServiceImpl.class);

    @Autowired
    private IWeixinShopRelationRepository weixinShopRelationRepository;
    @Autowired
    private IOpenAppService openAppService;
    @Autowired
    private ShopCacheData shopCacheData;
    @Autowired
    private IMiniappRepository miniappRepository;
    @Autowired
    private IWxmpRepository wxmpRepository;

    @Override
    public String findMiniappIdByShopId(Long shopId) {
        if(shopId == null) {
            logger.error("findMiniappIdByShopId shopId 为空");
            return null;
        }
        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByShopId(shopId);
        if(relationEntity != null) {
            return relationEntity.getMiniappId();
        }
        logger.error("findMiniappIdByShopId relationEntity 为空");
        return null;
    }

    @Override
    public String findWxmpappIdByShopId(Long shopId) {
        if(shopId == null) {
            logger.error("findWxmpappIdByShopId shopId 为空");
            return null;
        }
        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByShopId(shopId);
        if(relationEntity != null) {
            return relationEntity.getWxmpId();
        }
        logger.error("findWxmpappIdByShopId relationEntity 为空");
        return null;
    }

    @Override
    public Long findShopIdByMiniappId(String miniappId) {
        if(StringUtils.isEmpty(miniappId)) {
            return null;
        }
        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByMiniappId(miniappId);
        if(relationEntity != null) {
            return relationEntity.getShopId();
        }
        return null;
    }

    @Override
    public Long findShopIdByWxmpappId(String wxmpappId) {
        if(StringUtils.isEmpty(wxmpappId)) {
            return null;
        }
        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByWxmpId(wxmpappId);
        if(relationEntity != null) {
            return relationEntity.getShopId();
        }
        return null;
    }

    @Override
    public void saveMiniappRelation(String miniappId, Long shopId) {
        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByShopId(shopId);
        if(relationEntity == null) {
            relationEntity = new WeixinShopRelationEntity();
            relationEntity.setShopId(shopId);
            ShopDetaiInfo detaiInfo = shopCacheData.getShopById(shopId);
            if(detaiInfo != null) {
                relationEntity.setShopSn(detaiInfo.getSn());
            }
            relationEntity.setMiniappId(miniappId);
            String openAppId = openAppService.createOpen(miniappId);
            relationEntity.setOpenAppid(openAppId);
            relationEntity.setMiniappBindStatus(OpenBindStatusEnum.bind);
            relationEntity.setWxmpBindStatus(OpenBindStatusEnum.unbind);
            weixinShopRelationRepository.create(relationEntity);
        } else {
            relationEntity.setMiniappId(miniappId);
            if(StringUtils.isEmpty(relationEntity.getOpenAppid())) {
                String openAppId = openAppService.createOpen(miniappId);
                relationEntity.setOpenAppid(openAppId);
            } else {
                openAppService.bindOpen(miniappId, relationEntity.getOpenAppid());
            }
            relationEntity.setMiniappBindStatus(OpenBindStatusEnum.bind);
            weixinShopRelationRepository.modify(relationEntity);
        }
    }


    @Override
    public void saveWxmpRelation(String wxmpId, Long shopId) {
        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByShopId(shopId);
        if(relationEntity == null) {
            relationEntity = new WeixinShopRelationEntity();
            relationEntity.setShopId(shopId);
            ShopDetaiInfo detaiInfo = shopCacheData.getShopById(shopId);
            if(detaiInfo != null) {
                relationEntity.setShopSn(detaiInfo.getSn());
            }
            relationEntity.setWxmpId(wxmpId);
            String openAppId = openAppService.createOpen(wxmpId);
            relationEntity.setOpenAppid(openAppId);
            relationEntity.setMiniappBindStatus(OpenBindStatusEnum.unbind);
            relationEntity.setWxmpBindStatus(OpenBindStatusEnum.bind);
            weixinShopRelationRepository.create(relationEntity);
        } else {
            relationEntity.setWxmpId(wxmpId);
            if(StringUtils.isEmpty(relationEntity.getOpenAppid())) {
                String openAppId = openAppService.createOpen(wxmpId);
                relationEntity.setOpenAppid(openAppId);
            } else {
                openAppService.bindOpen(wxmpId, relationEntity.getOpenAppid());
            }
            relationEntity.setWxmpBindStatus(OpenBindStatusEnum.bind);
            weixinShopRelationRepository.modify(relationEntity);
        }
    }


    @Override
    public ShowPageImpl<WeixinShopRelationPagerDTO> getPager(Integer page, Integer size) {
        Page<WeixinShopRelationEntity> entityPager = weixinShopRelationRepository.findPage(QueryCondition.from(WeixinShopRelationEntity.class).limit(page, size));
        Page<WeixinShopRelationPagerDTO> dtoPager = entityPager.map(entity -> {
            WeixinShopRelationPagerDTO dto = new WeixinShopRelationPagerDTO();
            dto.setShopId(entity.getShopId());
            dto.setShopSn(entity.getShopSn());
            ShopDetaiInfo detaiInfo = shopCacheData.getShopById(entity.getShopId());
            if(detaiInfo != null) {
                dto.setShopName(detaiInfo.getName());
            }
            dto.setOpenAppid(entity.getOpenAppid());
            dto.setMiniappId(entity.getMiniappId());
            MiniappEntity miniappEntity = miniappRepository.findByMiniappId(entity.getMiniappId());
            if(miniappEntity != null) {
                dto.setMiniappName(miniappEntity.getNickName());
            }
            dto.setMiniappBindStatus(entity.getMiniappBindStatus().getShowName());
            dto.setWxmpId(entity.getWxmpId());
            WxmpEntity wxmpEntity = wxmpRepository.findByWxmpappId(entity.getWxmpId());
            if(wxmpEntity != null) {
                dto.setWxmpName(wxmpEntity.getNickName());
            }
            dto.setWxmpBindStatus(entity.getWxmpBindStatus().getShowName());
            return dto;
        });
        return ShowPageImpl.pager(dtoPager);
    }


    @Override
    public void initOpenapp() {
        List<WeixinShopRelationEntity> relationList = weixinShopRelationRepository.findAll();
        for(WeixinShopRelationEntity relationEntity : relationList) {
            if(StringUtils.isEmpty(relationEntity.getShopSn())) {
                ShopDetaiInfo detaiInfo = shopCacheData.getShopById(relationEntity.getShopId());
                if(detaiInfo != null) {
                    relationEntity.setShopSn(detaiInfo.getSn());
                }
            }
            if(StringUtils.isEmpty(relationEntity.getOpenAppid())) {
                try {
                    relationEntity.setOpenAppid(openAppService.createOpen(relationEntity.getMiniappId()));
                    relationEntity.setMiniappBindStatus(OpenBindStatusEnum.bind);
                } catch (Exception e) {
                    logger.error("创建openapp异常，MiniappId=" + relationEntity.getMiniappId());
                }
                weixinShopRelationRepository.modify(relationEntity);
            }
        }
    }
}
