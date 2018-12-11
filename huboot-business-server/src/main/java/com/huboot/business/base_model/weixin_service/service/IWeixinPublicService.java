package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.dto.WexinPublicPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.*;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicCreateDTO;
import com.huboot.business.base_model.weixin_service.dto.WexinAuthShopDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.common.component.exception.BizException;

import java.util.Map;

/**
 *商家微信公众号配置信息表Service
 */
public interface IWeixinPublicService {


    Pager<WexinAuthShopDTO> authShopPager(String shopUid, Integer page, Integer size);

    Pager<WexinPublicPagerDTO> publicPager(String shopUid, String publicUid, Integer page, Integer size);

    String getQrCode(String weixinUid);

    /**
     *
     * @param weixinUid
     * @return
     */
    WeixinPublicDTO getWeixinPublic(String weixinUid);

    /**
    * 创建
    * @param dto
    * @throws BizException
    */
    void create(WeixinPublicCreateDTO dto) throws BizException;

    /**
     * 验证服务器通过
     * @param weixinUid
     * @throws BizException
     */
    void validServerSuccess(String weixinUid);


    /**
     * 初始化公众号
     * @param weixinUid
     * @throws BizException
     */
    void initPublic(String weixinUid) throws BizException;


    /**
     * 获取微信授权认证url
     * @param weixinUid
     * @param authUrlDTO
     * @return
     */
    String getAuthUrl(String weixinUid, WeixinPublicAuthUrlDTO authUrlDTO);
    /**
     * 获取微信使用JSSDK
     * @param weixinUid
     * @param ticketDTO
     * @return
     */
    WeixinPublicJsapiSignatureDTO getTicket(String weixinUid, WeixinPublicTicketDTO ticketDTO);

    /**
     * 获取用户openid
     * @param weixinUid
     * @param wxcode
     * @return
     */
    WeixinAuthDTO getOpenId(String weixinUid, String wxcode);

    /**
     * 获取短链接
     * @param url
     * @return
     */
    String getShortUrl(String url, String weixinUid);


    /**
     *
     * @param weixinUid
     * @param wxcode
     * @return
     */
    WeixinBindUserDTO getWxUserInfo(String weixinUid, String wxcode);



    /**
     * 获取公众号二维码
     * @param map
     * @return
     */
    String getAndCreatePubQrCode(Map<String, String> map);

    /**
     *
     * @param originalId
     * @param openId
     * @param eventKey
     */
    void handleSacnAEvent(String originalId, String openId, String eventKey);

    /**
     * 获取通过渠道码关注的人数
     * @param startTime
     * @param endTime
     * @param weixinUid
     * @param sence
     * @return
     */
    WeixinSubscribeCountDTO getSubscribeCountWithSence(String startTime, String endTime, String weixinUid, String sence);



    public Map<String,String> isSubscribe(String phone) throws Exception;

    public Map<String,String> getShopQrcodeUrl(String shopUid) throws Exception;
}
