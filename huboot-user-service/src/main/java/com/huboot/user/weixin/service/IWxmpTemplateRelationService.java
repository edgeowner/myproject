package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.WxmpSendMessageDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageLogDetailDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageLogPagerDTO;
import com.huboot.user.weixin.dto.admin.WxmpTemplateRelationPagerDTO;

/**
 *公众号Service
 */
public interface IWxmpTemplateRelationService {

    void addTemplateForAllWxmp(Long relaTemplateId);

    void addAllTemplateForWxmp(String wxmpappId);

    void addTemplateForWxmp(String wxmpappId, Long relaTemplateId);

    void sendMessage(WxmpSendMessageDTO messageDTO);

    ShowPageImpl<WxmpTemplateRelationPagerDTO> pager(String node, String wxmpappId, Integer page, Integer size);

    ShowPageImpl<WxmpMessageLogPagerDTO> logPager(String node, String wxmpappId, Integer page, Integer size);

    WxmpMessageLogDetailDTO logDetail(Long logId);

}
