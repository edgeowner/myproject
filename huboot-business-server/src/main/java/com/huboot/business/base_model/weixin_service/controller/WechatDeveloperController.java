package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.service.IWeixinPublicService;
import com.huboot.business.base_model.weixin_service.support.WechatMpFactory;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@RestController
@RequestMapping("/base_model/weixin_service/portal")
public class WechatDeveloperController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatMpFactory wechatMpFactory;
    @Autowired
    private IWeixinPublicService weixinPublicService;

    @GetMapping(value = "/{weixinUid}",produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable("weixinUid") String weixinUid,
      @RequestParam(name = "signature",required = false) String signature,
      @RequestParam(name = "timestamp",required = false) String timestamp,
      @RequestParam(name = "nonce", required = false) String nonce,
      @RequestParam(name = "echostr", required = false) String echostr) {

        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        WxMpService wxService = wechatMpFactory.getDeveloperWXMpServiceWithNotValid(weixinUid);
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            weixinPublicService.validServerSuccess(weixinUid);
            logger.info("验证成功了");
            return echostr;
        }
        logger.error("验证失败了");
        //weixinPublicService.validServerFailure(weixinUid);
        return "非法请求";
    }

    @PostMapping(value = "/{weixinUid}", produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable("weixinUid") String weixinUid,
                     @RequestBody String requestBody,
                     @RequestParam("signature") String signature,
                     @RequestParam("timestamp") String timestamp,
                     @RequestParam("nonce") String nonce,
                     @RequestParam(name = "encrypt_type", required = false) String encType,
                     @RequestParam(name = "msg_signature",required = false) String msgSignature) {

        this.logger.info("\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
          signature, encType, msgSignature, timestamp, nonce, requestBody);

        WxMpService wxService = wechatMpFactory.getDeveloperWXMpService(weixinUid);

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        WxMpMessageRouter wxMpMessageRouter =  wechatMpFactory.createRouter(wxService);

        if (encType == null) { // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toXml();
        } else if ("aes".equals(encType)) { // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
              return "";
            }
            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        this.logger.debug("\n组装回复信息：{}", out);

        return out;
    }

}
