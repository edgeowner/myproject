package com.huboot.business.base_model.weixin_service.service;


import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappCodeTemplateCreateDTO;
import com.huboot.business.base_model.weixin_service.dto.WxCodeTemplate;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappCodeTemplateDTO;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;

import java.util.List;

/**
 *小程序代码模板信息表Service
 */
public interface IWeixinMimiappCodeTemplateService {

    void create(WeixinMimiappCodeTemplateCreateDTO dto);

    List<WxOpenMaCodeTemplate> getDraftList()throws Exception;

    void becomeTemplate(Long draftId) throws Exception;

    List<WxCodeTemplate> getTemplateList()throws Exception;

    void deleteTemplate(Integer templateId) throws Exception;

    void becomeSystemTemplate(Integer templateId)throws Exception ;

    Pager<WeixinMimiappCodeTemplateDTO> systemTemplatePager(Integer templateId, String userVersion, Integer page, Integer size);

    WeixinMimiappCodeTemplateDTO systemTemplateDetail(Integer id);

    void updateSystemTemplate(Integer id, WeixinMimiappCodeTemplateDTO dto);

}
