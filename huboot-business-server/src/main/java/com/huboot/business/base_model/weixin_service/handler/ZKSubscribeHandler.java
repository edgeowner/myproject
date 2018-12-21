package com.huboot.business.base_model.weixin_service.handler;

import com.huboot.business.base_model.weixin_service.builder.TextBuilder;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.IWeixinPublicSao;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ZKSubscribeHandler extends AbstractHandler {

    @Autowired
    private IWeixinUserService weixinUserService;
    @Value("${huboot.domain.zkfront}")
    private String frontDomain;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private IWeixinPublicSao weixinPublicSao;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                Map<String, Object> context, WxMpService weixinService,
                                WxSessionManager sessionManager) throws WxErrorException {

        String openId = wxMessage.getFromUser();
        String originalId = wxMessage.getToUser();
        this.logger.info("关注用户 OPENID: " + openId + " 公帐号ID：" + originalId + " EventKey:" + wxMessage.getEventKey());

        try {
            String eventKey = "";
            if(!StringUtils.isEmpty(wxMessage.getEventKey())) {
                eventKey = wxMessage.getEventKey().replace("qrscene_", "");
            }
            weixinUserService.subscribe(originalId, openId, eventKey);
            return new TextBuilder().build(this.helloWord(originalId), wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    private String helloWord(String originalId) {
        try {
            WeixinPublicEntity publicEntity = weixinPublicRepository.findByOriginalId(originalId);
            /*ZkShopRespDTO respDTO = bscShopSao.findShopByWeixinUid(publicEntity.getWeixinUid());
            StringBuilder content = new StringBuilder("");
            if(respDTO == null){
                return "感谢关注";
            }*/
         /*   AutoReplyDTO autoReplyDTO = autoReplySao.findByShopUid(respDTO.getShopUid());
            if(autoReplyDTO != null && !StringUtils.isEmpty(autoReplyDTO.getContent())){
                content.append(autoReplyDTO.getContent());
            }else{
                autoReplyDTO = autoReplySao.findByShopUid("system");
                content.append(autoReplyDTO.getContent());
                String shopUid = respDTO.getShopUid();
                Map<String,String> carShortUrlMap = new HashMap<>();
                carShortUrlMap.put("url",frontDomain + MenuEnum.carList.getUrl().replaceFirst("\\{0\\}", shopUid));
                //车辆列表
                content.append("惠价好车，整备待租："+weixinPublicSao.getShortUrl(carShortUrlMap)+"\n");
                //熟人推荐
                ArticleRecommendQueryDTO queryDTO = new ArticleRecommendQueryDTO();
                queryDTO.setShopUid(shopUid);
                queryDTO.setPage(1);
                queryDTO.setSize(Integer.MAX_VALUE);
                queryDTO.setAuditStatus(1);
                Pager<ArticleRecommendResDTO> articleRecommendResDTOPager =  articleRecommendSao.getArticleRecommendForPager(queryDTO);
                if(articleRecommendResDTOPager!=null && CollectionUtils.isNotEmpty(articleRecommendResDTOPager.getPageItems())){
                    Map<String,String> articleShortUrlMap = new HashMap<>();
                    articleShortUrlMap.put("url",frontDomain + MenuEnum.articleRecommend.getUrl().replaceFirst("\\{0\\}", shopUid));
                    content.append("本土风情，独家攻略："+weixinPublicSao.getShortUrl(articleShortUrlMap)+"\n");
                }
                //本地特产
                String[] icons =  respDTO.getHomeIconShow().split("\\D");
                Arrays.stream(ZkShopRespDTO.ZKHomeIconTypeEnum.values()).forEach(zkHomeIconTypeEnum -> {
                    long count = Arrays.stream(icons).filter(icon->{
                        return Integer.parseInt(icon) == zkHomeIconTypeEnum.getValue().intValue();
                    }).count();

                    if( count > 0 &&zkHomeIconTypeEnum.getShowName().equals(MenuEnum.GoodsMall.getName())){
                        Map<String,String> goodsShortUrlMap = new HashMap<>();
                        goodsShortUrlMap.put("url",frontDomain + MenuEnum.GoodsMall.getUrl().replaceFirst("\\{0\\}", shopUid));
                        content.append("甄选特产，贵在纯正："+weixinPublicSao.getShortUrl(goodsShortUrlMap));
                    }
                });

            }
            *//*AutoReplyDTO defaultAutoReplyDTO = autoReplySao.findByShopUid("system");
            if(content.toString().equals(defaultAutoReplyDTO.getContent())){
                String url = frontDomain + MenuEnum.home.getUrl().replaceFirst("\\{0\\}", respDTO.getShopUid());
                url = weixinPublicService.getShortUrl(url);
                content.append("<a href=\"" + url + "\">" + url + "</a>\n");
            }*/
            return "";
        } catch (Exception e) {
            logger.error("关注欢迎语异常", e);
            return "感谢关注";
        }
    }

}
