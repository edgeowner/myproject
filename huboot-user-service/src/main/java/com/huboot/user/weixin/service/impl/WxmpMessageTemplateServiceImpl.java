package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.enums.WeixinMessageNodeEnum;
import com.huboot.share.user_service.enums.WeixinTypeEnum;
import com.huboot.user.weixin.dto.WxmpMessageTemplateDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageTemplateCreateDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageTemplatePagerDTO;
import com.huboot.user.weixin.entity.WxmpMessageTemplateEntity;
import com.huboot.user.weixin.repository.IWxmpMessageTemplateRepository;
import com.huboot.user.weixin.service.IWxmpMessageTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *公众号ServiceImpl
 */
@Service("wxmpMessageTemplateServiceImpl")
public class WxmpMessageTemplateServiceImpl implements IWxmpMessageTemplateService {

    private Logger logger = LoggerFactory.getLogger(WxmpMessageTemplateServiceImpl.class);

    @Autowired
    private IWxmpMessageTemplateRepository wxmpMessageTemplateRepository;

    @Override
    public WxmpMessageTemplateDTO findById(Long id) {
        WxmpMessageTemplateEntity entity = wxmpMessageTemplateRepository.find(id);
        AppAssert.notNull(entity, "模板不存在");
        WxmpMessageTemplateDTO dto = new WxmpMessageTemplateDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setNode(entity.getNode().name());
        dto.setOpenType(entity.getOpenType().name());
        dto.setUrlNeedAuth(entity.getUrlNeedAuth().name());
        return dto;
    }

    @Override
    public ShowPageImpl<WxmpMessageTemplatePagerDTO> pager(String node, String templateIdShort, Integer page, Integer size) {
        Page<WxmpMessageTemplateEntity> entityPage = wxmpMessageTemplateRepository.findPage(QueryCondition.from(WxmpMessageTemplateEntity.class)
            .where(list -> {
                if(!StringUtils.isEmpty(node)) {
                    list.add(ConditionMap.eq("node", WeixinMessageNodeEnum.valueOf(node)));
                }
                if(!StringUtils.isEmpty(templateIdShort)) {
                    list.add(ConditionMap.eq("templateIdShort", templateIdShort));
                }
            }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size)
        );
        Page<WxmpMessageTemplatePagerDTO> dtoPage = entityPage.map(entity -> {
            WxmpMessageTemplatePagerDTO dto = new WxmpMessageTemplatePagerDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setNode(entity.getNode().name());
            dto.setNodeName(entity.getNode().getShowName());
            dto.setOpenTypeName(entity.getOpenType().getShowName());
            dto.setUrlNeedAuth(entity.getUrlNeedAuth().getShowName());
            return dto;
        });
        return ShowPageImpl.pager(dtoPage);
    }

    @Transactional
    @Override
    public WxmpMessageTemplateEntity createTemplate(WxmpMessageTemplateCreateDTO createDTO) {
        WxmpMessageTemplateEntity templateEntity = wxmpMessageTemplateRepository.findByNode(WeixinMessageNodeEnum.valueOf(createDTO.getNode()));
        if(templateEntity != null) {
            throw new BizException("消息节点已存在");
        }
        templateEntity = new WxmpMessageTemplateEntity();
        templateEntity.setNode(WeixinMessageNodeEnum.valueOf(createDTO.getNode()));
        templateEntity.setTemplateIdShort(createDTO.getTemplateIdShort());
        templateEntity.setOpenType(WeixinTypeEnum.valueOf(createDTO.getOpenType()));
        templateEntity.setUrl(createDTO.getUrl());
        templateEntity.setMiniPagepath(createDTO.getMiniPagepath());
        templateEntity.setUrlNeedAuth(YesOrNoEnum.valueOf(createDTO.getUrlNeedAuth()));
        templateEntity.setRemark(createDTO.getRemark());
        return wxmpMessageTemplateRepository.create(templateEntity);
    }

    @Override
    public List<WxmpMessageTemplateEntity> findSameTemplateIdShort(Long id) {
        List<WxmpMessageTemplateEntity> list = new ArrayList<>();
        WxmpMessageTemplateEntity templateEntity = wxmpMessageTemplateRepository.find(id);
        if(templateEntity == null) {
            return list;
        }
        return wxmpMessageTemplateRepository.findByTemplateIdShort(templateEntity.getTemplateIdShort());
    }

    @Transactional
    @Override
    public void edit(WxmpMessageTemplateDTO templateDTO) {
        WxmpMessageTemplateEntity entity = wxmpMessageTemplateRepository.find(templateDTO.getId());
        AppAssert.notNull(entity, "模板不存在");
        entity.setOpenType(WeixinTypeEnum.valueOf(templateDTO.getOpenType()));
        entity.setUrl(templateDTO.getUrl());
        entity.setMiniPagepath(templateDTO.getMiniPagepath());
        entity.setUrlNeedAuth(YesOrNoEnum.valueOf(templateDTO.getUrlNeedAuth()));
        entity.setRemark(templateDTO.getRemark());
        wxmpMessageTemplateRepository.modify(entity);
    }
}
