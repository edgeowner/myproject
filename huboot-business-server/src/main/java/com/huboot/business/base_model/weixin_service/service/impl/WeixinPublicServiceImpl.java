package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.config.WeixinConstant;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicCreateDTO;
import com.huboot.business.base_model.weixin_service.dto.WexinPublicPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.IWeixinPublicSao;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.*;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserPublicMapEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserPublicMapRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicService;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserPublicMapService;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserService;
import com.huboot.business.base_model.weixin_service.support.WechatMpFactory;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.jpa.ConditionMap;
import com.huboot.business.common.jpa.QueryCondition;
import com.huboot.business.base_model.weixin_service.dao.IWeixinPublicDao;
import com.huboot.business.base_model.weixin_service.dto.WexinAuthShopDTO;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 商家微信公众号配置信息表ServiceImpl
 */
@Service("weixinPublicServiceImpl")
public class WeixinPublicServiceImpl implements IWeixinPublicService {

    private Logger logger = LoggerFactory.getLogger(WeixinPublicServiceImpl.class);

    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private WechatMpFactory wechatMpFactory;
    @Value("${huboot.domain.zkfront}")
    private String frontDomain;
    @Value("${huboot.domain.thdcFront}")
    private String thdcFront;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private IWeixinUserPublicMapService weixinUserPublicMapService;

    @Autowired
    private IWeixinUserRepository weixinUserRepository;
    @Autowired
    private IWeixinUserService weixinUserService;
    @Autowired
    private IWeixinUserPublicMapRepository weixinUserPublicMapRepository;

    @Autowired
    private IWeixinPublicDao weixinPublicDao;


    @Override
    public Pager<WexinAuthShopDTO> authShopPager(String shopUid, Integer page, Integer size) {
       /* Page<XiehuaB2CShopEntity> ep = b2CShopRepository.findPage(QueryCondition.from(XiehuaB2CShopEntity.class).where(list -> {
            if(!StringUtils.isEmpty(shopUid)) {
                list.add(ConditionMap.eq("shopUid", shopUid));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));
        Page<WexinAuthShopDTO> dtoPage = ep.map(shop -> {
            WexinAuthShopDTO dto = new WexinAuthShopDTO();

            dto.setShopName(shop.getName());
            dto.setShopStatusName(shop.getStatus().getShowName());
            dto.setPublicUid(shop.getWeixinUid());
            if(!StringUtils.isEmpty(shop.getWeixinUid())) {
                WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(shop.getWeixinUid());
                if(publicEntity != null) {
                    dto.setPublicName(publicEntity.getNickName());
                    if(WeixinPublicEntity.StatusEnum.authorizedSuccess.equals(publicEntity.getStatus())) {
                        dto.setPublicAuth("已授权");
                    } else {
                        dto.setPublicAuth("未授权");
                    }
                }
            }
            dto.setMiniappUid(shop.getMiniappUid());
            if(!StringUtils.isEmpty(shop.getMiniappUid())) {
                WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(shop.getMiniappUid());
                if(publicEntity != null) {
                    dto.setMiniappName(publicEntity.getNickName());

                    if(WeixinPublicEntity.StatusEnum.authorizedSuccess.equals(publicEntity.getStatus())) {
                        dto.setMiniappAuth("已授权");
                    } else {
                        dto.setMiniappAuth("未授权");
                    }
                }
            }
            return dto;
        });*/
        return null;
    }


    @Override
    public Pager<WexinPublicPagerDTO> publicPager(String shopUid, String publicUid, Integer page, Integer size) {
/*
        Page<WeixinPublicEntity> ep = weixinPublicDao.getPublicPage(shopUid, publicUid, page, size);

        Page<WexinPublicPagerDTO> dtoPage = ep.map(pub -> {
            WexinPublicPagerDTO dto = new WexinPublicPagerDTO();

            XiehuaB2CShopEntity shopEntity = b2CShopRepository.findOneByWeixinUid(pub.getWeixinUid());
            if(shopEntity != null) {
                dto.setShopName(shopEntity.getName());
            }
            dto.setPublicUid(pub.getWeixinUid());
            dto.setPublicName(pub.getNickName());
            if(WeixinPublicEntity.BindTypeEnum.developer.equals(pub.getBindType())) {
                dto.setBindTypeName("开发者模式");
            }
            if(WeixinPublicEntity.BindTypeEnum.weixin3open.equals(pub.getBindType())) {
                dto.setBindTypeName("微信三方平台授权模式");
            }
            dto.setPrincipalName(pub.getPrincipalName());
            if(WeixinPublicEntity.StatusEnum.authorizedSuccess.equals(pub.getStatus())) {
                dto.setPublicAuth(1);
                dto.setPublicAuthName("已授权");
            } else {
                dto.setPublicAuth(0);
                dto.setPublicAuthName("未授权");
            }
            dto.setRemark(pub.getRemark());

            return dto;
        });*/
        return null;
    }


    @Transactional
    @Override
    public String getQrCode(String weixinUid) {
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if(publicEntity == null) {
            throw new BizException("微信配置不存在");
        }
        if(StringUtils.isEmpty(publicEntity.getQrcodeUrl())) {
            Map<String, String> map = new HashMap<>();
            map.put("weixinUid", weixinUid);
            map.put("scene", "init");
            String qrCodeUrl = getAndCreatePubQrCode(map);
            publicEntity.setQrcodeUrl(qrCodeUrl);
            weixinPublicRepository.update(publicEntity);
        }
        return publicEntity.getQrcodeUrl();
    }

    /**
     * @param weixinUid
     * @return
     */
    @Override
    public WeixinPublicDTO getWeixinPublic(String weixinUid) {
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        WeixinPublicDTO publicDTO = new WeixinPublicDTO();
        BeanUtils.copyProperties(publicEntity, publicDTO);
        publicDTO.setStatus(publicEntity.getStatus().ordinal());
        publicDTO.setBindType(publicEntity.getBindType().ordinal());
        publicDTO.setType(publicEntity.getType().ordinal());
        return publicDTO;
    }

    /**
     * 配置公众号
     *
     * @param dto
     * @throws BizException
     */
    @Transactional
    @Override
    public void create(WeixinPublicCreateDTO dto) throws BizException {
        WeixinPublicEntity entity = new WeixinPublicEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setWeixinUid(UUID.randomUUID().toString().replace("-", ""));
        entity.setBindType(WeixinPublicEntity.BindTypeEnum.developer);
        entity.setType(WeixinPublicEntity.TypeEnum.get(dto.getType()));
        if(WeixinPublicEntity.TypeEnum.miniapp.equals(dto.getType())){
            entity.setStatus(WeixinPublicEntity.StatusEnum.authorizedSuccess);
        }else{
            entity.setStatus(WeixinPublicEntity.StatusEnum.waitValid);
        }

        weixinPublicRepository.create(entity);
    }

    /**
     * 微信账号服务器配置通过
     *
     * @param weixinUid
     * @throws BizException
     */
    @Transactional
    @Override
    public void validServerSuccess(String weixinUid) {
        if (StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if (publicEntity == null) {
            logger.error("公众号配置为空,weixinUid=" + weixinUid);
            throw new BizException("公众号配置为空！");
        }
        publicEntity.setStatus(WeixinPublicEntity.StatusEnum.validServerSuccess);
        weixinPublicRepository.update(publicEntity);
    }

    /**
     * 初始化公众号
     * 1.校验公众号是否填写正确
     * 2.初始化店铺
     * 3.初始化信菜单
     * 3.初始化信消息模板
     *
     * @param weixinUid
     * @throws BizException
     */
    @Transactional
    @Override
    public void initPublic(String weixinUid) throws BizException {
        if (StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if (WeixinPublicEntity.StatusEnum.waitValid.equals(publicEntity.getStatus())) {
            logger.error("账号已经初始化，weixinUid={}", weixinUid);
            throw new BizException("服务器还未验证通过，请先验证！");
        }
        //校验公众号是否填写正确
        wechatMpFactory.getDeveloperWXMpServiceWithNotValid(weixinUid);
        Map<String, String> map = new HashMap<>();
        map.put("weixinUid", weixinUid);
        map.put("scene", "init");
        String qrCodeUrl = getAndCreatePubQrCode(map);
        publicEntity.setQrcodeUrl(qrCodeUrl);
        weixinPublicRepository.update(publicEntity);
    }

    /**
     * 获取微信授权认证url
     *
     * @param weixinUid
     * @param authUrlDTO
     * @return
     */
    @Override
    public String getAuthUrl(String weixinUid, WeixinPublicAuthUrlDTO authUrlDTO) {
        WeixinPublicEntity configEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if (configEntity == null) {
            throw new BizException("微信uid:" + weixinUid + "配置不存在");
        }
        String scopeType = authUrlDTO.getScopeType();
        String url = "";
        if (StringUtils.isEmpty(scopeType)) {
            scopeType = WeixinConstant.WEIXIN_SCOPE_USERINFO;
        }
        if (SystemEnum.zk.getVal().equals(authUrlDTO.getSystem().getVal())) {
            url = frontDomain + authUrlDTO.getUrl();
        }
        if (SystemEnum.thdc.getVal().equals(authUrlDTO.getSystem().getVal())) {
            url = thdcFront + authUrlDTO.getUrl();
        }
        if (WeixinPublicEntity.BindTypeEnum.developer.equals(configEntity.getBindType())) {
            WxMpService wxMpService = wechatMpFactory.getDeveloperWxMpService(configEntity);
            return wxMpService.oauth2buildAuthorizationUrl(url, scopeType, WeixinConstant.WEIXIN_STATE);
        } else if (WeixinPublicEntity.BindTypeEnum.weixin3open.equals(configEntity.getBindType())) {
            if (WeixinPublicEntity.StatusEnum.unAuthorized.equals(configEntity.getStatus())) {
                throw new BizException("微信uid:" + weixinUid + "公众号已经取消授权给开放平台");
            }
            WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
            return componentService.oauth2buildAuthorizationUrl(configEntity.getAppId(), url, scopeType, WeixinConstant.WEIXIN_STATE);
        } else {
            throw new BizException("获取微信授权认证url错误");
        }

    }

    /**
     * 获取微信使用JSSDK
     *
     * @param weixinUid
     * @param authUrlDTO
     * @return
     */
    @Override
    public WeixinPublicJsapiSignatureDTO getTicket(String weixinUid, WeixinPublicTicketDTO authUrlDTO) {
        WeixinPublicJsapiSignatureDTO weixinPublicJsapiSignatureDTO = new WeixinPublicJsapiSignatureDTO();
        WeixinPublicEntity configEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if (configEntity == null) {
            throw new BizException("微信uid:" + weixinUid + "配置不存在");
        }
        try {
            if (WeixinPublicEntity.TypeEnum.pubapp.equals(configEntity.getType())) {
                WxMpService wxMpService = wechatMpFactory.getWXMpService(configEntity);
                String url = URLDecoder.decode(authUrlDTO.getUrl(),"UTF-8");
                WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);
                BeanUtils.copyProperties(wxJsapiSignature, weixinPublicJsapiSignatureDTO);
            } else {
                throw new BizException("获取微信使用JSSDK错误");
            }
        } catch (WxErrorException e) {
            throw new BizException(BizException.RETRY, "获取微信使用JSSDK异常");
        }catch (UnsupportedEncodingException e){
            throw new BizException(BizException.RETRY, "获取微信使用JSSDK异常");
        }
        return weixinPublicJsapiSignatureDTO;
    }

    /**
     * 获取用户openid
     *
     * @param weixinUid
     * @param wxcode
     * @return
     */
    @Override
    public WeixinAuthDTO getOpenId(String weixinUid, String wxcode) {
        if (StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        if (StringUtils.isEmpty(wxcode)) {
            throw new BizException("微信code不能为空");
        }
        WeixinPublicEntity configEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if (configEntity == null) {
            throw new BizException("微信uid:" + weixinUid + "配置不存在");
        }
        WeixinAuthDTO authDTO = new WeixinAuthDTO();
        try {
            if (WeixinPublicEntity.BindTypeEnum.developer.equals(configEntity.getBindType())) {
                WxMpService wxMpService = wechatMpFactory.getDeveloperWxMpService(configEntity);
                WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(wxcode);
                authDTO.setOpenId(accessToken.getOpenId());
                authDTO.setUnionid(accessToken.getUnionId());
            } else if (WeixinPublicEntity.BindTypeEnum.weixin3open.equals(configEntity.getBindType())) {
                if (WeixinPublicEntity.StatusEnum.unAuthorized.equals(configEntity.getStatus())) {
                    throw new BizException("微信uid:" + weixinUid + "公众号已经取消授权给开放平台");
                }
                WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
                WxMpOAuth2AccessToken accessToken = componentService.oauth2getAccessToken(configEntity.getAppId(), wxcode);
                authDTO.setOpenId(accessToken.getOpenId());
                authDTO.setUnionid(accessToken.getUnionId());
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
        } catch (WxErrorException e) {
            logger.error("获取微信openid异常,wxcode=" + wxcode, e);
            throw new BizException(BizException.RETRY, "获取微信openid异常");
        }
    }

    @Override
    public String getShortUrl(String url, String weixinUid) {
        if(StringUtils.isEmpty(url)) {
            throw new BizException("url不能为空");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
        try {
            return wxMpService.shortUrl(url);
        } catch (Exception e) {
            logger.error("获取短链接异常", e);
            throw new BizException("获取短链接异常");
        }

    }

    /**
     *
     *
     * @param weixinUid
     * @param wxcode
     * @return
     */
    @Transactional
    @Override
    public WeixinBindUserDTO getWxUserInfo(String weixinUid, String wxcode) {
        if (StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        if (StringUtils.isEmpty(wxcode)) {
            throw new BizException("微信wxcode不能为空");
        }

        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if (publicEntity == null) {
            logger.error("微信配置不存在！weixinUid={}", weixinUid);
            throw new BizException("微信配置不存在");
        }
        WeixinAuthDTO authDTO = getOpenId(weixinUid, wxcode);

        WeixinUserEntity userEntity = weixinUserService.setPubUser(publicEntity, authDTO.getOpenId());

        WeixinBindUserDTO bindUserDTO = new WeixinBindUserDTO();
        bindUserDTO.setOpenId(authDTO.getOpenId());
        bindUserDTO.setHeadimgurl(userEntity.getHeadimgurl());
        bindUserDTO.setNickname(userEntity.getNickname());

        return bindUserDTO;
    }

    @Override
    public String getAndCreatePubQrCode(Map<String, String> map) {
        String weixinUid = map.get("weixinUid");
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("获取公众号二维码weixinUid为空");
        }
        String sceneStr = map.get("scene");
        if(StringUtils.isEmpty(sceneStr)) {
            throw new BizException("获取公众号二维码scene为空");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
        try {
            WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
            WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateLastTicket(sceneStr);
            return qrcodeService.qrCodePictureUrl(ticket.getTicket(), true);
        } catch (WxErrorException e) {
            logger.error("公众号配置异常:" + e.getError().getJson());
            throw new BizException("公众号配置错误，请检查");
        }
    }

    /**
     * 扫码事件
     * @param originalId
     * @param openId
     * @param eventKey
     */
    @Override
    public void handleSacnAEvent(String originalId, String openId, String eventKey) {
        try {
            if(eventKey.startsWith(IWeixinPublicSao.SysQrCodeActionEnum.promotion.getAction())) {
                logger.info("收到" + IWeixinPublicSao.SysQrCodeActionEnum.promotion.getShowName() + "扫码事件, eventKey={}", eventKey);
                WeixinPublicEntity publicEntity = weixinPublicRepository.findByOriginalId(originalId);
                if(publicEntity == null) {
                    logger.error("微信配置不存在,originalId=" + originalId);
                } else {

                }
            } else {

            }
        } catch (Exception e) {
            logger.error("渠道扫码通知异常", e);
        }
    }

    /**
     *
     * @param startTime
     * @param endTime
     * @param weixinUid
     * @param sence
     * @return
     */
    @Override
    public WeixinSubscribeCountDTO getSubscribeCountWithSence(String startTime, String endTime, String weixinUid, String sence) {
        WeixinSubscribeCountDTO countDTO = new WeixinSubscribeCountDTO();
        List<WeixinUserPublicMapEntity> publicMapList = weixinUserPublicMapRepository.findByCondition(
                QueryCondition.from(WeixinUserPublicMapEntity.class).where(list -> {
                    list.add(ConditionMap.eq("weixinUid", weixinUid));
                    list.add(ConditionMap.gte("updateTime", LocalDateTime.parse(startTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    list.add(ConditionMap.lt("updateTime", LocalDateTime.parse(endTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                })
        );
        if(CollectionUtils.isEmpty(publicMapList)) {
            return countDTO;
        }
        sence = IWeixinPublicSao.SysQrCodeActionEnum.promotion.getAction() + sence;
        for(WeixinUserPublicMapEntity publicMapEntity : publicMapList) {
            WeixinUserEntity userEntity = weixinUserRepository.findByOpenId(publicMapEntity.getOpenId());
            if(sence.equals(userEntity.getSubscribeSource())) {
                countDTO.setTotalSubscribeCount(countDTO.getTotalSubscribeCount() + 1);
                if(userEntity.getSubscribe() == 1) {
                    countDTO.setActualSubscribeCount(countDTO.getActualSubscribeCount() + 1);
                }
            }
        }
        return countDTO;
    }




    @Override
    public Map<String,String> isSubscribe(String openId) throws Exception{
        //String shopUid = (String) RequestInfo.get(ClientInfoInterceptor.SHOP_UID);
        Map<String, String> resultMap = new HashMap<String, String>();
        //1是
        String isSubscribe = "";
        //ZkShopRespDTO shopRespDTO = b2CShopSao.findShopByShopUid(shopUid);
        //查询此店铺二维码
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid("");
        if(StringUtils.isEmpty(openId)){
            isSubscribe = "0";
        }else{
            WeixinUserEntity userEntity = weixinUserRepository.findByOpenId(openId);
            //如果用户取消关注会将号码置为空,又再次关注的时候，是否关注会改为1，但是iphone字段还是空，因为这时并不知道具体是谁，需要再再次登录的时候，重新绑定
            if(userEntity == null){
                isSubscribe = "0";
            }else{
                isSubscribe = String.valueOf(userEntity.getSubscribe());
            }
        }

        resultMap.put("isSubscribe",isSubscribe);
        resultMap.put("qrcodeUrl",publicEntity.getQrcodeUrl());
        String saveQrcodeUrl = publicEntity.getSaveQrcodeUrl();
        if(StringUtils.isEmpty(saveQrcodeUrl)){
            //saveOtherQrcode(shopRespDTO.getName(), publicEntity);
        }
        resultMap.put("saveQrcodeUrl",publicEntity.getSaveQrcodeUrl());
        return resultMap;
    }

    private void saveOtherQrcode(String shopName, WeixinPublicEntity publicEntity) throws Exception {
        /*String[] text = {shopName};
        byte[] bytes = ImageUtils.createShopQRCode(publicEntity.getQrcodeUrl(),text);
        InputStream input = new ByteArrayInputStream(bytes);
        //获取图片的MD5
        String fileMD5 = FileUtils.md5(input);
        //获取上传文件的路径
        String uploadPath = UpYunUtil.getFilePath(UploadFileTypeEnum.Img, "xiehua", UploadFileBusinessTypeEnum.QRCode, fileMD5, "lottery.png", null);
        //上传到upyun
        UpYunUtil.writeFile(FileServiceTypeEnum.PublicImage, uploadPath, bytes, null);
        String url = UpYunUtil.getDomain(FileServiceTypeEnum.PublicImage) + uploadPath;
        publicEntity.setSaveQrcodeUrl(url);
        weixinPublicRepository.update(publicEntity);*/
    }


    @Override
    public Map<String, String> getShopQrcodeUrl(String shopUid) throws Exception {
        /*Map<String, String> resultMap = new HashMap<String, String>();
        //ZkShopRespDTO shopRespDTO = b2CShopSao.findShopByShopUid(shopUid);
        //查询此店铺二维码
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(shopRespDTO.getWeixinUid());
        resultMap.put("qrcodeUrl",publicEntity.getQrcodeUrl());
        String saveQrcodeUrl = publicEntity.getSaveQrcodeUrl();
        if(StringUtils.isEmpty(saveQrcodeUrl)){
            saveOtherQrcode(shopRespDTO.getName(), publicEntity);
        }
        resultMap.put("saveQrcodeUrl",publicEntity.getSaveQrcodeUrl());
        resultMap.put("shopName",shopRespDTO.getName());*/
        return null;
    }


}
