package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.common.enums.SuccessOrFailureEnum;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.api.dto.WxmpSendMessageDTO;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.WeixinMessageNodeEnum;
import com.huboot.share.user_service.enums.WeixinTypeEnum;
import com.huboot.user.common.constant.WeixinConstant;
import com.huboot.user.weixin.dto.admin.WxmpMessageLogDetailDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageLogPagerDTO;
import com.huboot.user.weixin.dto.admin.WxmpTemplateRelationPagerDTO;
import com.huboot.user.weixin.entity.*;
import com.huboot.user.weixin.repository.*;
import com.huboot.user.weixin.service.IWxmpMessageTemplateService;
import com.huboot.user.weixin.service.IWxmpTemplateRelationService;
import com.huboot.user.weixin.support.WxServiceFactory;
import lombok.Data;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *公众号ServiceImpl
 */
@Service("wxmpTemplateRelationServiceImpl")
public class WxmpTemplateRelationServiceImpl implements IWxmpTemplateRelationService {

    private Logger logger = LoggerFactory.getLogger(WxmpTemplateRelationServiceImpl.class);

    @Autowired
    private IWxmpTemplateRelationRepository wxmpTemplateRelationRepository;
    @Autowired
    private IWxmpMessageTemplateRepository wxmpMessageTemplateRepository;
    @Autowired
    private IWxmpMessageTemplateService wxmpMessageTemplateService;
    @Autowired
    private WxServiceFactory wxServiceFactory;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private IWxmpRepository wxmpRepository;
    @Autowired
    private IWxmpMessageLogRepository messageLogRepository;
    @Autowired
    private IWeixinShopRelationRepository shopRelationRepository;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private IMiniappUserRepository miniappUserRepository;
    @Autowired
    private IWxmpUserRepository wxmpUserRepository;

    @Override
    public void addTemplateForAllWxmp(Long relaTemplateId) {
        List<WxmpEntity> wxmpEntityList = wxmpRepository.findAll();
        for(WxmpEntity wxmpEntity : wxmpEntityList) {
            taskExecutor.execute(new AddTemplateTask(wxmpEntity.getWxmpappId(), relaTemplateId));
        }
    }

    @Override
    public void addAllTemplateForWxmp(String wxmpappId) {
        List<WxmpMessageTemplateEntity> templateEntityList = wxmpMessageTemplateRepository.findAll();
        for(WxmpMessageTemplateEntity templateEntity : templateEntityList) {
            taskExecutor.execute(new AddTemplateTask(wxmpappId, templateEntity.getId()));
        }
    }

    @Override
    public ShowPageImpl<WxmpTemplateRelationPagerDTO> pager(String node, String wxmpappId, Integer page, Integer size) {
        WxmpMessageTemplateEntity templateEntity = null;
        if(!StringUtils.isEmpty(node)) {
            templateEntity = wxmpMessageTemplateRepository.findByNode(WeixinMessageNodeEnum.valueOf(node));
        }
        final WxmpMessageTemplateEntity qtemplateEntity = templateEntity;
        Page<WxmpTemplateRelationEntity> entityPage = wxmpTemplateRelationRepository.findPage(QueryCondition.from(WxmpTemplateRelationEntity.class)
            .where(list -> {
                if(qtemplateEntity != null) {
                    list.add(ConditionMap.eq("relaTemplateId", qtemplateEntity.getId()));
                }
                if(!StringUtils.isEmpty(wxmpappId)) {
                    list.add(ConditionMap.eq("wxmpappId", wxmpappId));
                }
            }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size)
        );
        Page<WxmpTemplateRelationPagerDTO> dtoPage = entityPage.map(entity -> {
            WxmpTemplateRelationPagerDTO dto = new WxmpTemplateRelationPagerDTO();
            WxmpMessageTemplateEntity template = wxmpMessageTemplateRepository.find(entity.getRelaTemplateId());
            if(template != null) {
                dto.setNode(template.getNode().name());
                dto.setNodeName(template.getNode().getShowName());
            }
            dto.setWxmpappId(entity.getWxmpappId());
            WxmpEntity wxmpEntity = wxmpRepository.findByWxmpappId(entity.getWxmpappId());
            if(wxmpEntity != null) {
                dto.setWxmpappName(wxmpEntity.getNickName());
            }
            dto.setTemplateId(entity.getTemplateId());
            return dto;
        });

        return ShowPageImpl.pager(dtoPage);
    }

    @Override
    public ShowPageImpl<WxmpMessageLogPagerDTO> logPager(String node, String wxmpappId, Integer page, Integer size) {
        Page<WxmpMessageLogEntity> entityPage = messageLogRepository.findPage(QueryCondition.from(WxmpMessageLogEntity.class)
            .where(list -> {
                if(!StringUtils.isEmpty(node)) {
                    list.add(ConditionMap.eq("node", node));
                }
                if(!StringUtils.isEmpty(wxmpappId)) {
                    list.add(ConditionMap.eq("wxmpappId", wxmpappId));
                }
            }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size)
        );
        Page<WxmpMessageLogPagerDTO> dtoPage = entityPage.map(entity -> {
            WxmpMessageLogPagerDTO dto = new WxmpMessageLogPagerDTO();
            dto.setId(entity.getId());
            dto.setWxmpappId(entity.getWxmpappId());
            dto.setCreateTime(entity.getCreateTime());
            dto.setNode(entity.getNode());
            dto.setNodeName(WeixinMessageNodeEnum.valueOf(entity.getNode()).getShowName());
            dto.setOpenId(entity.getOpenId());
            dto.setSendStatusName(entity.getSendStatus().getShowName());
            dto.setRemark(entity.getRemark());
            return dto;
        });
        return ShowPageImpl.pager(dtoPage);
    }

    @Override
    public WxmpMessageLogDetailDTO logDetail(Long logId) {
        WxmpMessageLogDetailDTO detailDTO = new WxmpMessageLogDetailDTO();
        WxmpMessageLogEntity logEntity = messageLogRepository.find(logId);
        BeanUtils.copyProperties(logEntity, detailDTO);
        WxmpEntity wxmpEntity = wxmpRepository.findByWxmpappId(logEntity.getWxmpappId());
        if(wxmpEntity != null) {
            detailDTO.setWxmpappName(wxmpEntity.getNickName());
        }
        detailDTO.setNodeName(WeixinMessageNodeEnum.valueOf(logEntity.getNode()).getShowName());
        detailDTO.setSendStatusName(logEntity.getSendStatus().getShowName());
        return detailDTO;
    }

    class AddTemplateTask implements Runnable {

        private String wxmpappId;
        private Long relaTemplateId;

        public AddTemplateTask(String wxmpappId, Long relaTemplateId) {
            this.wxmpappId = wxmpappId;
            this.relaTemplateId = relaTemplateId;
        }

        @Override
        public void run() {
            addTemplateForWxmp(wxmpappId, relaTemplateId);
        }
    }

    @Transactional
    @Override
    public void addTemplateForWxmp(String wxmpappId, Long relaTemplateId) {
        WxmpTemplateRelationEntity relationEntity = wxmpTemplateRelationRepository.findByWxmpappIdAndRelaTemplateId(wxmpappId, relaTemplateId);
        if(relationEntity != null) {
            logger.error("消息模板已经添加,wxmpappId={},relaTemplateId={}", wxmpappId, relaTemplateId);
            throw new BizException("消息模板已经添加");
        }
        List<WxmpMessageTemplateEntity> templateEntityList = wxmpMessageTemplateService.findSameTemplateIdShort(relaTemplateId);
        if(CollectionUtils.isEmpty(templateEntityList)) {
            logger.error("消息模板不存在,relaTemplateId=" + relaTemplateId);
            throw new BizException("消息模板不存在");
        }
        List<Long> relaIdList = templateEntityList.stream().map(WxmpMessageTemplateEntity::getId).collect(Collectors.toList());
        List<WxmpTemplateRelationEntity> relationEntityList = wxmpTemplateRelationRepository.findByCondition(
                QueryCondition.from(WxmpTemplateRelationEntity.class)
                        .where(ConditionMap.eq("wxmpappId", wxmpappId))
                        .and(ConditionMap.in("relaTemplateId", relaIdList))
        );
        relationEntity = new WxmpTemplateRelationEntity();
        relationEntity.setWxmpappId(wxmpappId);
        relationEntity.setRelaTemplateId(relaTemplateId);
        if(CollectionUtils.isEmpty(relationEntityList)) {
            WxmpMessageTemplateEntity templateEntity = wxmpMessageTemplateRepository.find(relaTemplateId);
            WxMpTemplateMsgService templateMsgService = wxServiceFactory.getWxMpService(wxmpappId).getTemplateMsgService();
            try {
                String templateId = templateMsgService.addTemplate(templateEntity.getTemplateIdShort());
                relationEntity.setTemplateId(templateId);
            } catch (Exception e) {
                logger.error("获取templateId异常，wxmpappId={}，TemplateIdShort={}", wxmpappId, templateEntity.getTemplateIdShort(), e);
                throw new BizException("获取templateId异常");
            }
        } else {
            WxmpTemplateRelationEntity old = relationEntityList.get(0);
            relationEntity.setTemplateId(old.getTemplateId());
        }
        wxmpTemplateRelationRepository.create(relationEntity);
    }

    @Override
    public void sendMessage(WxmpSendMessageDTO messageDTO) {
        taskExecutor.execute(new SendMessageTask(messageDTO));
    }

    class SendMessageTask implements Runnable {

        private WxmpSendMessageDTO messageDTO;

        public SendMessageTask(WxmpSendMessageDTO messageDTO) {
            this.messageDTO = messageDTO;
        }

        @Transactional
        @Override
        public void run() {
            WxmpMessageLogEntity logEntity = new WxmpMessageLogEntity();
            logEntity.setParameter(JsonUtil.buildNormalMapper().toJson(messageDTO));
            logEntity.setRemark(messageDTO.getDesc());
            CheckResult checkResult = check(logEntity, messageDTO);
            if(checkResult.canSend) {
                send(logEntity, checkResult, messageDTO);
            } else {
                logEntity.setSendStatus(SuccessOrFailureEnum.failure);
                logEntity.setErrorReason(checkResult.getErrorReason());
            }
            messageLogRepository.create(logEntity);
        }
    }

    @Data
    static class CheckResult {

        private boolean canSend;
        private WxmpMessageTemplateEntity templateEntity;
        private WxmpTemplateRelationEntity templateRelationEntity;
        private String miniappId;
        private String errorReason;

        public static CheckResult errorResult(String errorReason) {
            CheckResult checkResult = new CheckResult();
            checkResult.setCanSend(false);
            checkResult.setErrorReason(errorReason);
            return checkResult;
        }

        public static CheckResult successResult(WxmpMessageTemplateEntity templateEntity,
                                                WxmpTemplateRelationEntity templateRelationEntity,
                                                String miniappId) {
            CheckResult checkResult = new CheckResult();
            checkResult.setCanSend(true);
            checkResult.setTemplateEntity(templateEntity);
            checkResult.setTemplateRelationEntity(templateRelationEntity);
            checkResult.setMiniappId(miniappId);
            return checkResult;
        }
    }

    private CheckResult check(WxmpMessageLogEntity logEntity, WxmpSendMessageDTO messageDTO) {
        if(messageDTO.getShopId() == null) {
            return CheckResult.errorResult("店铺id为空");
        }
        if(messageDTO.getUserId() == null) {
            return CheckResult.errorResult("用户id为空");
        }
        if(messageDTO.getNodeEnum() == null) {
            return CheckResult.errorResult("消息节点为空");
        }
        if(CollectionUtils.isEmpty(messageDTO.getKeywordList())) {
            return CheckResult.errorResult("消息keywordList为空");
        }
        //检查消息模板
        WxmpMessageTemplateEntity templateEntity = wxmpMessageTemplateRepository.findByNode(messageDTO.getNodeEnum());
        if(templateEntity == null) {
            return CheckResult.errorResult("系统消息模板不存在");
        }
        logEntity.setNode(messageDTO.getNodeEnum().name());
        //检查appid
        WeixinShopRelationEntity relationEntity = shopRelationRepository.findByShopId(messageDTO.getShopId());
        if(relationEntity == null || StringUtils.isEmpty(relationEntity.getWxmpId())) {
            return CheckResult.errorResult("没有找到公众号appid");
        }
        logEntity.setWxmpappId(relationEntity.getWxmpId());
        if (WeixinTypeEnum.miniapp.equals(templateEntity.getOpenType())) {
            if(StringUtils.isEmpty(relationEntity.getMiniappId())) {
                return CheckResult.errorResult("消息模板打开小程序，但是没有找到小程序appid");
            }
        }
        //检查是否添加了该模板
        WxmpTemplateRelationEntity templateRelationEntity = wxmpTemplateRelationRepository.findByWxmpappIdAndRelaTemplateId(relationEntity.getWxmpId(), templateEntity.getId());
        if(templateRelationEntity == null || StringUtils.isEmpty(templateRelationEntity.getTemplateId())) {
            return CheckResult.errorResult("公众号还没有配置该消息模板");
        }

        //获取openid
        UserDetailInfo userDetailInfo = userCacheData.getUserDetailInfo(messageDTO.getUserId());
        if(userDetailInfo == null || StringUtils.isEmpty(userCacheData.getCurrentUserWycThirdOpenId().getValue())) {
            return CheckResult.errorResult("用户没有绑定小程序，无法获取公众号openid");
        }
        MiniappUserEntity miniappUserEntity = miniappUserRepository.findByOpenId(userCacheData.getCurrentUserWycThirdOpenId().getValue());
        if(miniappUserEntity == null || StringUtils.isEmpty(miniappUserEntity.getUnionId())) {
            return CheckResult.errorResult("用户没有unionId，无法获取公众号openid");
        }
        WxmpUserEntity wxmpUserEntity = wxmpUserRepository.findByUnionId(miniappUserEntity.getUnionId());
        if(wxmpUserEntity == null || StringUtils.isEmpty(wxmpUserEntity.getOpenId())) {
            return CheckResult.errorResult("用户没有公众号openid");
        }
        logEntity.setOpenId(wxmpUserEntity.getOpenId());

        return CheckResult.successResult(templateEntity, templateRelationEntity, relationEntity.getMiniappId());
    }


    private void send(WxmpMessageLogEntity logEntity, CheckResult checkResult, WxmpSendMessageDTO messageDTO) {
        try {
            WxmpMessageTemplateEntity templateEntity = checkResult.getTemplateEntity();
            WxmpTemplateRelationEntity templateRelationEntity = checkResult.getTemplateRelationEntity();
            WxMpService wxMpService = wxServiceFactory.getWxMpService(logEntity.getWxmpappId());

            WxMpTemplateMessage message = WxMpTemplateMessage.builder().build();
            message.setTemplateId(templateRelationEntity.getTemplateId());
            message.setToUser(logEntity.getOpenId());
            message.setUrl(this.getUrl(templateEntity, messageDTO.getUrlParmas(), wxMpService));
            message.setData(this.getData(messageDTO.getFrist(), messageDTO.getKeywordList(), messageDTO.getRemark()));
            if(WeixinTypeEnum.miniapp.equals(templateEntity.getOpenType())) {
                WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                miniProgram.setAppid(checkResult.getMiniappId());
                miniProgram.setPagePath(this.getPagePath(templateEntity, messageDTO.getUrlParmas()));
                message.setMiniProgram(miniProgram);
            }
            logEntity.setContent(message.toJson());

            String messageId = wxMpService.getTemplateMsgService().sendTemplateMsg(message);
            logEntity.setResponse(messageId);
            logEntity.setSendStatus(SuccessOrFailureEnum.success);
        } catch (Exception e) {
            logger.error("", e);
            logEntity.setSendStatus(SuccessOrFailureEnum.failure);
            logEntity.setErrorReason(e.getMessage());
        }
    }

    private String getUrl(WxmpMessageTemplateEntity templateEntity, Map<String, String> urlParmas, WxMpService wxMpService) {
        String url = templateEntity.getUrl();
        if(StringUtils.isEmpty(url)) {
            return "http://weixin.qq.com/download";
        }
        if(!CollectionUtils.isEmpty(urlParmas)) {
            Set<Map.Entry<String, String>> entrySet = urlParmas.entrySet();
            for(Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                url = url.replace("{" + key + "}", value);
            }
        }
        if(YesOrNoEnum.yes.equals(templateEntity.getUrlNeedAuth())) {
            url = wxMpService.oauth2buildAuthorizationUrl(url, WeixinConstant.WEIXIN_SCOPE_USERINFO, WeixinConstant.WEIXIN_STATE);
        }
        return url;
    }

    private String getPagePath(WxmpMessageTemplateEntity templateEntity, Map<String, String> urlParmas) {
        String pagepath = templateEntity.getMiniPagepath();
        if(!StringUtils.isEmpty(pagepath)) {
            if(!CollectionUtils.isEmpty(urlParmas)) {
                Set<Map.Entry<String, String>> entrySet = urlParmas.entrySet();
                for(Map.Entry<String, String> entry : entrySet) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    pagepath = pagepath.replace("{" + key + "}", value);
                }
            }
        }
        return pagepath;
    }

    private List<WxMpTemplateData> getData(String frist, List<String> keywordList, String remark) {
        if(CollectionUtils.isEmpty(keywordList)) {
            throw new BizException("微信通知内容为空");
        }
        List<WxMpTemplateData> tempaltelist = new ArrayList<>();
        if(!StringUtils.isEmpty(frist)) {
            WxMpTemplateData templateData = new WxMpTemplateData(WeixinConstant.FRIST, frist);
            templateData.setColor(WeixinConstant.COLOR_FRIST);
            tempaltelist.add(templateData);
        }
        for(int i = 0; i < keywordList.size(); i++) {
            WxMpTemplateData templateData = new WxMpTemplateData(WeixinConstant.KEYWORD(i), keywordList.get(i));
            templateData.setColor(WeixinConstant.COLOR_KEYWORD);
            tempaltelist.add(templateData);
        }
        if(!StringUtils.isEmpty(remark)) {
            WxMpTemplateData templateData = new WxMpTemplateData(WeixinConstant.REMARK, remark);
            templateData.setColor(WeixinConstant.COLOR_REMARK);
            tempaltelist.add(templateData);
        }
        return tempaltelist;
    }
}
