package com.huboot.user.auth.controller.wycminiapp;

import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.user.auth.dto.ThirdAuthenticationReqDTO;
import com.huboot.user.auth.dto.UsernamePasswordAuthenticationReqDTO;
import com.huboot.user.auth.dto.wycminiapp.UserCustomerWeixinPhoneNumberAddReqDTO;
import com.huboot.user.auth.dto.wycminiapp.WycMiniappLoginInfoResDTO;
import com.huboot.user.auth.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "网约车司机小程序端-用户服务-登陆认证 API")
@RestController
@RequestMapping(value = "/wycdriverminiapp/user/auth")
public class WycminiappAuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @PostMapping(value = "/login")
    @ApiOperation("用户名密码登陆,头部获取凭证：Authorization")
    public void login(@RequestBody UsernamePasswordAuthenticationReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.loginWycMiniapp(dto);
        if (jwtClaims != null) {
            response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
        }
    }

    @PostMapping(value = "/login/weixin")
    @ApiOperation("微信code登陆,头部获取凭证：Authorization")
    public void wxLogin(@Valid @RequestBody ThirdAuthenticationReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.wxLogin(dto);
        if (jwtClaims != null) {
            response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
        }
    }

    @PostMapping(value = "/weixin/phoneNumber")
    @ApiOperation("小程序phoneNumber信息-取值后注册并登陆")
    public void postPhoneNumber(@Valid @RequestBody UserCustomerWeixinPhoneNumberAddReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.weinxinPhoneNumber(dto);
        if (jwtClaims != null) {
            response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
        }
    }

    /*@PostMapping(value = "/updatePassword")
    @ApiOperation("密码修改")
    public void updatePassword(@RequestBody UpdatePasswordReqDTO dto) throws Exception {
        authService.updatePassword(dto);
    }*/

    @GetMapping(value = "/loginInfo")
    @ApiOperation("获取当前用户登陆信息")
    public WycMiniappLoginInfoResDTO getLoginInfo() {
        WycMiniappLoginInfoResDTO dto = authService.findWycMiniAppLoginInfo();
        return dto;
    }

}
