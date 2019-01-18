package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.enums.MiniappReleaseStatusEnum;
import com.huboot.share.user_service.enums.WeixinAuthStatusEnum;
import com.huboot.user.common.config.RefreshValue;
import com.huboot.user.common.constant.WeixinConstant;
import com.huboot.user.weixin.dto.admin.AuditResultDTO;
import com.huboot.user.weixin.dto.admin.ExtJson;
import com.huboot.user.weixin.dto.admin.MiniappReleaseLogDetail;
import com.huboot.user.weixin.dto.admin.MiniappReleaseLogPagerDTO;
import com.huboot.user.weixin.entity.MiniappCodeTemplateEntity;
import com.huboot.user.weixin.entity.MiniappEntity;
import com.huboot.user.weixin.entity.MiniappReleaseLogEntity;
import com.huboot.user.weixin.entity.WeixinShopRelationEntity;
import com.huboot.user.weixin.repository.IMiniappCodeTemplateRepository;
import com.huboot.user.weixin.repository.IMiniappReleaseLogRepository;
import com.huboot.user.weixin.repository.IMiniappRepository;
import com.huboot.user.weixin.repository.IWeixinShopRelationRepository;
import com.huboot.user.weixin.service.IMiniappConfigService;
import com.huboot.user.weixin.service.IMiniappReleaseLogService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *小程序发布记录ServiceImpl
 */
@Service("mimiappReleaseLogServiceImpl")
public class MiniappReleaseLogServiceImpl implements IMiniappReleaseLogService {

    private Logger logger = LoggerFactory.getLogger(MiniappReleaseLogServiceImpl.class);

    @Autowired
    private IMiniappReleaseLogRepository releaseLogRepository;
    @Autowired
    private IMiniappConfigService configService;
    @Autowired
    private IMiniappRepository miniappRepository;
    @Autowired
    private IMiniappCodeTemplateRepository codeTemplateRepository;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private RefreshValue refreshValue;
    @Autowired
    private IWeixinShopRelationRepository weixinShopRelationRepository;

    @Override
    public ShowPageImpl<MiniappReleaseLogPagerDTO> getPager(Integer page, Integer size,
                                                            String miniappId, String templateId) {
        Page<MiniappReleaseLogEntity> pager = releaseLogRepository.findPage(QueryCondition.from(MiniappReleaseLogEntity.class).where(list -> {
            if(!StringUtils.isEmpty(miniappId)) {
                list.add(ConditionMap.eq("miniappId", miniappId));
            }
            if(!StringUtils.isEmpty(templateId)) {
                list.add(ConditionMap.eq("templateId", templateId));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));
        Page<MiniappReleaseLogPagerDTO> pagerDTO = pager.map(entity -> {
            MiniappReleaseLogPagerDTO dto = new MiniappReleaseLogPagerDTO();
            dto.setLogId(entity.getId());
            dto.setCreateTime(entity.getCreateTime());
            MiniappEntity miniapp = miniappRepository.findByMiniappId(entity.getMiniappId());
            dto.setMiniappId(miniapp.getMiniappId());
            dto.setMiniappName(miniapp.getNickName());
            MiniappCodeTemplateEntity codeTemplate = codeTemplateRepository.findByTemplateId(entity.getTemplateId());
            dto.setUserVersion(codeTemplate.getUserVersion());
            dto.setStatus(entity.getStatus().name());
            dto.setStatusName(entity.getStatus().getShowName());
            dto.setTemplateId(entity.getTemplateId());
            dto.setAuditId(entity.getAuditId());
            return dto;
        });
        return new ShowPageImpl(pagerDTO);
    }

    /**
     * 提交代码
     * @param
     * @return
     */
    @Transactional
    @Override
    public MiniappReleaseLogEntity commitCode(String miniappId, String templateId) {
        MiniappEntity miniappEntity = miniappRepository.findByMiniappId(miniappId);
        AppAssert.notNull(miniappEntity, "小程序不存在");
        if(!WeixinAuthStatusEnum.authorized.equals(miniappEntity.getStatus())) {
            throw new BizException("小程序未授权");
        }
        MiniappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.findByTemplateId(templateId);
        AppAssert.notNull(codeTemplateEntity, "小程序代码模板不存在");

        configService.checkConfig(miniappId);

        WeixinShopRelationEntity relationEntity = weixinShopRelationRepository.findByMiniappId(miniappId);
        AppAssert.notNull(relationEntity, "小程序还未绑定店铺");
        if(StringUtils.isEmpty(relationEntity.getShopSn())) {
            throw new BizException("小程序还未绑定店铺");
        }
        ExtJson extJson = new ExtJson();
        extJson.setExtAppid(miniappId);
        extJson.setExt(new ExtJson.Ext(relationEntity.getShopSn(), refreshValue.getRequestdomain()));

        Map<String, String> postMap = new HashMap<>();
        postMap.put("template_id", codeTemplateEntity.getTemplateId());
        postMap.put("ext_json", extJson.toJson());
        postMap.put("user_version", codeTemplateEntity.getUserVersion());
        postMap.put("user_desc", codeTemplateEntity.getUserDesc());
        String postData = JsonUtil.buildNormalMapper().toJson(postMap);

        MiniappReleaseLogEntity logEntity = new MiniappReleaseLogEntity();
        logEntity.setMiniappId(miniappEntity.getMiniappId());
        logEntity.setTemplateId(codeTemplateEntity.getTemplateId());
        logEntity.setCommitCodeParameter(postData);

        try {
            String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(miniappEntity.getMiniappId(), false);
            String url = WeixinConstant.COMMIT + accessToken;
            logger.info("小程序代码提交参数:url={}, data={}", url, postData);

            String result = wxOpenService.post(url, postData);
            logger.info("小程序代码提交结果：" + result);
            logEntity.setCommitCodeResult(result);

            Map<String, String> map = JsonUtil.buildNonDefaultMapper().fromJson(result, Map.class);
            if("ok".equals(map.get("errmsg"))) {
                logEntity.setStatus(MiniappReleaseStatusEnum.code_commit_success);
            } else {
                logEntity.setStatus(MiniappReleaseStatusEnum.code_commit_failure);
            }
        } catch (Exception e) {
            logger.warn("小程序代码提交异常", e);
            logEntity.setCommitCodeResult(e.getMessage());
            logEntity.setStatus(MiniappReleaseStatusEnum.code_commit_failure);
        }
        releaseLogRepository.create(logEntity);
        return logEntity;
    }


    /**
     * 提交审核
     * @param releaseLogId
     * @param releaseAfterAudit
     * @return
     */
    @Transactional
    @Override
    public MiniappReleaseLogEntity commitCheck(Long releaseLogId, YesOrNoEnum releaseAfterAudit, String checkList) {
        MiniappReleaseLogEntity logEntity = releaseLogRepository.find(releaseLogId);
        AppAssert.notNull(logEntity, "发布记录不存在");
        if(!MiniappReleaseStatusEnum.code_commit_success.equals(logEntity.getStatus())
                && !MiniappReleaseStatusEnum.check_commit_failure.equals(logEntity.getStatus())) {
            throw new BizException("【代码提交成功】或者【审核提交失败】状态才能提交代码审核");
        }

        List<MiniappReleaseLogEntity> logEntityList = releaseLogRepository.findByMiniappIdAndStatus(logEntity.getMiniappId(), MiniappReleaseStatusEnum.check_commit_success);
        if(!CollectionUtils.isEmpty(logEntityList)) {
            logger.warn("小程序有正在审核中的代码，不能再次提交审核, MiniappId=" + logEntity.getMiniappId());
            throw new BizException("小程序有正在审核中的代码，不能再次提交审核");
        }

        MiniappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.findByTemplateId(logEntity.getTemplateId());
        AppAssert.notNull(codeTemplateEntity, "小程序代码模板不存在");

        String postData = checkList;

        try {
            String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(logEntity.getMiniappId(), false);
            String url = WeixinConstant.SUBMIT_AUDIT + accessToken;
            logger.info("小程序提交代码审核参数:url={}, data={}", url, postData);

            String result = wxOpenService.post(url, postData);
            logger.info("小程序提交代码审核结果：" + result);
            logEntity.setCommitAuditResult(result);

            Map<String, Object> map = JsonUtil.buildNonDefaultMapper().fromJson(result, HashMap.class);
            if("ok".equals(map.get("errmsg").toString())) {
                logEntity.setStatus(MiniappReleaseStatusEnum.check_commit_success);
                logEntity.setAuditId(map.get("auditid").toString());
            } else {
                logEntity.setStatus(MiniappReleaseStatusEnum.check_commit_failure);
            }
        } catch (Exception e) {
            logger.warn("小程序提交代码审核异常", e);
            logEntity.setCommitAuditResult(e.getMessage());
            logEntity.setStatus(MiniappReleaseStatusEnum.check_commit_failure);
        }
        logEntity.setReleaseAfterAudit(releaseAfterAudit.name());
        releaseLogRepository.modify(logEntity);
        return logEntity;
    }

    @Transactional
    @Override
    public void undoCodeAudit(String appId)throws Exception {
        List<MiniappReleaseLogEntity> logEntityList = releaseLogRepository.findByMiniappIdAndStatus(appId, MiniappReleaseStatusEnum.check_commit_success);
        if(CollectionUtils.isEmpty(logEntityList)) {
            throw new BizException("没有审核中的代码");
        }
        MiniappReleaseLogEntity logEntity = logEntityList.get(0);
        logger.info("准备撤销审核代码：logEntity={}", JsonUtil.buildNormalMapper().toJson(logEntity));
        String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appId, false);
        String url = WeixinConstant.UNDOCODEAUDIT + accessToken;
        wxOpenService.get(url, null);
        logEntity.setReleaseAfterAudit("");
        logEntity.setAuditId("");
        logEntity.setCommitAuditResult("");
        logEntity.setStatus(MiniappReleaseStatusEnum.code_commit_success);
        releaseLogRepository.modify(logEntity);
    }

    @Override
    public AuditResultDTO getAuditResult(String appId) throws Exception {
        String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appId, false);
        String url = WeixinConstant.GET_LAST_AUDITSTATUS + accessToken;
        String result = wxOpenService.get(url, null);
        return JsonUtil.buildNormalMapper().fromJson(result, AuditResultDTO.class);
    }

    @Override
    public AuditResultDTO getAuditResultWithAuditId(String auditId) throws Exception {
        MiniappReleaseLogEntity logEntity = releaseLogRepository.findByAuditId(auditId);
        AppAssert.notNull(logEntity, "发布记录不存在");
        String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(logEntity.getMiniappId(), false);
        String url = WeixinConstant.GET_AUDITSTATUS + accessToken;
        Map<String, String> map = new HashMap<>();
        map.put("auditid", auditId);
        String postData = JsonUtil.buildNormalMapper().toJson(map);
        String result = wxOpenService.post(url, postData);
        return JsonUtil.buildNormalMapper().fromJson(result, AuditResultDTO.class);
    }

    /**
     * 审核结果处理
     * @param appId
     */
    @Transactional
    @Override
    public void handleAuditResult(String appId) {
        AuditResultDTO resultDTO;
        try {
            resultDTO = this.getAuditResult(appId);
        } catch (Exception e) {
            logger.error("审核结果处理-获取小程序最新审核结果异常", e);
            throw new BizException("获取小程序最新审核结果异常");
        }
        MiniappReleaseLogEntity logEntity = releaseLogRepository.findByMiniappIdAndAuditId(appId, resultDTO.getAuditid());
        AppAssert.notNull(logEntity, "发布记录不存在");

        if("0".equals(resultDTO.getStatus())) {
            logEntity.setStatus(MiniappReleaseStatusEnum.auth_success);
            logEntity.setAuditResult("审核通过");
        } else if("1".equals(resultDTO.getStatus())) {
            logEntity.setStatus(MiniappReleaseStatusEnum.auth_failure);
            logEntity.setAuditResult(resultDTO.getReason());
        } else {
            throw new BizException("没有审核成功");
        }
        releaseLogRepository.modify(logEntity);
        if(YesOrNoEnum.yes.name().equals(logEntity.getReleaseAfterAudit())) {
            try {
                this.release(logEntity.getId());
            } catch (Exception e) {
                logger.error("小程序发布异常", e);
            }
        }
    }

    /**
     * 发布
     * @param releaseLogId
     * @return
     */
    @Transactional
    @Override
    public MiniappReleaseLogEntity release(Long releaseLogId) {
        MiniappReleaseLogEntity logEntity = releaseLogRepository.find(releaseLogId);
        AppAssert.notNull(logEntity, "发布记录不存在");
        if(!MiniappReleaseStatusEnum.auth_success.equals(logEntity.getStatus())
                && !MiniappReleaseStatusEnum.release_failure.equals(logEntity.getStatus())) {
            throw new BizException("【审核通过】或者【发布失败】状态才能发布");
        }

        MiniappEntity miniappEntity = miniappRepository.findByMiniappId(logEntity.getMiniappId());
        AppAssert.notNull(miniappEntity, "小程序不存在");

        String postData = "{}";

        try {
            String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(logEntity.getMiniappId(), false);
            String url = WeixinConstant.RELEASE + accessToken;
            logger.info("小程序发布参数:url={}, data={}", url, postData);

            String result = wxOpenService.post(url, postData);
            logger.info("小程序发布结果：" + result);
            logEntity.setReleaseResult(result);

            Map<String, Object> map = JsonUtil.buildNonDefaultMapper().fromJson(result, Map.class);
            if("ok".equals(map.get("errmsg").toString())) {
                logEntity.setStatus(MiniappReleaseStatusEnum.release_success);
            } else {
                logEntity.setStatus(MiniappReleaseStatusEnum.release_failure);
            }
        } catch (Exception e) {
            logger.warn("小程序发布异常", e);
            logEntity.setReleaseResult(e.getMessage());
            logEntity.setStatus(MiniappReleaseStatusEnum.release_failure);
        }
        releaseLogRepository.modify(logEntity);

        if(YesOrNoEnum.no.equals(miniappEntity.getHasRelease())) {
            miniappEntity.setHasRelease(YesOrNoEnum.yes);
            miniappRepository.modify(miniappEntity);
        }

        return logEntity;
    }

    @Override
    public List<String> bitchCommit(String templateId, YesOrNoEnum releaseAfterAudit, String checkList) {
        List<String> flist = new ArrayList<>();
        List<MiniappEntity> list = miniappRepository.findByCanBitchRelease(YesOrNoEnum.yes);
        if(!CollectionUtils.isEmpty(list)) {
            for(MiniappEntity miniappEntity : list) {
                try {
                    MiniappReleaseLogEntity logEntity = commitCode(miniappEntity.getMiniappId(), templateId);
                    commitCheck(logEntity.getId(), releaseAfterAudit, checkList);
                } catch (Exception e) {
                    logger.error("小程序批量提交异常：appid={}", miniappEntity.getMiniappId(), e);
                    flist.add(miniappEntity.getMiniappId());
                }
            }
        }
        return flist;
    }

    @Override
    public List<String> bitchRelease(String templateId) {
        List<String> flist = new ArrayList<>();
        List<MiniappReleaseLogEntity> list = releaseLogRepository.findByTemplateIdAndStatus(templateId, MiniappReleaseStatusEnum.auth_success);
        if(!CollectionUtils.isEmpty(list)) {
            for(MiniappReleaseLogEntity logEntity : list) {
                try {
                    this.release(logEntity.getId());
                } catch (Exception e) {
                    logger.error("小程序批量发布异常：appid={}", logEntity.getMiniappId(), e);
                    flist.add(logEntity.getMiniappId());
                }
            }
        }
        return flist;
    }

    @Override
    public MiniappReleaseLogDetail getDetail(Long logId) {
        MiniappReleaseLogDetail detail = new MiniappReleaseLogDetail();

        MiniappReleaseLogEntity logEntity = releaseLogRepository.find(logId);
        detail.setId(logEntity.getId());
        detail.setCreateTime(logEntity.getCreateTime());
        detail.setMiniappId(logEntity.getMiniappId());
        MiniappEntity miniappEntity = miniappRepository.findByMiniappId(logEntity.getMiniappId());
        detail.setMiniappName(miniappEntity.getNickName());
        detail.setTemplateId(logEntity.getTemplateId());
        MiniappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.findByTemplateId(logEntity.getTemplateId());
        detail.setUserVersion(codeTemplateEntity.getUserVersion());
        detail.setUserDesc(codeTemplateEntity.getUserDesc());
        detail.setStatus(logEntity.getStatus().name());
        detail.setStatusName(logEntity.getStatus().getShowName());
        detail.setCommitCodeParameter(logEntity.getCommitCodeParameter());
        detail.setCommitCodeResult(logEntity.getCommitCodeResult());
        detail.setCommitAuditResult(logEntity.getCommitAuditResult());
        detail.setAuditId(logEntity.getAuditId());
        detail.setAuditResult(logEntity.getAuditResult());
        detail.setReleaseAfterAudit(logEntity.getReleaseAfterAudit());
        if(!StringUtils.isEmpty(logEntity.getReleaseAfterAudit())) {
            detail.setReleaseAfterAuditName(YesOrNoEnum.valueOf(logEntity.getReleaseAfterAudit()).getShowName());
        } else {
            detail.setReleaseAfterAuditName("");
        }
        detail.setReleaseResult(logEntity.getReleaseResult());
        return detail;
    }
}
