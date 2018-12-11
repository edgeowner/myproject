package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappCodeTemplateCreateDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinMimiappCodeTemplateEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinMimiappCodeTemplateRepository;
import com.huboot.business.common.jpa.QueryCondition;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappCodeTemplateDTO;
import com.huboot.business.base_model.weixin_service.dto.WxCodeTemplate;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.service.IWeixinMimiappCodeTemplateService;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.jpa.ConditionMap;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *小程序代码模板信息表ServiceImpl
 */
@Service("weixinMimiappCodeTemplateServiceImpl")
public class WeixinMimiappCodeTemplateServiceImpl implements IWeixinMimiappCodeTemplateService {

    private Logger logger = LoggerFactory.getLogger(WeixinMimiappCodeTemplateServiceImpl.class);

    @Autowired
    private IWeixinMimiappCodeTemplateRepository codeTemplateRepository;
    @Autowired
    private WxOpenService wxOpenService;
    @Value("${app.domain.zkfront}")
    private String zkfront;
    @Value("${app.domain.zkapi}")
    private String zkapi;

    @Override
    public void create(WeixinMimiappCodeTemplateCreateDTO dto) {
        WeixinMimiappCodeTemplateEntity templateEntity = new WeixinMimiappCodeTemplateEntity();
        templateEntity.setTemplateId(dto.getTemplateId());
        templateEntity.setUserVersion(dto.getUserVersion());
        templateEntity.setUserDesc(dto.getUserDesc());
        try {
            templateEntity.setExtJson(JsonUtils.toJsonString(dto.getExtJson()));
            templateEntity.setCheckList(JsonUtils.toJsonString(dto.getCheckList()));
        } catch (Exception e) {
            throw new BizException("json解析异常");
        }
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
            WeixinMimiappCodeTemplateEntity templateEntity = codeTemplateRepository.findByTemplateId(template.getTemplateId().intValue());
            if(templateEntity != null) {
                dto.setHasAdd("1");
                dto.setHasAddName("是");
            } else {
                dto.setHasAdd("0");
                dto.setHasAddName("否");
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public void deleteTemplate(Integer templateId) throws Exception {
        wxOpenService.getWxOpenComponentService().deleteTemplate(templateId);
    }

    @Transactional
    @Override
    public void becomeSystemTemplate(Integer templateId)throws Exception  {
        WeixinMimiappCodeTemplateEntity templateEntity = codeTemplateRepository.findByTemplateId(templateId);
        if(templateEntity != null) {
            throw new BizException("模板添加到系统");
        }
        List<WxOpenMaCodeTemplate> list = wxOpenService.getWxOpenComponentService().getTemplateList();
        Optional<WxOpenMaCodeTemplate> optional = list.stream().filter(t -> t.getTemplateId().intValue() == templateId.intValue()).findFirst();
        if(!optional.isPresent()) {
            throw new BizException("模板不存在");
        }
        WxOpenMaCodeTemplate template = optional.get();
        WeixinMimiappCodeTemplateEntity codeTemplateEntity = new WeixinMimiappCodeTemplateEntity();
        codeTemplateEntity.setTemplateId(template.getTemplateId().intValue());
        codeTemplateEntity.setUserVersion(template.getUserVersion());
        codeTemplateEntity.setUserDesc(template.getUserDesc());
        if(!zkfront.startsWith("https")) {
            zkfront = zkfront.replace("http", "https");
        }
        if(!zkapi.startsWith("https")) {
            zkapi = zkapi.replace("http", "https");
        }
        String extjson = "{\n" +
                "\t\"extAppid\": \"${extAppid}\",\n" +
                "\t\"ext\": {\n" +
                "\t\t\"shopUid\": \"${shopUid}\",\n" +
                "\t\t\"env\": \"${env}\",\n" +
                "\t\t\"skAppId\": \"${skAppId}\",\n" +
                "\t\t\"baseUrl\": \"" + zkfront + "/\",\n" +
                "\t\t\"requestUrl\": \"" + zkapi + "/api-xiehua/\"\n" +
                "\t}\n" +
                "}";
        codeTemplateEntity.setExtJson(extjson);
        String checkList = "{\n" +
                "  \"item_list\" : [ {\n" +
                "    \"address\" : \"pages/index/index\",\n" +
                "    \"tag\" : \"出行 交通\",\n" +
                "    \"first_class\" : \"出行与交通\",\n" +
                "    \"second_class\" : \"租车\",\n" +
                "    \"first_id\" : \"110\",\n" +
                "    \"second_id\" : \"129\",\n" +
                "    \"title\" : \"首页\"\n" +
                "  }, {\n" +
                "    \"address\" : \"pages/zk/zk\",\n" +
                "    \"tag\" : \"出行 交通\",\n" +
                "    \"first_class\" : \"出行与交通\",\n" +
                "    \"second_class\" : \"租车\",\n" +
                "    \"first_id\" : \"110\",\n" +
                "    \"second_id\" : \"129\",\n" +
                "    \"title\" : \"店铺\"\n" +
                "  } ]\n" +
                "}";
        codeTemplateEntity.setCheckList(checkList);
        codeTemplateRepository.create(codeTemplateEntity);
    }


    @Override
    public Pager<WeixinMimiappCodeTemplateDTO> systemTemplatePager(Integer templateId, String userVersion, Integer page, Integer size) {
        Page<WeixinMimiappCodeTemplateEntity> ep = codeTemplateRepository.findPage(QueryCondition.from(WeixinMimiappCodeTemplateEntity.class).where(list -> {
            if(!StringUtils.isEmpty(templateId)) {
                list.add(ConditionMap.eq("templateId", templateId));
            }
            if(!StringUtils.isEmpty(userVersion)) {
                list.add(ConditionMap.eq("userVersion", userVersion));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "templateId")).limit(page, size));
        Page<WeixinMimiappCodeTemplateDTO> dtoPage = ep.map(entity -> {
            WeixinMimiappCodeTemplateDTO dto = new WeixinMimiappCodeTemplateDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });
        return new Pager(dtoPage);
    }


    @Override
    public WeixinMimiappCodeTemplateDTO systemTemplateDetail(Integer id) {
        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.find(id);
        if(codeTemplateEntity == null) {
            throw new BizException("系统模板不存在");
        }
        WeixinMimiappCodeTemplateDTO dto = new WeixinMimiappCodeTemplateDTO();
        BeanUtils.copyProperties(codeTemplateEntity, dto);
        return dto;
    }

    @Transactional
    @Override
    public void updateSystemTemplate(Integer id, WeixinMimiappCodeTemplateDTO dto) {
        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.find(id);
        if(codeTemplateEntity == null) {
            throw new BizException("系统模板不存在");
        }
        if(StringUtils.isEmpty(dto.getExtJson())) {
            throw new BizException("ExtJson不能为空");
        }
        if(StringUtils.isEmpty(dto.getCheckList())) {
            throw new BizException("CheckList不能为空");
        }
        codeTemplateEntity.setExtJson(dto.getExtJson());
        codeTemplateEntity.setCheckList(dto.getCheckList());
        codeTemplateRepository.update(codeTemplateEntity);
    }
}
