package com.huboot.business.base_model.weixin_service.dto.weixin_center;


import com.huboot.business.base_model.weixin_service.dto.common.constant.ApplicationName;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = ApplicationName.XIEHUA_SERVER)
public interface IWeixinPublicSao {

    public static final String SYS_QRCODE_KEY_SCENE = "scene";

    public static final String SYS_QRCODE_KEY_WEIXINUID = "weixinUid";

    @GetMapping(value = "/base_model/weixin_service/weixinPublic/getWeixinPublic/{weixinUid}")
    public WeixinPublicDTO getWeixinPublic(@PathVariable("weixinUid") String weixinUid);

    /**
     * 获取微信使用JSSDK
     *
     * @param weixinUid
     * @param authUrlDTO
     * @return
     */
    @PostMapping(value = "/base_model/weixin_service/weixinPublic/getAuthUrl/{weixinUid}")
    public String getAuthUrl(@PathVariable("weixinUid") String weixinUid, @RequestBody WeixinPublicAuthUrlDTO authUrlDTO);

    /**
     * 获取微信认证url
     *
     * @param weixinUid
     * @param authUrlDTO
     * @return
     */
    @PostMapping(value = "/base_model/weixin_service/weixinPublic/ticket/{weixinUid}")
    public WeixinPublicJsapiSignatureDTO getTicket(@PathVariable("weixinUid") String weixinUid, @RequestBody WeixinPublicTicketDTO authUrlDTO);

    /**
     * 绑定openid跟手机号
     *
     * @param weixinUid
     * @param wxcode
     * @return openId
     */
    @GetMapping(value = "/base_model/weixin_service/weixinPublic/getWxUserInfo")
    public WeixinBindUserDTO getWxUserInfo(@RequestParam("weixinUid") String weixinUid, @RequestParam("wxcode") String wxcode);


    /**
     * 获取开放平台微信认证url
     *
     * @param authUrlDTO
     * @return
     */
    @PostMapping(value = "/base_model/weixin_service/open/getPreAuthUrl")
    public String getOpenAuthUrl(@RequestBody WeixinPublicAuthUrlDTO authUrlDTO);


    @GetMapping(value = "/base_model/weixin_service/weixinMimiapp/getMiniAppOpenId")
    public WeixinAuthDTO getMiniAppOpenId(@RequestParam("miniAppId") String weixinUid, @RequestParam("wxcode") String wxcode);


    @GetMapping(value = "/base_model/weixin_service/weixinPublic/getOpenId")
    public WeixinAuthDTO getPublicOpenId(@RequestParam("weixinUid") String weixinUid, @RequestParam("wxcode") String wxcode);

    /**
     * 获取短链接
     * @param map('url': 'xxx')
     * @return
     */
    @PostMapping(value = "/base_model/weixin_service/weixinPublic/getShortUrl")
    public String getShortUrl(@RequestBody Map<String, String> map);

    /**
     * @return
     */
    @GetMapping(value = "/base_model/weixin_service/open/init_weixin_with_openauthcode")
    public WeixinPublicDTO initWeixinWithOpenAuthCode(@RequestParam("authorizationCode") String authorizationCode,
                                                      @RequestParam("type") Integer type);



    /**
     * 获取公众号二维码
     * @param map{"weixinUid":"", "scene": ""}
     * @return
     */
    @PostMapping(value = "/base_model/weixin_service/weixinPublic/getAndCreatePubQrCode")
    public String getPubQrCode(@RequestBody Map<String, String> map);

    /**
     * 获取小程序二维码
     * @param map{"weixinUid":"", "scene": ""}
     * @return
     */
    @PostMapping(value = "/base_model/weixin_service/weixinMimiapp/getAndCreateMiniQrCode")
    public QrCodeDTO getMiniQrCode(@RequestBody Map<String, String> map);


    @GetMapping(value = "/base_model/weixin_service/weixinPublic/getSubscribeCountWithSence")
    public WeixinSubscribeCountDTO getSubscribeCountWithSence(@RequestParam("startTime") String startTime,
                                                              @RequestParam("endTime") String endTime,
                                                              @RequestParam("weixinUid") String weixinUid,
                                                              @RequestParam("sence") String sence);

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum SysQrCodeActionEnum{

        promotion("a_","渠道营销二维码"),e_card("b_","电子名片二维码");

        private String action;

        private String showName;
    }


    @GetMapping(value = "/base_model/weixin_service/weixinPublic/getAuthorizerInfoByWxCode/{wxCode}")
    public Map<String,String> getAuthorizerInfoByWxCode(@PathVariable("wxCode") String wxCode);


    @GetMapping(value = "/base_model/weixin_service/weixinMimiapp/getMiniAppPhone")
    public String getMiniAppPhone(@RequestParam("miniAppId") String weixinUid, @RequestParam("encryptedData") String encryptedData,
                                  @RequestParam("iv") String iv, @RequestParam("openId") String openId);
}
