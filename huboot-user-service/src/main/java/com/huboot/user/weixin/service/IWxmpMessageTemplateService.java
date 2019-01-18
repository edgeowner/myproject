package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.WxmpMessageTemplateDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageTemplateCreateDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageTemplatePagerDTO;
import com.huboot.user.weixin.entity.WxmpMessageTemplateEntity;

import java.util.List;

/**
 *公众号Service
 */
public interface IWxmpMessageTemplateService {

    WxmpMessageTemplateDTO findById(Long id);

    ShowPageImpl<WxmpMessageTemplatePagerDTO> pager(String node, String templateIdShort, Integer page, Integer size);

    WxmpMessageTemplateEntity createTemplate(WxmpMessageTemplateCreateDTO createDTO);

    List<WxmpMessageTemplateEntity> findSameTemplateIdShort(Long id);

    void edit(WxmpMessageTemplateDTO templateDTO);
}
