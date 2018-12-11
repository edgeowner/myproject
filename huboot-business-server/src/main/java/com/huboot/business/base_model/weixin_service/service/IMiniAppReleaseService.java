package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.config.WxMpXmlMessageExt;
import com.huboot.business.base_model.weixin_service.dto.ReleaseLogDetailDTO;
import com.huboot.business.base_model.weixin_service.dto.ReleaseLogPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
public interface IMiniAppReleaseService {

    /**
     * 小程序代码提交
     * @param lastTemplateId
     * @param weixinUid
     */
    Integer commitCode(Integer lastTemplateId, String weixinUid);


    /**
     * 小程序提交审核
     */
    void commitCheck(Integer releaseLogId,Integer releaseAfterAudit);

    /**
     *
     * @param releaseLogId
     * @throws Exception
     */
    void revokeCheck(Integer releaseLogId) throws Exception;

    /**
     *
     * @param releaseLogId
     */
    void reCommitCheck(Integer releaseLogId);

    /**
     * 审核结果处理
     * @param messageExt
     */
    void checkResult(WxMpXmlMessageExt messageExt);


    /**
     * 发布小程序
     * @param releaseLogId
     */
    void release(Integer releaseLogId);

    /**
     * 获取小程序审核结果
     * @param releaseLogId
     * @return
     */
    String getCheckResult(Integer releaseLogId);


    /**
     *
     * @param lastTemplateId
     */
    void bitchUpdateVersion(Integer lastTemplateId, Integer releaseAfterAudit, String exclude);

    /**
     *
     */
    void bitchRelease(Integer templateId);

    /**
     *
     * @param shopUid
     * @param userVersion
     * @return
     */
    Pager<WeixinMimiappPagerDTO> releasePager(String shopUid, String userVersion, Integer page, Integer size);


    Pager<ReleaseLogPagerDTO> releaseLogPager(String miniappUid, Integer templateId, Integer status, Integer page, Integer size);


    ReleaseLogDetailDTO releaseDetail(Integer releaseLogId);
}
