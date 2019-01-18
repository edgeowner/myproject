package com.huboot.user.auth.controller.wycshop;

import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.user.auth.dto.UpdatePasswordReqDTO;
import com.huboot.user.auth.dto.UsernamePasswordAuthenticationReqDTO;
import com.huboot.user.auth.dto.wycshop.WycShopLoginInfoResDTO;
import com.huboot.user.auth.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "网约车管理端-用户服务-登陆认证 API")
@RestController
@RequestMapping(value = "/wycshop/user/auth")
public class WycshopAuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @PostMapping(value = "/login")
    @ApiOperation("用户名密码登陆,头部获取凭证：Authorization")
    public void login(@RequestBody UsernamePasswordAuthenticationReqDTO dto, HttpServletResponse response) throws Exception {
        JwtClaims jwtClaims = authService.loginWycShop(dto);
        response.setHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(jwtClaims));
    }

    @PostMapping(value = "/updatePassword")
    @ApiOperation("密码修改")
    public void updatePassword(@RequestBody UpdatePasswordReqDTO dto) throws Exception {
        authService.updatePassword(dto);
    }

    @GetMapping(value = "/loginInfo")
    @ApiOperation("获取当前用户登陆信息")
    public WycShopLoginInfoResDTO getLoginInfo() {
        WycShopLoginInfoResDTO dto = authService.findWycShopLoginInfo();
        return dto;
    }

}
