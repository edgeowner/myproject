package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicTempalteEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinTempalteEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicTempalteRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinTempalteRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicTempalteService;
import com.huboot.business.base_model.weixin_service.service.IWeixinTempalteService;
import com.huboot.business.common.jpa.QueryCondition;
import com.huboot.business.base_model.weixin_service.dto.WeixinTempalteAddDTO;

import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.jpa.ConditionMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 微信模板信息表ServiceImpl
 */
@Service("weixinTempalteServiceImpl")
public class WeixinTempalteServiceImpl implements IWeixinTempalteService {

    private Logger logger = LoggerFactory.getLogger(WeixinTempalteServiceImpl.class);

    @Autowired
    private IWeixinTempalteRepository weixinTempalteRepository;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private IWeixinPublicTempalteRepository weixinPublicTempalteRepository;
    @Autowired
    private IWeixinPublicTempalteService weixinPublicTempalteService;

    @Transactional
    @Override
    public Integer create(WeixinTempalteAddDTO dto, Integer initType) throws BizException {
        Assert.notNull(dto.getSystem(), "系统不能为空");
        Assert.notNull(dto.getNode(), "微信通知节点不能为空");
        Assert.notNull(dto.getTemplateIdShort(), "微信模板的编号不能为空");
        Assert.notNull(dto.getTitle(), "微信模板的标题不能为空");
        WeixinTempalteEntity entity = new WeixinTempalteEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(WeixinTempalteEntity.StatusEnum.tempalte_save);
        entity = weixinTempalteRepository.create(entity);
        if(initType == 1) {
            this.setTempalteForAllPublic(entity);
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public void initTempalteForAllPublic(Integer stid) throws BizException {
        Assert.notNull(stid, "消息模板记录id不能为空");
        WeixinTempalteEntity tempalteEntity = weixinTempalteRepository.find(stid);
        Assert.notNull(tempalteEntity, "消息模板记录不存在");
        this.setTempalteForAllPublic(tempalteEntity);
    }

    /**
     *
     * @param finalEntity
     */
    private void setTempalteForAllPublic(final WeixinTempalteEntity finalEntity) {
        List<WeixinTempalteEntity> tempalteEntities = weixinTempalteRepository.findByCondition(QueryCondition.from(WeixinTempalteEntity.class)
                .where( list ->{
                    list.add(ConditionMap.eq("templateIdShort", finalEntity.getTemplateIdShort()));
                    list.add(ConditionMap.ne("id", finalEntity.getId()));
                }).limit(Integer.MAX_VALUE));

        // WeixinPublicEntity  条件  system=1 and type=1 and bindType=2
        List<WeixinPublicEntity> publicList = weixinPublicRepository.findByTypeAndBindType(WeixinPublicEntity.TypeEnum.pubapp, WeixinPublicEntity.BindTypeEnum.weixin3open);
        for (WeixinPublicEntity publicEntity : publicList) {
            if (!CollectionUtils.isEmpty(tempalteEntities)) {
                WeixinPublicTempalteEntity publicTempalteEntity = weixinPublicTempalteRepository.findByWeixinUidAndRelaTemplateId(publicEntity.getWeixinUid(), tempalteEntities.get(0).getId());
                if (publicTempalteEntity != null) {
                    WeixinPublicTempalteEntity publicTempalteEntity1 = new WeixinPublicTempalteEntity();
                    publicTempalteEntity1.setWeixinUid(publicEntity.getWeixinUid());
                    publicTempalteEntity1.setRelaTemplateId(finalEntity.getId());
                    publicTempalteEntity1.setTemplateId(publicTempalteEntity.getTemplateId());
                    weixinPublicTempalteRepository.create(publicTempalteEntity1);
                } else {
                    try {
                        weixinPublicTempalteService.setPublicTempalte(finalEntity, publicEntity);
                    } catch (Exception e) {
                        logger.warn("公众号模板设置异常,weixinuid={}", publicEntity.getWeixinUid(), e);
                    }
                }
            } else {
                try {
                    weixinPublicTempalteService.setPublicTempalte(finalEntity, publicEntity);
                } catch (Exception e) {
                    logger.warn("公众号模板设置异常,weixinuid={}", publicEntity.getWeixinUid(), e);
                }
            }
        }
    }

}
