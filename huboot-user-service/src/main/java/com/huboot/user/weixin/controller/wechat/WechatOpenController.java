package com.huboot.user.weixin.controller.wechat;

import com.huboot.user.common.constant.WeixinConstant;
import com.huboot.user.weixin.config.WxMpXmlMessageExt;
import com.huboot.user.weixin.service.IMiniappReleaseLogService;
import com.huboot.user.weixin.service.IOpenAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.util.WxOpenCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Api(tags = "网约车司机小程序端-小程序开放平台回调接口 API")
@RestController
@RequestMapping("/weixin/open/")
public class WechatOpenController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private WxOpenMessageRouter messageRouter;
    @Autowired
    private IMiniappReleaseLogService releaseLogService;
    @Autowired
    private IOpenAppService openAppService;

    /**
     *
     * @param requestBody
     * @param timestamp
     * @param nonce
     * @param signature
     * @param encType
     * @param msgSignature
     * @return
     */
    @RequestMapping("/receive_ticket")
    @ApiOperation("平台消息")
    public Object receiveTicket(@RequestBody(required = false) String requestBody,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce,
                                @RequestParam("signature") String signature,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        this.logger.info("接收微信receive_ticket请求：[signature=[{}], encType=[{}], msgSignature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody, wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        this.logger.info("消息解密后内容为：\n{} ", inMessage.toString());
        try {
            String out = wxOpenService.getWxOpenComponentService().route(inMessage);
            openAppService.updateAuthorize(inMessage.getAppId(), inMessage.getInfoType());
            this.logger.info("组装回复信息：{}", out);
        } catch (WxErrorException e) {
            this.logger.error("receive_ticket", e);
        }
        return "success";
    }


    /**
     *
     * @param requestBody
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
    @RequestMapping("{appId}/callback")
    @ApiOperation("公众号、小程序消息")
    public Object callback(@RequestBody(required = false) String requestBody,
                           @PathVariable("appId") String appId,
                           @RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("openid") String openid,
                           @RequestParam("encrypt_type") String encType,
                           @RequestParam("msg_signature") String msgSignature) {
        this.logger.info("接收微信callback请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = "";
        // aes加密的消息
        WxOpenCryptUtil cryptUtil = new WxOpenCryptUtil(wxOpenService.getWxOpenConfigStorage());
        String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, requestBody);
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(plainText);

        // 全网发布测试用例
        if (StringUtils.equalsAnyIgnoreCase(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8")) {
            out = this.releaseOpen(inMessage, appId);
        } else {
            if(WxConsts.XmlMsgType.EVENT.equals(inMessage.getMsgType())
                    && (WeixinConstant.WEAPP_AUDIT_SUCCESS.equals(inMessage.getEvent()) || WeixinConstant.WEAPP_AUDIT_FAIL.equals(inMessage.getEvent()))) {
                WxMpXmlMessageExt messageExt = WxMpXmlMessageExt.fromXml(plainText);
                this.logger.info("小程序代码审核结果消息解密后内容为：\n{} ", messageExt.toString());
                try {
                    releaseLogService.handleAuditResult(appId);
                } catch (Exception e) {
                    logger.error("小程序审核结果处理异常", e);
                }
            } else {
                this.logger.info("消息解密后内容为：\n{} ", inMessage.toString());
                WxMpXmlOutMessage outMessage = messageRouter.route(inMessage, appId);
                if(outMessage != null){
                    out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage, wxOpenService.getWxOpenConfigStorage());
                }
            }
        }
        return out;
    }

    /**
     *  平台全网发布
     * @param inMessage
     * @param appId
     * @return
     */
    private String releaseOpen(WxMpXmlMessage inMessage, String appId) {
        String out = "";
        try {
            if (StringUtils.equals(inMessage.getMsgType(), "text")) {
                if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                    out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                            WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                    .fromUser(inMessage.getToUser())
                                    .toUser(inMessage.getFromUser())
                                    .build(),
                            wxOpenService.getWxOpenConfigStorage()
                    );
                } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                    String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                    wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                }
            } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
                WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
            }
        } catch (WxErrorException e) {
            logger.error("callback", e);
        }
        return out;
    }
}
