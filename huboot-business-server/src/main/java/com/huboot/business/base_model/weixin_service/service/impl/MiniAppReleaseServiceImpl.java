package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.config.WxMpXmlMessageExt;
import com.huboot.business.base_model.weixin_service.dto.ReleaseLogPagerDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinMimiappCodeTemplateEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinMimiappCodeTemplateRepository;
import com.huboot.business.base_model.weixin_service.service.IMiniAppReleaseService;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicSettingService;
import com.huboot.business.common.jpa.QueryCondition;
import com.huboot.business.common.utils.DateUtil;
import com.huboot.business.base_model.weixin_service.config.WeixinConstant;
import com.huboot.business.base_model.weixin_service.dto.CommYesNoEnum;
import com.huboot.business.base_model.weixin_service.dto.ReleaseLogDetailDTO;
import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.entity.WeixinMimiappTemplateMapEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinMimiappTemplateMapRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.jpa.ConditionMap;
import com.huboot.business.common.utils.JsonUtils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Administrator on 2018/11/12 0012.
 */
@Service
public class MiniAppReleaseServiceImpl implements IMiniAppReleaseService {

    private Logger logger = LoggerFactory.getLogger(MiniAppReleaseServiceImpl.class);


    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private IWeixinPublicRepository publicRepository;
    @Autowired
    private IWeixinMimiappTemplateMapRepository templateMapRepository;
    @Autowired
    private IWeixinPublicSettingService weixinPublicSettingService;
    @Autowired
    private IWeixinMimiappCodeTemplateRepository codeTemplateRepository;
    @Value("${config.profile:local}")
    private String env;
    @Value("${xiehua.zkskWeixinUid}")
    private String zkskWeixinUid;

    /**
     * 小程序代码提交
     * @param lastTemplateId
     * @param weixinUid
     */
    @Transactional
    @Override
    public Integer commitCode(Integer lastTemplateId, String weixinUid) {
        if(lastTemplateId == null) {
            throw new BizException("代码模板id不能为空");
        }
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("weixinUid不能为空");
        }
        /*XiehuaB2CShopEntity shopEntity = b2CShopRepository.findOneByMiniappUid(weixinUid);
        if(shopEntity == null) {
            throw new BizException("小程序未绑定店铺");
        }
        if(!XiehuaB2CShopEntity.B2cShopStatusEnum.enable.equals(shopEntity.getStatus())) {
            throw new BizException("绑定店铺不启用状态");
        }*/
        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.findByTemplateId(lastTemplateId);
        if(codeTemplateEntity == null) {
            logger.error("模板不存在,lastTemplateId=" + lastTemplateId);
            throw new BizException("模板不存在");
        }

        //检查小程序各个域名和版本库是否设置成功
        weixinPublicSettingService.checkPublicSetting(weixinUid);

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(weixinUid);

        String extJson = codeTemplateEntity.getExtJson();
        extJson = extJson.replace("${extAppid}", publicEntity.getAppId());
        //extJson = extJson.replace("${shopUid}", shopEntity.getShopUid());
        extJson = extJson.replace("${env}", env);
        WeixinPublicEntity skMiniApp = publicRepository.findByWeixinUid(zkskWeixinUid);
        if(skMiniApp != null) {
            extJson = extJson.replace("${skAppId}", skMiniApp.getAppId());
        }
        Map<String, String> postMap = new HashMap<>();
        postMap.put("template_id", codeTemplateEntity.getTemplateId().toString());
        postMap.put("ext_json", extJson);
        postMap.put("user_version", codeTemplateEntity.getUserVersion());
        postMap.put("user_desc", codeTemplateEntity.getUserDesc());

        String accessToken = null;
        try {
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.COMMIT + accessToken;
            String postData = JsonUtils.toJsonString(postMap);
            logger.info("小程序代码提交参数：" + postData);
            String result = "";

            //记录小程序提交记录
            WeixinMimiappTemplateMapEntity templateMapEntity = new WeixinMimiappTemplateMapEntity();
            templateMapEntity.setWeixinUid(publicEntity.getWeixinUid());
            templateMapEntity.setCodeTempalteId(codeTemplateEntity.getId());
            templateMapEntity.setCommitCodeParameter(postData);

            try{
                result = wxOpenService.post(url, postData);
                logger.info("小程序代码提交结果：" + result);
                templateMapEntity.setCommitCodeResult(result);
                Map<String, String> map = JsonUtils.fromJsonToMap(result);
                if("ok".equals(map.get("errmsg"))) {
                    templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitSuccess);
                } else {
                    templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitFailure);
                }
            }catch (WxErrorException e){
                logger.error("小程序代码提交异常", e);
                templateMapEntity.setCommitCodeResult(e.getMessage());
                templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitFailure);
            }

            templateMapRepository.create(templateMapEntity);
            return templateMapEntity.getId();
        } catch (Exception e) {
            logger.error("小程序代码提交异常", e);
            throw new BizException("小程序代码提交异常");
        }
    }


    /**
     * 小程序提交审核
     */
    @Transactional
    @Override
    public void commitCheck(Integer releaseLogId, Integer releaseAfterAudit) {

        WeixinMimiappTemplateMapEntity templateMapEntity = templateMapRepository.find(releaseLogId);
        if(templateMapEntity == null) {
            throw new BizException("发布记录不存在");
        }

        if(!WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitSuccess.equals(templateMapEntity.getStatus())) {
            throw new BizException("代码不是提交成功状态，不能提交审核");
        }

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(templateMapEntity.getWeixinUid());
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }

        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.find(templateMapEntity.getCodeTempalteId());
        if(codeTemplateEntity == null) {
            throw new BizException("小程序模板代码不存在");
        }

        try {
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.SUBMIT_AUDIT + accessToken;
            logger.info("小程序代码审核参数：" + codeTemplateEntity.getCheckList());
            String result = "";

            try{
                result = wxOpenService.post(url, codeTemplateEntity.getCheckList());
                logger.info("小程序代码审核返回：" + result);
                templateMapEntity.setCommitCheckResult(result);
                Map<String, Object> map = JsonUtils.fromJsonToMap(result);
                if("ok".equals(map.get("errmsg"))) {
                    templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckWait);
                    templateMapEntity.setAuditId(map.get("auditid").toString());
                } else {
                    templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeChecCommitkFailure);
                }
                templateMapEntity.setAuditResult("");
            }catch (WxErrorException e){
                templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeChecCommitkFailure);
                templateMapEntity.setCommitCheckResult(e.getMessage());
                logger.error("代码提交审核异常", e);
            }
            templateMapEntity.setReleaseAfterAudit(releaseAfterAudit);
            templateMapRepository.update(templateMapEntity);
        } catch (Exception e) {
            logger.error("代码提交审核异常", e);
            throw new BizException("代码提交审核异常");
        }

    }


    @Transactional
    @Override
    public void revokeCheck(Integer releaseLogId) throws Exception {
        WeixinMimiappTemplateMapEntity oldLog = templateMapRepository.find(releaseLogId);
        if(oldLog == null) {
            throw new BizException("发布记录不存在");
        }

        if(!WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckWait.equals(oldLog.getStatus())) {
            throw new BizException("代码不是审核中，不能撤销审核");
        }

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(oldLog.getWeixinUid());
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }

        logger.info("准备撤销审核代码：oldLog={}", JsonUtils.toJsonString(oldLog));
        String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(publicEntity.getAppId(), false);
        String url = WeixinConstant.UNDOCODEAUDIT + accessToken;
        wxOpenService.get(url, null);
        oldLog.setReleaseAfterAudit(0);
        oldLog.setAuditId("");
        oldLog.setCommitCheckResult("");
        oldLog.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitSuccess);
        templateMapRepository.update(oldLog);
    }

    @Transactional
    @Override
    public void reCommitCheck(Integer releaseLogId) {
        WeixinMimiappTemplateMapEntity oldLog = templateMapRepository.find(releaseLogId);
        if(oldLog == null) {
            throw new BizException("发布记录不存在");
        }

        if(!WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckFailure.equals(oldLog.getStatus())) {
            throw new BizException("代码不是审核状态，不能重新提交审核");
        }

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(oldLog.getWeixinUid());
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }

        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.find(oldLog.getCodeTempalteId());
        if(codeTemplateEntity == null) {
            throw new BizException("小程序模板代码不存在");
        }

        WeixinMimiappTemplateMapEntity newTemplateMapEntity = new WeixinMimiappTemplateMapEntity();
        BeanUtils.copyProperties(oldLog, newTemplateMapEntity);
        newTemplateMapEntity.setId(null);

        try {
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.SUBMIT_AUDIT + accessToken;
            logger.info("小程序代码审核参数：" + codeTemplateEntity.getCheckList());
            String result = wxOpenService.post(url, codeTemplateEntity.getCheckList());
            logger.info("小程序代码审核返回：" + result);
            newTemplateMapEntity.setCommitCheckResult(result);
            Map<String, Object> map = JsonUtils.fromJsonToMap(result);
            if("ok".equals(map.get("errmsg"))) {
                newTemplateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckWait);
                newTemplateMapEntity.setAuditId(map.get("auditid").toString());
                templateMapRepository.create(newTemplateMapEntity);
            } else {
                throw new BizException("重新提交失败");
            }
        } catch (Exception e) {
            logger.error("代码重新提交审核异常", e);
            throw new BizException("代码提交审核异常");
        }
    }

    /**
     * 审核结果处理
     * @param messageExt
     */
    @Transactional
    @Override
    public void checkResult(WxMpXmlMessageExt messageExt) {
        WeixinPublicEntity publicEntity = publicRepository.findByOriginalId(messageExt.getToUser());
        if(publicEntity == null) {
            logger.error("小程序配置不存在：OriginalId=" + messageExt.getToUser());
            return;
        }
        List<WeixinMimiappTemplateMapEntity> templateMapEntityList = templateMapRepository.findByWeixinUidAndStatusOrderByUpdateTimeDesc(publicEntity.getWeixinUid(),WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckWait);
        WeixinMimiappTemplateMapEntity templateMapEntity = null;
        if(CollectionUtils.isEmpty(templateMapEntityList)) {
            logger.error("小程序模板配置关系不存在：OriginalId=" + messageExt.getToUser());
            return;
        }else{
            templateMapEntity = templateMapEntityList.get(0);
        }

        if(messageExt.getSuccTime() != null) {
            templateMapEntity.setAuditResult("success");
            templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckSuccess);
        } else {
            templateMapEntity.setAuditResult(messageExt.getReason());
            templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckFailure);
        }
        templateMapRepository.update(templateMapEntity);

        //判断是否审核后自动发布
        if(templateMapEntity.getReleaseAfterAudit().equals(CommYesNoEnum.Yes.getValue())) {
            try {
                this.releaseApp(templateMapEntity, publicEntity);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }


    /**
     * 发布小程序
     * @param releaseLogId
     */
    @Transactional
    @Override
    public void release(Integer releaseLogId) {

        WeixinMimiappTemplateMapEntity templateMapEntity = templateMapRepository.find(releaseLogId);
        if(templateMapEntity == null) {
            throw new BizException("发布记录不存在");
        }

        if(!WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckSuccess.equals(templateMapEntity.getStatus())
                && !WeixinMimiappTemplateMapEntity.StatusEnum.codeReleaseFailure.equals(templateMapEntity.getStatus())) {
            throw new BizException("状态不对，不能发布");
        }

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(templateMapEntity.getWeixinUid());
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }

        releaseApp(templateMapEntity, publicEntity);
    }


    /**
     *
     * @param
     */
    @Transactional
    public void releaseApp(WeixinMimiappTemplateMapEntity templateMapEntity, WeixinPublicEntity publicEntity) {
        try {
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.RELEASE + accessToken;
            String postData = JsonUtils.toJsonString(new HashMap<String, String>());
            String result = "";
            try{
                result = wxOpenService.post(url, postData);
                logger.info("小程序发布结果：" + result);
                templateMapEntity.setReleaseResult(result);
                Map<String, String> map = JsonUtils.fromJsonToMap(result);
                if("ok".equals(map.get("errmsg"))) {
                    templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeReleaseSuccess);
                } else {
                    templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeReleaseFailure);
                }
            }catch (WxErrorException e){
                templateMapEntity.setStatus(WeixinMimiappTemplateMapEntity.StatusEnum.codeReleaseFailure);
                logger.error("小程序发布异常", e.getMessage());
                templateMapEntity.setReleaseResult(e.getMessage());
            }
        } catch (Exception e) {
            logger.error("小程序发布异常", e);
            throw new BizException("小程序发布异常");
        }
        templateMapRepository.update(templateMapEntity);
    }


    /**
     * 获取小程序审核结果
     * @return
     */
    @Override
    public String getCheckResult(Integer releaseLogId) {
        WeixinMimiappTemplateMapEntity templateMapEntity = templateMapRepository.find(releaseLogId);
        if(templateMapEntity == null) {
            throw new BizException("发布记录不存在");
        }

        if(StringUtils.isEmpty(templateMapEntity.getAuditId())) {
            throw new BizException("没有审核ID");
        }

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(templateMapEntity.getWeixinUid());
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }

        try {
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.GET_AUDITSTATUS + accessToken;
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("auditid", templateMapEntity.getAuditId());
            String postData = JsonUtils.toJsonString(postMap);
            logger.info("获取小程序审核结果参数：" + postData);
            return wxOpenService.post(url, postData);
        } catch (Exception e) {
            logger.error("获取小程序审核结果异常", e);
            throw new BizException("获取小程序审核结果异常");
        }
    }


    /**
     * 小程序批量更新版本
     * @param lastTemplateId
     */
    @Override
    public void bitchUpdateVersion(Integer lastTemplateId, Integer releaseAfterAudit, String exclude) {
        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.findByTemplateId(lastTemplateId);
        if(codeTemplateEntity == null) {
            throw new BizException("代码版本不存在");
        }
        logger.info("开始小程序批量更新版本!");
        List<WeixinPublicEntity> publicList = publicRepository.findByTypeAndBindType(WeixinPublicEntity.TypeEnum.miniapp, WeixinPublicEntity.BindTypeEnum.weixin3open);
        List<String> tempList = new ArrayList<>();
        if(!StringUtils.isEmpty(exclude)) {
            String[] strs = exclude.split(";");
            tempList = Arrays.asList(strs);
        }
        final List<String> excludeList = tempList;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(WeixinPublicEntity publicEntity : publicList) {
                    if(excludeList.contains(publicEntity.getWeixinUid())) {
                        continue;
                    }
                    try {
                        Integer logId = commitCode(lastTemplateId, publicEntity.getWeixinUid());
                        commitCheck(logId, releaseAfterAudit);
                    } catch (Exception e) {
                        logger.error("小程序更新版本失败，weixinUid=" + publicEntity.getWeixinUid(), e);
                    }
                }
                logger.info("小程序批量更新版本完成！");
            }
        }).start();
    }

    @Override
    public void bitchRelease(Integer templateId) {
        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.findByTemplateId(templateId);
        if(codeTemplateEntity == null) {
            throw new BizException("代码版本不存在");
        }
        List<WeixinMimiappTemplateMapEntity> list = templateMapRepository.findByCodeTempalteIdAndStatus(codeTemplateEntity.getId(), WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckSuccess);
        for(WeixinMimiappTemplateMapEntity mapEntity : list) {
            WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(mapEntity.getWeixinUid());
            if(publicEntity == null) {
                throw new BizException("小程序配置不存在");
            }
            releaseApp(mapEntity, publicEntity);
        }
    }


    @Override
    public Pager<WeixinMimiappPagerDTO> releasePager(String shopUid, String userVersion, Integer page, Integer size) {
       /* XiehuaB2CShopEntity respDTO = null;
        if(!StringUtils.isEmpty(shopUid)) {
            respDTO = b2CShopRepository.findOneByShopUid(shopUid);
        }
        final XiehuaB2CShopEntity shop = respDTO;
        Page<WeixinMimiappTemplateMapEntity> page1 = templateMapRepository.findPage(QueryCondition.from(WeixinMimiappTemplateMapEntity.class).where(list -> {
            if(shop != null) {
                list.add(ConditionMap.eq("weixinUid", shop.getMiniappUid()));
            }
            if(!StringUtils.isEmpty(userVersion)) {
                String parameter = "\"user_version\" : \"" + userVersion + "\"";
                list.add(ConditionMap.like("commitCodeParameter", parameter));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));
        List<WeixinMimiappPagerDTO> dtoList = new ArrayList<>();
        page1.forEach(templateMapEntity -> {
            try {
                WeixinMimiappPagerDTO pagerDTO = new WeixinMimiappPagerDTO();
                if(!StringUtils.isEmpty(templateMapEntity.getCommitCodeParameter())) {
                    pagerDTO.setUserVersion(JsonUtils.fromJsonToMap(templateMapEntity.getCommitCodeParameter()).get("user_version").toString());
                }
                pagerDTO.setCreateTime(templateMapEntity.getCreateTime().format(DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD_HH_MM_SS)));
                pagerDTO.setCheckStatus(templateMapEntity.getStatus().ordinal());
                pagerDTO.setCheckStatusName(templateMapEntity.getStatus().getShowName());
                if(WeixinMimiappTemplateMapEntity.StatusEnum.codeCheckFailure.equals(templateMapEntity.getStatus())) {
                    pagerDTO.setFailureReason(templateMapEntity.getAuditResult());
                }
                if(shop != null) {
                    pagerDTO.setShopName(shop.getName());
                } else {
                    XiehuaB2CShopEntity shopDTO = b2CShopRepository.findOneByMiniappUid(templateMapEntity.getWeixinUid());
                    if(shopDTO != null) {
                        pagerDTO.setShopName(shopDTO.getName());
                    }
                }
                dtoList.add(pagerDTO);
            } catch (Exception e) {
                logger.warn("数据异常", e);
                throw new BizException("数据异常");
            }
        });*/
        return null;
    }


    @Override
    public Pager<ReleaseLogPagerDTO> releaseLogPager(String miniappUid, Integer templateId, Integer status, Integer page, Integer size) {
        Page<WeixinMimiappTemplateMapEntity> ep = templateMapRepository.findPage(QueryCondition.from(WeixinMimiappTemplateMapEntity.class).where(list -> {
            if(!StringUtils.isEmpty(miniappUid)) {
                list.add(ConditionMap.eq("weixinUid", miniappUid));
            }
            if(templateId != null) {
                WeixinMimiappCodeTemplateEntity templateEntity = codeTemplateRepository.findByTemplateId(templateId);
                if(templateEntity != null) {
                    list.add(ConditionMap.eq("codeTempalteId", templateEntity.getId()));
                } else {
                    list.add(ConditionMap.eq("codeTempalteId", 0));
                }
            }
            if(status != null) {
                list.add(ConditionMap.eq("status", WeixinMimiappTemplateMapEntity.StatusEnum.valueOf(status)));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));
        Page<ReleaseLogPagerDTO> dtoPage = ep.map(entity -> {
            ReleaseLogPagerDTO dto = new ReleaseLogPagerDTO();

            dto.setLogId(entity.getId());
            dto.setMiniappUid(entity.getWeixinUid());
            WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(entity.getWeixinUid());
            if(publicEntity != null) {
                dto.setMiniappName(publicEntity.getNickName());
            }
            dto.setCreateTime(entity.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dto.setStatus(entity.getStatus().ordinal());
            dto.setStatusName(entity.getStatus().getShowName());
            WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.find(entity.getCodeTempalteId());
            if(codeTemplateEntity != null) {
                dto.setTemplateId(codeTemplateEntity.getTemplateId());
                dto.setUserVersion(codeTemplateEntity.getUserVersion());
                dto.setUserDesc(codeTemplateEntity.getUserDesc());
            }
            return dto;
        });
        return new Pager<>(dtoPage);
    }

    @Override
    public ReleaseLogDetailDTO releaseDetail(Integer releaseLogId) {
        ReleaseLogDetailDTO detailDTO = new ReleaseLogDetailDTO();

        WeixinMimiappTemplateMapEntity templateMapEntity = templateMapRepository.find(releaseLogId);
        if(templateMapEntity == null) {
            throw new BizException("发布记录不存在");
        }
        detailDTO.setLogId(templateMapEntity.getId());

        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(templateMapEntity.getWeixinUid());
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }
        detailDTO.setMiniappName(publicEntity.getNickName());
        detailDTO.setMiniappUid(publicEntity.getWeixinUid());

        /*XiehuaB2CShopEntity shopEntity = b2CShopRepository.findOneByMiniappUid(publicEntity.getWeixinUid());
        if(shopEntity == null) {
            throw new BizException("小程序未绑定店铺");
        }
        detailDTO.setShopName(shopEntity.getName());*/

        detailDTO.setCreateTime(templateMapEntity.getCreateTime().format(DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD_HH_MM_SS)));
        detailDTO.setStatusName(templateMapEntity.getStatus().getShowName());

        WeixinMimiappCodeTemplateEntity codeTemplateEntity = codeTemplateRepository.find(templateMapEntity.getCodeTempalteId());
        if(codeTemplateEntity == null) {
            throw new BizException("代码版本不存在");
        }
        detailDTO.setTemplateId(codeTemplateEntity.getTemplateId());
        detailDTO.setUserVersion(codeTemplateEntity.getUserVersion());
        detailDTO.setUserDesc(codeTemplateEntity.getUserDesc());
        detailDTO.setCommitCodeParameter(templateMapEntity.getCommitCodeParameter());
        detailDTO.setCommitCodeResult(templateMapEntity.getCommitCodeResult());
        if(!WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitSuccess.equals(templateMapEntity.getStatus())
                && !WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitFailure.equals(templateMapEntity.getStatus())){
            detailDTO.setCommitCheckParameter(codeTemplateEntity.getCheckList());
        }
        detailDTO.setCommitCheckResult(templateMapEntity.getCommitCheckResult());
        detailDTO.setAuditId(templateMapEntity.getAuditId());
        detailDTO.setAuditResult(templateMapEntity.getAuditResult());
        if(templateMapEntity.getReleaseAfterAudit() == 1) {
            detailDTO.setReleaseAfterAudit("是");
        } else if(!WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitSuccess.equals(templateMapEntity.getStatus())
                && !WeixinMimiappTemplateMapEntity.StatusEnum.codeCommitFailure.equals(templateMapEntity.getStatus())){
            detailDTO.setReleaseAfterAudit("否");
        }
        if(WeixinMimiappTemplateMapEntity.StatusEnum.codeReleaseFailure.equals(templateMapEntity.getStatus())
                || WeixinMimiappTemplateMapEntity.StatusEnum.codeReleaseSuccess.equals(templateMapEntity.getStatus())) {
            detailDTO.setReleaseTime(templateMapEntity.getUpdateTime().format(DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD_HH_MM_SS)));
            detailDTO.setReleaseResult(templateMapEntity.getReleaseResult());
        }
        return detailDTO;
    }
}
