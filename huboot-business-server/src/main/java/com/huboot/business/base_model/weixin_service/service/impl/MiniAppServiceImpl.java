package com.huboot.business.base_model.weixin_service.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.QrCodeDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinMimiappTemplateMapRepository;
import com.huboot.business.base_model.weixin_service.service.IMiniAppService;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicSettingService;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserPublicMapService;
import com.huboot.business.base_model.weixin_service.support.MiniAppFactory;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.base_model.weixin_service.config.WeixinConstant;
import com.huboot.business.base_model.weixin_service.dao.IWeixinPublicDao;
import com.huboot.business.base_model.weixin_service.dto.MiniappPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.entity.WeixinMimiappTemplateMapEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicSettingEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserPublicMapEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserPublicMapRepository;
import com.huboot.business.common.component.exception.BizException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2018/4/7 0007.
 */
@Service
public class MiniAppServiceImpl implements IMiniAppService {

    private Logger logger = LoggerFactory.getLogger(MiniAppServiceImpl.class);

    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private IWeixinPublicRepository publicRepository;
    @Autowired
    private IWeixinMimiappTemplateMapRepository templateMapRepository;
    @Value("${xiehua.domain.zkfront}")
    private String zkfront;
    @Value("${xiehua.domain.thdcFront}")
    private String thdcFront;
    @Value("${xiehua.domain.zkapi}")
    private String zkapi;
    @Value("${config.profile:local}")
    private String env;
    @Value("${xiehua.zkskWeixinUid}")
    private String zkskWeixinUid;
    @Autowired
    private MiniAppFactory miniAppFactory;
    @Autowired
    private IWeixinUserPublicMapService weixinUserPublicMapService;
    @Autowired
    private IWeixinPublicSettingService weixinPublicSettingService;
    @Autowired
    private IWeixinPublicDao weixinPublicDao;
    @Autowired
    private IWeixinUserPublicMapRepository weixinUserPublicMapRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    @Override
    public void youhua() {
        List<WeixinPublicEntity> list = publicRepository.findByTypeAndBindType(WeixinPublicEntity.TypeEnum.miniapp, WeixinPublicEntity.BindTypeEnum.weixin3open);
        for(WeixinPublicEntity publicEntity : list) {
            publicEntity.setHeadImg("");
            String refreshToken = wxOpenService.getWxOpenConfigStorage().getAuthorizerRefreshToken(publicEntity.getAppId());
            publicEntity.setRefreshToken(refreshToken);
            publicRepository.update(publicEntity);
        }
    }

    /**
     * @param weixinUid
     * @param wxcode
     * @return
     */
    @Override
    public WeixinAuthDTO getMiniAppOpenId(String weixinUid, String wxcode) throws Exception {
        if (StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        if (StringUtils.isEmpty(wxcode)) {
            throw new BizException("微信code不能为空");
        }
        WeixinPublicEntity configEntity = publicRepository.findByWeixinUid(weixinUid);
        if (configEntity == null) {
            throw new BizException("微信uid:" + weixinUid + "配置不存在");
        }
        WeixinAuthDTO authDTO = new WeixinAuthDTO();
        try {
            if (WeixinPublicEntity.BindTypeEnum.developer.equals(configEntity.getBindType())) {
                WxMaService wxMaService = miniAppFactory.getMaService(configEntity);
                String url = WeixinConstant.JSCODE_2_SESSION.replace("${appid}", configEntity.getAppId());
                url = url.replace("${secret}", configEntity.getSecret());
                url = url.replace("${js_code}", wxcode);
                logger.info("小程序获取openid url：" + url);
                String result = wxMaService.get(url, null);
                logger.info("result值{}", result);
                Map<String, String> map = JsonUtils.fromJsonToMap(result);
                if (StringUtils.isEmpty(map.get("openid"))) {
                    throw new BizException("获取微信openid为空");
                }
                logger.info("获取sessionkey值{}", map.toString());
                authDTO.setOpenId(map.get("openid"));
                authDTO.setUnionid(map.get("unionid") == null ? "" : map.get("unionid"));
                authDTO.setSessionKey(map.get("session_key") == null ? "" : map.get("session_key"));
            } else if (WeixinPublicEntity.BindTypeEnum.weixin3open.equals(configEntity.getBindType())) {
                if (WeixinPublicEntity.StatusEnum.unAuthorized.equals(configEntity.getStatus())) {
                    throw new BizException("微信uid:" + weixinUid + "公众号已经取消授权给开放平台");
                }
                WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
                WxMaJscode2SessionResult accessToken = componentService.miniappJscode2Session(configEntity.getAppId(), wxcode);
                authDTO.setOpenId(accessToken.getOpenid());
                authDTO.setUnionid(accessToken.getUnionid());
                authDTO.setSessionKey(accessToken.getSessionKey());
            } else {
                throw new BizException("获取微信openid异常");
            }
            logger.info("获取openid结果：" + authDTO.toString());
            try {
                weixinUserPublicMapService.createPublicMap(authDTO, weixinUid);
            } catch (Exception e) {
                logger.error("创建createPublicMap异常", e);
            }
            return authDTO;
        } catch (Exception e) {
            logger.error("获取微信openid异常,wxcode=" + wxcode, e);
            if (e instanceof WxErrorException) {
                throw new BizException(BizException.RETRY, "获取微信openid异常");
            }
            throw e;
        }
    }

    /**
     * 设置小程序域名配置
     * @return
     */
    @Transactional
    @Override
    public void commitDomain(String weixinUid) {
        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(weixinUid);
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }
        commitDomain(publicEntity);
    }

    private void commitDomain(WeixinPublicEntity publicEntity) {
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(publicEntity.getType())) {
            logger.error("不是小程序无需设置域名,weixinUid=" + publicEntity.getWeixinUid());
            throw new BizException("不是小程序无需设置域名");
        }
        try {

            //设置服务器域名
            Map<String, Object> domainPostMap = new HashMap<>();
            domainPostMap.put("action", "set");
            domainPostMap.put("requestdomain", Arrays.asList(zkapi, "https://wxapi.growingio.com"));
            domainPostMap.put("wsrequestdomain", Arrays.asList(zkapi));
            domainPostMap.put("uploaddomain", Arrays.asList(zkapi));
            domainPostMap.put("downloaddomain", Arrays.asList(zkapi));
            String domainPostData = JsonUtils.toJsonString(domainPostMap);

            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.MODIFY_DOMAIN + accessToken;
            logger.info("小程序服务器域名设置提交参数url={}, data={}：", url, domainPostData);
            String result = "";
            //Map<String, String> resultMap = new HashedMap();

            //调用接口，错误返回码都是以异常WxErrorException的形式抛出来
            try{
                result = wxOpenService.post(url, domainPostData);
                logger.info("小程序服务器域名设置结果：" + result);
                Map<String, String> map = JsonUtils.fromJsonToMap(result);
                if("ok".equals(map.get("errmsg"))) {
                    weixinPublicSettingService.createByParam(publicEntity.getWeixinUid(), WeixinPublicSettingEntity.SetTypeEnum.serverDomainName,domainPostData,result, WeixinPublicSettingEntity.StatusEnum.domainSetSuccess);
                } else {
                    throw new BizException("小程序服务器域名设置");
                }
            }catch (WxErrorException e){
                logger.error("小程序服务器域名设置", e);
            }
        } catch (Exception e) {
            logger.error("小程序服务器域名设置", e);
            throw new BizException("小程序服务器域名设置");
        }
    }

    @Override
    public void bitchCommitDomain() {
        List<WeixinPublicEntity> publicList = publicRepository.findByTypeAndBindType(WeixinPublicEntity.TypeEnum.miniapp, WeixinPublicEntity.BindTypeEnum.weixin3open);
        for(WeixinPublicEntity publicEntity : publicList) {
            commitDomain(publicEntity);
        }
    }

    @Override
    public void commitViewDomain(String weixinUid) {
        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(weixinUid);
        if(publicEntity == null) {
            throw new BizException("小程序配置不存在");
        }
        commitViewDomain(publicEntity);
    }

    private void commitViewDomain(WeixinPublicEntity publicEntity) {
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(publicEntity.getType())) {
            logger.error("不是小程序无需设置业务域名,weixinUid=" + publicEntity.getWeixinUid());
            throw new BizException("不是小程序无需设置业务域名");
        }

        try {

            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);

            //设置业务域名
            Map<String, Object> webviewPostMap = new HashMap<>();
            webviewPostMap.put("action", "set");
            webviewPostMap.put("webviewdomain", Arrays.asList(zkfront, "https://p-erro.zchz.com"));
            String webviewPostData = JsonUtils.toJsonString(webviewPostMap);

            String url = WeixinConstant.WEBVIEW_DOMAIN + accessToken;
            logger.info("小程序业务域名设置提交参数url={}, data={}：", url, webviewPostData);
            try{
                String result = wxOpenService.post(url, webviewPostData);
                logger.info("小程序业务域名设置结果：" + result);
                Map<String, String> map = JsonUtils.fromJsonToMap(result);
                if("ok".equals(map.get("errmsg"))) {
                    weixinPublicSettingService.createByParam(publicEntity.getWeixinUid(), WeixinPublicSettingEntity.SetTypeEnum.businessDomainName,webviewPostData,result, WeixinPublicSettingEntity.StatusEnum.domainSetSuccess);
                } else {
                    throw new BizException("小程序业务域名设置异常");
                }

            }catch (WxErrorException e){
                logger.error("小程序业务域名设置异常", e);
            }

        } catch (Exception e) {
            logger.error("小程序域名设置异常", e);
            throw new BizException("小程序域名设置异常");
        }
    }

    @Override
    public void bitchCommitViewDomain() {
        List<WeixinPublicEntity> publicList = publicRepository.findByTypeAndBindType(WeixinPublicEntity.TypeEnum.miniapp, WeixinPublicEntity.BindTypeEnum.weixin3open);
        for(WeixinPublicEntity publicEntity : publicList) {
            commitViewDomain(publicEntity);
        }
    }

    /**
     *
     * @param weixinUid
     * @param version
     */
    @Override
    public void setWeappSupportVersion(String weixinUid, String version) {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("weixinUid不能为空");
        }
        if(StringUtils.isEmpty(version)) {
            version = WeixinConstant.SUPPORT_VERSION;
        }
        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(weixinUid);
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(publicEntity.getType())) {
            logger.error("不是小程序无需设置版本库,weixinUid=" + publicEntity.getWeixinUid());
            throw new BizException("不是小程序无需设置版本库");
        }
        try {
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            String accessToken = componentService.getAuthorizerAccessToken(publicEntity.getAppId(), false);
            String url = WeixinConstant.SET_WEAPP_SUPPORT_VERSION + accessToken;
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("version", version);
            String postData = JsonUtils.toJsonString(postMap);
            logger.info("小程序设置版本库提交参数url={}, data={}：", url, postData);
            String result = "";
            try{
                result = wxOpenService.post(url, postData);
                logger.info("小程序设置版本库结果：" + result);
                Map<String, String> map = JsonUtils.fromJsonToMap(result);
                if("ok".equals(map.get("errmsg"))) {
                    weixinPublicSettingService.createByParam(publicEntity.getWeixinUid(), WeixinPublicSettingEntity.SetTypeEnum.weappSupportVersion,postData,result, WeixinPublicSettingEntity.StatusEnum.domainSetSuccess);
                } else {
                    throw new BizException("小程序设置版本库异常");
                }
            }catch (WxErrorException e){
                logger.error("小程序设置版本库异常", e);
            }

        } catch (Exception e) {
            logger.error("小程序设置版本库异常", e);
            throw new BizException("小程序设置版本库异常");
        }
    }


    @Override
    public QrCodeDTO getAndCreateMiniQrCode(@RequestBody Map<String, String> map) {
        String weixinUid = map.get("weixinUid");
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("获取小程序二维码weixinUid为空");
        }
        String sceneStr = map.get("scene");
        if(StringUtils.isEmpty(sceneStr)) {
            throw new BizException("获取小程序二维码scene为空");
        }
        String path = "pages/index/index";
        if(!StringUtils.isEmpty(map.get("path"))) {
            path = map.get("path");
        }
        WeixinPublicEntity publicEntity = publicRepository.findByWeixinUid(weixinUid);
        Assert.notNull(publicEntity, "小程序不存在");
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(publicEntity.getType())) {
            throw new BizException("不是小程序");
        }
        WxMaQrcodeService qrcodeService =  wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(publicEntity.getAppId()).getQrcodeService();
        try {
            QrCodeDTO dto = new QrCodeDTO();
            File file = qrcodeService.createWxaCodeUnlimit(sceneStr, path);
            dto.setFile(file);
            //dto.setPath(fileUploadService.uploadFile(new MockMultipartFileDTO("file", file)));
            return dto;
        } catch (Exception e) {
            logger.error("生成小程序二维码异常：" + e);
            if(e instanceof WxErrorException) {
                WxErrorException we = (WxErrorException)e;
                if(we.getError().getErrorCode() == 41030) {
                    throw new BizException("小程序正在发布, 请等待发布完成！");
                }
            }
            throw new BizException("生成小程序二维码异常");
        }
    }


    @Override
    public Pager<MiniappPagerDTO> miniappPager(String shopUid, String miniappUid, Integer page, Integer size) {
        Page<WeixinPublicEntity> ep = weixinPublicDao.getMiniappPage(shopUid, miniappUid, page, size);

        Page<MiniappPagerDTO> dtoPage = ep.map(pub -> {
            MiniappPagerDTO dto = new MiniappPagerDTO();

          /*  XiehuaB2CShopEntity shopEntity = b2CShopRepository.findOneByMiniappUid(pub.getWeixinUid());
            if(shopEntity != null) {
                dto.setShopName(shopEntity.getName());
            }*/
            dto.setMiniappUid(pub.getWeixinUid());
            dto.setMiniappName(pub.getNickName());
            if(WeixinPublicEntity.BindTypeEnum.developer.equals(pub.getBindType())) {
                dto.setBindTypeName("开发者模式");
            }
            if(WeixinPublicEntity.BindTypeEnum.weixin3open.equals(pub.getBindType())) {
                dto.setBindTypeName("微信三方平台授权模式");
            }
            dto.setPrincipalName(pub.getPrincipalName());
            if(WeixinPublicEntity.StatusEnum.authorizedSuccess.equals(pub.getStatus())) {
                dto.setMiniappAuth(1);
                dto.setMiniappAuthName("已授权");
            } else {
                dto.setMiniappAuth(0);
                dto.setMiniappAuthName("未授权");
            }
            dto.setRemark(pub.getRemark());

            WeixinMimiappTemplateMapEntity mapEntity = new WeixinMimiappTemplateMapEntity();
            mapEntity.setWeixinUid(pub.getWeixinUid());
            if(!CollectionUtils.isEmpty(templateMapRepository.findByBeanPropWithLimit(mapEntity, 1))) {
                dto.setReleaseOnLine(1);
                dto.setReleaseOnLineName("是");
            } else {
                dto.setReleaseOnLine(0);
                dto.setReleaseOnLineName("否");
            }

            return dto;
        });
        return new Pager(dtoPage);
    }


    @Override
    public String getExperienceQrcode(String miniappUid, String path) throws Exception {
        WeixinPublicEntity miniappEntity = publicRepository.findByWeixinUid(miniappUid);
        Assert.notNull(miniappEntity, "小程序不存在");
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(miniappEntity.getType())) {
            throw new BizException("不是小程序");
        }
        String accessToken = wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(miniappEntity.getAppId(), false);
        String url = WeixinConstant.GET_QRCODE + accessToken;
        if(!StringUtils.isEmpty(path)) {
            path = path.replace("/", "%2F");
            url = url + "&path=" + path;
        }
        logger.info("获取小程序体验码url：" + url);
        byte[] response = restTemplate.getForObject(url, byte[].class);
        return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(response);
    }

    @Transactional
    @Override
    public String getMiniappQrcode(String miniappUid) throws Exception {
        WeixinPublicEntity miniappEntity = publicRepository.findByWeixinUid(miniappUid);
        Assert.notNull(miniappEntity, "小程序不存在");
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(miniappEntity.getType())) {
            throw new BizException("不是小程序");
        }
        WeixinMimiappTemplateMapEntity mapEntity = new WeixinMimiappTemplateMapEntity();
        mapEntity.setWeixinUid(miniappEntity.getWeixinUid());
        if(CollectionUtils.isEmpty(templateMapRepository.findByBeanPropWithLimit(mapEntity, 1))) {
            throw new BizException("小程序还未发布过，不能生成二维码");
        }
        if(StringUtils.isEmpty(miniappEntity.getQrcodeUrl())) {
            WxMaQrcodeService qrcodeService =  wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(miniappEntity.getAppId()).getQrcodeService();
            try {
                File file = qrcodeService.createQrcode("pages/index/index");
                //miniappEntity.setQrcodeUrl(fileUploadService.uploadFile(new MockMultipartFileDTO("file", file)));
                publicRepository.update(miniappEntity);
            } catch (Exception e) {
                logger.warn("小程序生成二维码异常, appId={}", miniappUid, e);
                throw new BizException("小程序生成二维码异常");
            }
        }
        return miniappEntity.getQrcodeUrl();
    }

    @Override
    public String getMiniPhone(String weixinUid, String encryptedData, String iv, String openId) throws Exception {
        WeixinPublicEntity configEntity = publicRepository.findByWeixinUid(weixinUid);
        WxMaUserService userService = miniAppFactory.getMaService(configEntity).getUserService();
        WeixinUserPublicMapEntity publicMapEntity = weixinUserPublicMapRepository.findByOpenIdAndWeixinUid(openId, weixinUid);
        WxMaPhoneNumberInfo numberInfo = userService.getPhoneNoInfo(publicMapEntity.getSessionKey(), encryptedData, iv);
        return numberInfo.getPhoneNumber();
    }
}
