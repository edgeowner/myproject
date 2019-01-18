package com.huboot.user.auth.controller.zkuser;

import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.user.auth.dto.ThirdAuthenticationReqDTO;
import com.huboot.user.auth.dto.UsernameCodeAuthenticationReqDTO;
import com.huboot.user.auth.dto.UsernameCodeSendReqDTO;
import com.huboot.user.auth.dto.wycminiapp.UserCustomerWeixinPhoneNumberAddReqDTO;
import com.huboot.user.auth.dto.zkuser.ZkUserLoginInfoResDTO;
import com.huboot.user.auth.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "直客用户端-用户中心-登陆认证 API")
@RestController
@RequestMapping(value = "/zkuser/user/auth")
public class ZkuserAuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @PostMapping(value = "/login/smsCode")
    @ApiOperation("用户名验证码登陆,头部获取凭证：Authorization")
    public void login(@RequestBody UsernameCodeAuthenticationReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.loginZkUser(dto);
        if (jwtClaims != null) {
            response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
        }
    }

    @PostMapping(value = "/login/weixin")
    @ApiOperation("微信code登陆,头部获取凭证：Authorization")
    public void wxLogin(@Valid @RequestBody ThirdAuthenticationReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.wxLoginForZkUser(dto);
        if (jwtClaims != null) {
            response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
        }
    }

    @PostMapping(value = "/weixin/phoneNumber")
    @ApiOperation("小程序phoneNumber信息-取值后注册并登陆")
    public void postPhoneNumber(@Valid @RequestBody UserCustomerWeixinPhoneNumberAddReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.weinxinPhoneNumberForZkUser(dto);
        if (jwtClaims != null) {
            response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
        }
    }

    @GetMapping(value = "/loginInfo")
    @ApiOperation("获取当前用户登陆信息")
    public ZkUserLoginInfoResDTO getLoginInfo() {
        ZkUserLoginInfoResDTO dto = authService.findZkUserLoginInfo();
        return dto;
    }

    @PostMapping(value = "/logout")
    @ApiOperation("退出,头部清空凭证：Authorization")
    public void login(HttpServletResponse response) throws Exception {
        authService.logoutZkUser();
        response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, "");
    }

    @PostMapping(value = "/sms")
    @ApiOperation("用户名验证码登陆,头部获取凭证：Authorization")
    public void smsSend(@RequestBody UsernameCodeSendReqDTO dto) throws Exception {
        authService.smsSendZkUser(dto);
    }
}