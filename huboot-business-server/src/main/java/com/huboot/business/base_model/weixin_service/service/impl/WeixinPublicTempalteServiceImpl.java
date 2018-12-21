package com.huboot.business.base_model.weixin_service.service.impl;

import com.google.gson.JsonParser;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ZKWeixinMessageDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicTempalteEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicTempalteLogEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinTempalteEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicTempalteLogRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicTempalteRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinTempalteRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicTempalteService;
import com.huboot.business.base_model.weixin_service.config.WeixinConstant;
import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;

import com.huboot.business.base_model.weixin_service.support.WechatMpFactory;
import com.huboot.business.common.component.exception.BizException;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *微信模板信息表ServiceImpl
 */
@Service("weixinPublicTempalteServiceImpl")
public class WeixinPublicTempalteServiceImpl implements IWeixinPublicTempalteService {

    private Logger logger = LoggerFactory.getLogger(WeixinPublicTempalteServiceImpl.class);

    @Autowired
    private IWeixinPublicTempalteRepository weixinPublicTempalteRepository;
    @Autowired
    private IWeixinTempalteRepository weixinTempalteRepository;
    @Autowired
    private WechatMpFactory wechatMpFactory;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private IWeixinPublicTempalteLogRepository tempalteLogRepository;
    @Value("${huboot.domain.zkfront}")
    private String zkFrontDomain;
    @Autowired
    private ThreadPoolTaskExecutor weixinTaskExecutor;

    private static final JsonParser JSON_PARSER = new JsonParser();

    @Transactional
    @Override
    public void initPublicTemplate(String weixinUid, Integer system) {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid为空！");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if(publicEntity == null) {
            throw new BizException("微信配置不存在！");
        }
        List<WeixinTempalteEntity> tempalteList = weixinTempalteRepository.findBySystem(system);
        if(CollectionUtils.isEmpty(tempalteList)) {
            throw new BizException("微信消息模板库为空！");
        }
        this.deletePublicTemplate(publicEntity);
        for(WeixinTempalteEntity tempalteEntity : tempalteList) {
            this.setPublicTempalte(tempalteEntity, publicEntity);
        }
    }

    private void deletePublicTemplate(WeixinPublicEntity publicEntity) {
        WxMpTemplateMsgService templateMsgService = wechatMpFactory.getWXMpService(publicEntity).getTemplateMsgService();
        try {
            List<WxMpTemplate> templateList = templateMsgService.getAllPrivateTemplate();
            if(!CollectionUtils.isEmpty(templateList)) {
                for(WxMpTemplate template : templateList) {
                    templateMsgService.delPrivateTemplate(template.getTemplateId());
                }
            }
        } catch (WxErrorException e) {
            logger.error("删除消息模板异常", e);
            throw new BizException("删除消息模板异常");
        }
    }

    @Override
    public void deletePublicTemplate(String weixinUid) {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信消息模板库为空！");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if(publicEntity == null) {
            throw new BizException("微信配置不存在！");
        }
        this.deletePublicTemplate(publicEntity);
    }

    /**
     *
     * @param tempalteEntity
     * @param publicEntity
     */
    @Override
    public void setPublicTempalte(WeixinTempalteEntity tempalteEntity, WeixinPublicEntity publicEntity) {
        WeixinPublicTempalteEntity publicTempalteEntity = weixinPublicTempalteRepository.findByWeixinUidAndRelaTemplateId(publicEntity.getWeixinUid(), tempalteEntity.getId());
        if(publicTempalteEntity == null) {
            String wxTempalteId = "";
            try {
                WxMpTemplateMsgService templateMsgService = wechatMpFactory.getWXMpService(publicEntity).getTemplateMsgService();
                wxTempalteId = templateMsgService.addTemplate(tempalteEntity.getTemplateIdShort());
            } catch (Exception e) {
                throw new BizException("初始化添加公众号消息模板异常");
            }
            publicTempalteEntity = new WeixinPublicTempalteEntity();
            publicTempalteEntity.setWeixinUid(publicEntity.getWeixinUid());
            publicTempalteEntity.setRelaTemplateId(tempalteEntity.getId());
            publicTempalteEntity.setTemplateId(wxTempalteId);
            weixinPublicTempalteRepository.create(publicTempalteEntity);
            if(!WeixinTempalteEntity.StatusEnum.public_template_save.equals(tempalteEntity.getStatus())) {
                tempalteEntity.setStatus(WeixinTempalteEntity.StatusEnum.public_template_save);
                weixinTempalteRepository.update(tempalteEntity);
            }
        }
    }


    /**
     * 初始化添加公众号消息模板
     * @throws BizException
     */
    @Override
    public void sendZKWeixinMessage(ZKWeixinMessageDTO dto) throws BizException {
        Assert.notNull(dto.getUserGid(), "用户gid不能为空");
        Assert.notNull(dto.getWeixinUid(), "微信uid不能为空");
        Assert.notNull(dto.getNode(), "微信节点不能为空");
        Assert.notEmpty(dto.getKeywordList(), "参数不能为空");

        boolean flag = false;
        String errorReason = "";
        String openId = "";

        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(dto.getWeixinUid());;
        /*XiehuaB2cUserEntity userEntity = b2cUserRepository.findByGid(dto.getUserGid());
        XiehuaB2CShopEntity shopEntity = b2CShopRepository.findOneByWeixinUid(dto.getWeixinUid());
        if(userEntity != null && shopEntity != null) {
            XiehuaB2cUserShopMapEntity userShopMapEntity = b2cUserShopMapRepository.findOneByUserIdAndShopUid(userEntity.getId(), shopEntity.getShopUid());
            if(userShopMapEntity != null) {
                openId = userShopMapEntity.getWeixinOpenid();
            }
        }*/
        if(StringUtils.isEmpty(openId)) {
            errorReason = "openId 沒找到";
        } else {
            flag = true;
        }

        SendWeixinMessageTask runnable = new SendWeixinMessageTask(SystemEnum.zk.getVal(),
                dto.getNode(), openId, publicEntity, dto.getKeywordList());
        runnable.setFrist(dto.getFrist());
        runnable.setRemark(dto.getRemark());
        runnable.setFrontDomain(zkFrontDomain);
        runnable.setUrlParmasList(dto.getUrlParmasList());
        runnable.setFlag(flag);
        runnable.setErrorReason(errorReason);
        weixinTaskExecutor.execute(runnable);

    }

    private class SendWeixinMessageTask implements Runnable {

        private Integer system;
        private String openId;
        private WeixinPublicEntity publicEntity;
        private Integer node;
        private String frist;
        private String remark;
        private List<String> keywordList;
        private List<String> urlParmasList;
        private String frontDomain;
        private boolean flag;
        private String errorReason;

        public SendWeixinMessageTask(Integer system, Integer node, String openId, WeixinPublicEntity publicEntity, List<String> keywordList) {
            this.system = system;
            this.node = node;
            this.openId = openId;
            this.publicEntity = publicEntity;
            this.keywordList = keywordList;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setErrorReason(String errorReason) {
            this.errorReason = errorReason;
        }

        public void setFrist(String frist) {
            this.frist = frist;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setFrontDomain(String frontDomain) {
            this.frontDomain = frontDomain;
        }

        public void setUrlParmasList(List<String> urlParmasList) {
            this.urlParmasList = urlParmasList;
        }

        @Override
        public void run() {
            WeixinPublicTempalteLogEntity logEntity = new WeixinPublicTempalteLogEntity();
            logEntity.setSystem(system);
            logEntity.setOpenId(openId);
            logEntity.setNode(node);
            logEntity.setMessageContent("");
            if(!CollectionUtils.isEmpty(urlParmasList)) {
                logEntity.setRemark(urlParmasList.toString());
            }
            if(publicEntity != null) {
                logEntity.setWeixinUid(publicEntity.getWeixinUid());
            }
            try {
                if(flag) {
                    WeixinTempalteEntity tempalteEntity = weixinTempalteRepository.findByNodeAndSystem(node, system);
                    if(tempalteEntity == null) {
                        throw new BizException("微信模板配置不存在");
                    }
                    WeixinPublicTempalteEntity publicTempalteEntity = weixinPublicTempalteRepository.findByWeixinUidAndRelaTemplateId(publicEntity.getWeixinUid(), tempalteEntity.getId());
                    if(publicTempalteEntity == null) {
                        throw new BizException("公众号微信模板配置不存在");
                    }

                    WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);

                    WxMpTemplateMessage message = WxMpTemplateMessage.builder().build();
                    /*if(!StringUtils.isEmpty(tempalteEntity.getClickUrl())) {
                        message.setUrl(getUrl(frontDomain, urlParmasList, tempalteEntity, wxMpService));
                    }*/

                    //跳转小程序
                    //url和miniprogram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。
                    // 当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
                    WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                    //查询小程序的appid
                    /*XiehuaB2CShopEntity shopEntity = b2CShopRepository.findOneByWeixinUid(publicEntity.getWeixinUid());
                    WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(shopEntity.getMiniappUid());
                    miniProgram.setAppid(publicEntity.getAppId());
                    miniProgram.setPagePath(getMiniAppUrl(frontDomain, urlParmasList, tempalteEntity, wxMpService));
                    message.setMiniProgram(miniProgram);*/

                    message.setTemplateId(publicTempalteEntity.getTemplateId());
                    message.setToUser(openId);
                    List<WxMpTemplateData> dataList = getTemplateData(frist, keywordList, remark);
                    dataList.stream().forEach(data -> {
                        message.addData(data);
                    });

                    logEntity.setTemplateId(publicTempalteEntity.getTemplateId());
                    logEntity.setMessageContent(message.toJson());

                    /*String mesgid = "";
                    String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
                    String responseContent = wxMpService.post(url, message.toJson());
                    JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
                    if(jsonObject.get("errcode").getAsInt() == 0) {
                        mesgid = jsonObject.get("msgid").getAsString();
                    } else {
                        throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
                    }*/

                    String mesgid = wxMpService.getTemplateMsgService().sendTemplateMsg(message);
                    logEntity.setMessageId(mesgid);
                    logEntity.setSendStatus(WeixinPublicTempalteLogEntity.SendStatusEnum.success);
                } else {
                    logEntity.setSendStatus(WeixinPublicTempalteLogEntity.SendStatusEnum.failure);
                    logEntity.setErrorReason(errorReason);
                }
            } catch (WxErrorException e1) {
                logEntity.setSendStatus(WeixinPublicTempalteLogEntity.SendStatusEnum.failure);
                logEntity.setErrorReason(e1.getError().getErrorMsg());
            } catch (Exception e2) {
                logEntity.setSendStatus(WeixinPublicTempalteLogEntity.SendStatusEnum.failure);
                logEntity.setErrorReason(e2.getMessage());
            } finally {
                tempalteLogRepository.create(logEntity);
            }
        }
    }

    /**
     *
     * @param frontDomain
     * @param urlParmas
     * @return
     */
    private String getUrl(String frontDomain, List<String> urlParmas, WeixinTempalteEntity tempalteEntity, WxMpService wxMpService) {
        String url = frontDomain + tempalteEntity.getClickUrl();
        if(!CollectionUtils.isEmpty(urlParmas)) {
            String[] strpramas = urlParmas.toArray(new String[urlParmas.size()]);
            url = MessageFormat.format(url, strpramas);
        }
        if(1 == tempalteEntity.getNeedAuth()) {
            url = wxMpService.oauth2buildAuthorizationUrl(url, WeixinConstant.WEIXIN_SCOPE_USERINFO, WeixinConstant.WEIXIN_STATE);
        }
        return url;
    }

    /**
     *
     * @param frontDomain
     * @param urlParmas
     * @return
     */
    private String getMiniAppUrl(String frontDomain, List<String> urlParmas, WeixinTempalteEntity tempalteEntity, WxMpService wxMpService) {
        String url = tempalteEntity.getClickUrl();
        List<String> newUrlParmas = new ArrayList<String>();
        newUrlParmas.add(frontDomain);
        newUrlParmas.addAll(urlParmas);
        if(!CollectionUtils.isEmpty(newUrlParmas)) {
            //String[] strpramas = newUrlParmas.toArray(new String[newUrlParmas.size()]);
            for(int i = 0;i<newUrlParmas.size();i++){
                url = url.replaceFirst("\\{"+i+"\\}", newUrlParmas.get(i));
            }
            //url = url.replaceFirst("\\{0\\}", newUrlParmas.get(0)).replaceFirst("\\{1\\}", newUrlParmas.get(1)).replaceFirst("\\{2\\}", newUrlParmas.get(2));
            //url = MessageFormat.format(url, strpramas);
        }
        if(1 == tempalteEntity.getNeedAuth()) {
            url = wxMpService.oauth2buildAuthorizationUrl(url, WeixinConstant.WEIXIN_SCOPE_USERINFO, WeixinConstant.WEIXIN_STATE);
        }
        return url;
    }


    /**
     *
     * @param frist
     * @param keywordList
     * @param remark
     * @return
     */
    private List<WxMpTemplateData> getTemplateData(String frist, List<String> keywordList, String remark) {
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


    /*public static void main(String[] args) {
        List<String> newUrlParmas = new ArrayList<String>();
        newUrlParmas.add("123");
        newUrlParmas.add("456");
        newUrlParmas.add("789");
        String url = "pages/index/index?link={\"ordersn\":{2},\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}";
        for(int i = 0;i<newUrlParmas.size();i++){
            url = url.replaceFirst("\\{"+i+"\\}", newUrlParmas.get(i));
        }

        System.out.println(url);
    }*/


}
