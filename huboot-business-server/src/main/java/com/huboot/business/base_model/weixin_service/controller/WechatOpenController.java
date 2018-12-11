package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicAuthUrlDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicDTO;
import com.huboot.business.base_model.weixin_service.service.IWeixinOpenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Api(tags = "微信开放平台 API")
@RestController
@RequestMapping("/base_model/weixin_service/open")
public class WechatOpenController {

    private Logger logger = LoggerFactory.getLogger(WechatOpenController.class);

    @Autowired
    private IWeixinOpenService weixinOpenService;

    /**
     * 获取开放平台授权url
     * @param authUrlDTO
     * @return
     */
    @PostMapping("/getPreAuthUrl")
    @ApiOperation(response = String.class, value = "获取开放平台授权url")
    public String getPreAuthUrl(@RequestBody WeixinPublicAuthUrlDTO authUrlDTO){
        return weixinOpenService.getPreAuthUrl(authUrlDTO.getUrl());
    }

    /**
     * 通过open authorizationCode 获取公众号信息
     */
    @GetMapping("/init_weixin_with_openauthcode")
    @ApiOperation(response = void.class, value = "通过open authorizationCode 初始化公众号信息")
    public WeixinPublicDTO initWeixinWithOpenAuthCode(@RequestParam("authorizationCode") String authorizationCode,
                                                      @RequestParam("type") Integer type){
        logger.info("通过open authorizationCode 初始化公众号信息{}{}", authorizationCode, type);
        return weixinOpenService.initWeixinWithOpenAuthCode(authorizationCode, type);
    }

    @GetMapping(value = "/getAccessToken/{weixinUid}")
    @ApiOperation(response = String.class, value = "获取AuthorizerAccessToken")
    public String getAccessToken(@PathVariable("weixinUid") String weixinUid) throws Exception {
        return weixinOpenService.getAccessToken(weixinUid);
    }

}
