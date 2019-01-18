package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.user.weixin.dto.admin.AuditResultDTO;
import com.huboot.user.weixin.dto.admin.MiniappReleaseLogDetail;
import com.huboot.user.weixin.dto.admin.MiniappReleaseLogPagerDTO;
import com.huboot.user.weixin.entity.MiniappReleaseLogEntity;

import java.util.List;

/**
 *小程序发布记录Service
 */
public interface IMiniappReleaseLogService {

    ShowPageImpl<MiniappReleaseLogPagerDTO> getPager(Integer page, Integer size,
                                                     String miniappId, String templateId);


    MiniappReleaseLogEntity commitCode(String miniappId, String templateId);

    MiniappReleaseLogEntity commitCheck(Long releaseLogId, YesOrNoEnum releaseAfterAudit, String checkList);

    void undoCodeAudit(String appId) throws Exception;

    AuditResultDTO getAuditResult(String appId) throws Exception;

    AuditResultDTO getAuditResultWithAuditId(String auditId) throws Exception;

    void handleAuditResult(String appId);

    MiniappReleaseLogEntity release(Long releaseLogId);

    List<String> bitchCommit(String templateId, YesOrNoEnum releaseAfterAudit, String checkList);

    List<String> bitchRelease(String templateId);

    MiniappReleaseLogDetail getDetail(Long logId);
}
