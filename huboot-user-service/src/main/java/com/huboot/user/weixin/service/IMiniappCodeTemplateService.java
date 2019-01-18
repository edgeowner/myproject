package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.MiniappCodeTemplateDTO;
import com.huboot.user.weixin.dto.admin.MiniappCodeTemplateCreateDTO;
import com.huboot.user.weixin.dto.admin.WxCodeTemplate;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;

import java.util.List;

/**
 *小程序代码库Service
 */
public interface IMiniappCodeTemplateService {

    void addTemplate(MiniappCodeTemplateCreateDTO createDTO);

    void getAndAddTemplate(Long templateId) throws Exception;

    ShowPageImpl<MiniappCodeTemplateDTO> getPager(Integer page, Integer size);

    List<WxOpenMaCodeTemplate> getDraftList() throws Exception;

    List<WxCodeTemplate> getTemplateList()throws Exception;

    void becomeTemplate(Long draftId) throws Exception;

    void deleteTemplate(Long templateId) throws Exception;

}
