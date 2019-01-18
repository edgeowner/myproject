package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.user.weixin.dto.MiniappCodeTemplateDTO;
import com.huboot.user.weixin.dto.admin.MiniappCodeTemplateCreateDTO;
import com.huboot.user.weixin.dto.admin.WxCodeTemplate;
import com.huboot.user.weixin.entity.MiniappCodeTemplateEntity;
import com.huboot.user.weixin.repository.IMiniappCodeTemplateRepository;
import com.huboot.user.weixin.service.IMiniappCodeTemplateService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *小程序代码库ServiceImpl
 */
@Service("miniappCodeTemplateServiceImpl")
public class MiniappCodeTemplateServiceImpl implements IMiniappCodeTemplateService {

    private Logger logger = LoggerFactory.getLogger(MiniappCodeTemplateServiceImpl.class);

    @Autowired
    private IMiniappCodeTemplateRepository codeTemplateRepository;
    @Autowired
    private WxOpenService wxOpenService;

    @Transactional
    @Override
    public void addTemplate(MiniappCodeTemplateCreateDTO createDTO) {
        MiniappCodeTemplateEntity templateEntity = codeTemplateRepository.findByTemplateId(createDTO.getTemplateId());
        if(templateEntity != null) {
            throw new BizException("代码模板id已经存在");
        }
        templateEntity = new  MiniappCodeTemplateEntity();
        templateEntity.setTemplateId(createDTO.getTemplateId());
        templateEntity.setUserDesc(createDTO.getUserDesc());
        templateEntity.setUserVersion(createDTO.getUserVersion());
        codeTemplateRepository.create(templateEntity);
    }

    @Override
    public List<WxOpenMaCodeTemplate> getDraftList() throws Exception {
        return wxOpenService.getWxOpenComponentService().getTemplateDraftList();
    }

    @Override
    public void becomeTemplate(Long draftId) throws Exception {
        wxOpenService.getWxOpenComponentService().addToTemplate(draftId);
    }

    @Override
    public void deleteTemplate(Long templateId) throws Exception {
        wxOpenService.getWxOpenComponentService().deleteTemplate(templateId);
    }

    @Override
    public List<WxCodeTemplate> getTemplateList() throws Exception{
        List<WxCodeTemplate> dtoList = new ArrayList<>();
        List<WxOpenMaCodeTemplate> templateList = wxOpenService.getWxOpenComponentService().getTemplateList();
        templateList.sort((o1, o2) -> o1.getCreateTime() > o2.getCreateTime() ? -1 : 1);
        for(int i = 0; i < templateList.size(); i++) {
            WxOpenMaCodeTemplate template = templateList.get(i);
            WxCodeTemplate dto = new WxCodeTemplate();
            dto.setUserVersion(template.getUserVersion());
            dto.setUserDesc(template.getUserDesc());
            dto.setTemplateId(template.getTemplateId().toString());
            MiniappCodeTemplateEntity templateEntity = codeTemplateRepository.findByTemplateId(template.getTemplateId().toString());
            if(templateEntity != null) {
                dto.setHasAdd(YesOrNoEnum.yes.name());
                dto.setHasAddName(YesOrNoEnum.yes.getShowName());
            } else {
                dto.setHasAdd(YesOrNoEnum.no.name());
                dto.setHasAddName(YesOrNoEnum.no.getShowName());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public void getAndAddTemplate(Long templateId) throws Exception {
        List<WxOpenMaCodeTemplate> templateList = wxOpenService.getWxOpenComponentService().getTemplateList();
        MiniappCodeTemplateCreateDTO createDTO = null;
        for(WxOpenMaCodeTemplate codeTemplate : templateList) {
            if(codeTemplate.getTemplateId().longValue() == templateId.longValue()) {
                createDTO = new MiniappCodeTemplateCreateDTO();
                createDTO.setTemplateId(codeTemplate.getTemplateId().toString());
                createDTO.setUserDesc(codeTemplate.getUserDesc());
                createDTO.setUserVersion(codeTemplate.getUserVersion());
                break;
            }
        }
        if(createDTO == null) {
            throw new BizException("版本不存在");
        }
        this.addTemplate(createDTO);
    }

    @Override
    public ShowPageImpl<MiniappCodeTemplateDTO> getPager(Integer page, Integer size) {
        Page<MiniappCodeTemplateEntity> pager = codeTemplateRepository.findPage(QueryCondition.from(MiniappCodeTemplateEntity.class)
                .sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));
        Page<MiniappCodeTemplateDTO> dtoPager = pager.map(entity -> {
            MiniappCodeTemplateDTO dto = new MiniappCodeTemplateDTO();
            dto.setId(entity.getId());
            dto.setTemplateId(entity.getTemplateId());
            dto.setUserDesc(entity.getUserDesc());
            dto.setUserVersion(entity.getUserVersion());
            return dto;
        });
        return new ShowPageImpl(dtoPager);
    }
}
